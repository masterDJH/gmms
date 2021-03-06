function permsFormat(value) {// 拼接 权限 字符串
    var permsName = "";
    for(var p of value){
        permsName += p.perName +"、";
    }
    // 去除最后一个"、",并返回结果
    return permsName.substring(0, permsName.lastIndexOf("、"));
}

// 入口函数
$(function () {
    var roleDataGrid = $("#roleDataGrid");//获取Role展示页面数据表格
    var searchForm = $("#searchForm");//获取form搜索表单
    var editDialog = $("#editDialog");//获取添加修改页面的数据表格
    var editForm = $("#editForm");//获取添加修改页面的form表单

    //拿到两个grid数据表格
    var  rolePermsGrid = $("#rolePermsGrid");  //左边当前角色的权限
    var  allPermsGrid = $("#allPermsGrid");  //右边所有的权限类型数据

    //绑定操作函数,并执行用户对应的操作函数
    $("a[data-method]").on("click", function() {
        // var method = $(this).attr("data-method");
        var method = $(this).data("method"); //获取a标签中指定的函数名
        itsource[method](); //执行相应的函数
    });
    var itsource = {
        // 只弹出添加页面,功能由save方法完成
        "add": function() {
            // 打开添加框前,清空数据
            editForm.form("clear");

            //loadData 加载本地数据，旧的行将被移除。此处用于清空上一次的残余数据
            rolePermsGrid.datagrid("loadData",[]);
            // 先居中,再打开添加框
            editDialog.dialog("center").dialog("open");
        },
        // 只弹出修改页面,功能由save方法完成
        "update": function() {
            var row = roleDataGrid.datagrid("getSelected");
            if(row){
                // 回显选定编辑的数据
                editForm.form("load",row);
                // 修改面板先居中,再打开添加框
                editDialog.dialog("center").dialog("open");

                //复制要修改的角色中的权限(JS新语法),用于清空
                var newPerms = [...row.permissions];
                // 每次都载入备份的数据,以免上一次未提交的残余数据影响下一次的数据回显
                rolePermsGrid.datagrid("loadData",newPerms);
            }else {
                $.messager.alert("提示", "你还没有选择修改哪一列哦!", "warnning");
                return ;
            };
        },
        "remove": function() {
            var row = roleDataGrid.datagrid("getSelected");
            if(row) { // 判断是否选中一列数据
                $.messager.confirm("确认对话框", "你确定要删除: " + row.roleName + " 吗？", function(res) {
                    if(res) { // 确定删除
                        $.post("/role/delete",{id:row.id},function (result) {
                            if(result.success){// 删除成功
                                /*
                                *   alert()不建议使用：
                                *   1.不同浏览器的显示效果不同
                                *   2.alert()显示时,会阻塞进程,不便于以后实行异步请求
                                * */
                                // $.messager.alert(result.mes);
                            }else {// 删除失败
                                $.messager.alert("错误原因","删除失败，失败原因："+result.mes,"error");
                            }
                        },"json");
                        // 延时刷新数据，防止后台数据更新不及时，导致页面数据更新仍为旧数据
                        setTimeout(function () {
                            roleDataGrid.datagrid("reload"); // 刷新数据
                        },200);
                    } else { // 取消删除
                        $.messager.alert("提示", "已取消删除!", "warnning");
                        roleDataGrid.datagrid("reload"); // 刷新数据
                    }
                });
            } else {
                $.messager.alert("提示", "你还没有选择删除哪一列哦!", "warnning");
                return ;
            }
        },
        "search": function() {
            // 获取指定表单的所有参数,并转为JSON格式的数据
            var params = searchForm.serializeObject();

            // 使用原有的DataGrid表格的URL发送请求,再加上下面的参数,最后将数据加载到原有的DataGrid表格中
            roleDataGrid.datagrid('load',params);
        },
        "save":function () {
            var URL = "/role/save";// 默认为 save 操作
            var roleId = $("#roleId").val();// 获取添加页面的隐藏域 id 值
            if (roleId){// 添加面板的Employee的id不为空,表示是 update 操作
                URL = "/role/update?cmd=update";
            }
            // 接收前台数据,并提交数据
            editForm.form("submit",{
                url:URL,
                /**
                 * param:加在它身上的所有数据都会向后台提交
                 */
                onSubmit: function(param){
                    // 处理左边的grid的权限数据,让它以正确格式提交到后台去
                    var rows = rolePermsGrid.datagrid("getRows");
                    // 拿到左边grid的所有值
                    for (var i=0;i<rows.length;i++){
                        // 不能使用param.permission[i].id = rows[i].id,因为param没有permission这个属性
                        // 遍历rows，拿到每个权限数据的id,再赋值给permissions[0...rows.length].id属性,这样就能把权限数据存入数据库
                        param[`permissions[${i}].id`] = rows[i].id;
                    }
                    // 返回数据校验的结果,根据结果决定是否执行URL对应的添加方法
                    return $(this).form("validate");
                },
                success: function(data){
                    // 将Json字符串转成JSON对象
                    //var result = eval("("+data+")");
                    var result = JSON.parse(data);
                    if(result.success){// 添加成功
                        $("#roleDataGrid").datagrid("reload"); // 刷新数据
                        roleDataGrid.datagrid("reload");
                        // 关闭添加框前,清空数据
                        editForm.form("clear");
                        // 关闭添加框
                        itsource.close();
                    } else {
                        $.messager.alert("错误原因","添加失败，失败原因："+result.mes,"error");
                    }
                }
            });
        },
        "close":function () {
            // 关闭添加框前,清空数据
            editForm.form("clear");
            // 关闭添加面板
            editDialog.dialog("close");
        },
        //添加权限 index:选定行索引 row:选定行的信息
        "addPerms":function (index, row) {
            // 获得左边表格里所有的权限数据
            var rows = rolePermsGrid.datagrid("getRows");
            // 遍历左边表格里所有的权限数据,如果有相同的权限,就提示已存在该权限
            for (var perm of rows){
                if (perm.id == row.id){// 存在相同的权限,提示已存在该权限
                    $.messager.show({
                        title:'提示',
                        msg:'该权限已经存在！',
                        timeout:3000,
                        showType:'fade',
                        style:{
                            right:'',
                            top:document.body.scrollTop+document.documentElement.scrollTop,
                            bottom:''
                        }
                    });
                    // 返回,不执行余下代码
                    return;
                }
            }
            // 向左边的表格添加当前选定的权限数据
            rolePermsGrid.datagrid("appendRow",row);
        },
        //删除权限 index:选定行索引 row:选定行的信息
        "removePerms":function (index, row) {
            // 在左边的表格中删除当前选定的权限
            rolePermsGrid.datagrid("deleteRow",index);
        }
    };

    //创建左边数据表格,展示角色的权限
    rolePermsGrid.datagrid({
        fit:true, // 面板大小自适应父容器
        fitColumns:true, // 自动展开、收缩列的大小，以适应网格的宽度
        onDblClickRow: itsource.removePerms, // 绑定双击事件,以删除当前选定行的权限
        columns:[[
            {field:'perName',title:'名称',width:100},
            {field:'perUrl',title:'路径',width:100},
            {field:'sn',title:'编码',width:100}
        ]]
    });

    // 创建右边数据表格,查询显示所有的权限
    allPermsGrid.datagrid({
        fit:true, // 面板大小自适应父容器
        fitColumns:true, // 自动展开、收缩列的大小，以适应网格的宽度
        url:"/permission/findAll",
        onDblClickRow: itsource.addPerms, // 绑定双击事件,以添加当前选定行的权限
        columns:[[
            {field:'perName',title:'名称',width:100},
            {field:'perUrl',title:'路径',width:100},
            {field:'sn',title:'编码',width:100}
        ]]
    });
});