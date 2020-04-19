$(function () {
    var  announcementDataGrid = $("#announcementDataGrid"); //获取页面显示表格
    var searchForm = $("#searchForm");//获取form搜索表单
    var editDialog = $("#editDialog");//获取添加修改页面的数据表格
    var editForm = $("#editForm");//获取添加修改页面的form表单
    var editor = $("#editor");// 获取添加页面的富文本编辑器
    var info = $("#info");// 获取添加修改页面的【隐藏域课程描述文本框】

    //绑定操作函数,并执行用户对应的操作函数
    $("a[data-method]").on("click", function() {
        // var method = $(this).attr("data-method");
        var method = $(this).data("method"); //获取a标签中指定的函数名
        itsource[method](); //执行相应的函数
    });
    var itsource = {
        // 只弹出添加页面,功能由save方法完成
        "add": function() {
            // 打开添加页面,清空数据
            editForm.form("clear");
            // 打开添加页面,清空富文本编辑器
            editor.texteditor("setValue","");
            // 先居中,再打开添加框
            editDialog.dialog("center").dialog("open");
        },
        // 只弹出修改页面,功能由save方法完成
        "update": function() {
            var row = announcementDataGrid.datagrid("getSelected");
            if(row){
                // 回显富文本编辑器的文字
                editor.texteditor("setValue",row.info);

                // 回显选定编辑的数据
                editForm.form("load",row);
                // 修改面板先居中,再打开添加框
                editDialog.dialog("center").dialog("open");
            }else {
                $.messager.alert("提示", "你还没有选择修改哪一列哦!", "warnning");
                return ;
            };
        },
        "remove": function() {
            var row = announcementDataGrid.datagrid("getSelected");
            if(row) { // 判断是否选中一列数据
                $.messager.confirm("确认对话框", "你确定要删除: " + row.title + " 吗？", function(res) {
                    if(res) { // 确定删除
                        $.post("/announcement/delete",{id:row.id},function (result) {
                            if(result.success){// 删除成功
                                /*
                                *   alert()不建议使用：
                                *   1.不同浏览器的显示效果不同
                                *   2.alert()显示时,会阻塞进程,不便于以后实行异步请求
                                * */
                                // $.messager.alert(result.mes);
                            }else {// 删除失败
                                $.messager.alert("错误原因","删除失败 \n"+result.mes,"error");
                            }
                        },"json");
                        // 延时刷新数据，防止后台数据更新不及时，导致页面数据更新仍为旧数据
                        setTimeout(function () {
                            announcementDataGrid.datagrid("reload"); // 刷新数据
                        },200);
                    } else { // 取消删除
                        $.messager.show({
                            title:'提示',
                            msg:'已取消删除!',
                            timeout:3000,
                            showType:'fade',
                            style:{
                                right:'',
                                top:document.body.scrollTop+document.documentElement.scrollTop,
                                bottom:''

                            }
                        });
                        announcementDataGrid.datagrid("reload"); // 刷新数据
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
            announcementDataGrid.datagrid('load',params);
        },
        "save":function () {
            var URL = "/announcement/save";// 默认为 save 操作
            var announcementId = $("#announcementId").val();// 获取添加页面的隐藏域 id 值
            if (announcementId){// 添加面板的Employee的id不为空,表示是 update 操作
                URL = "/announcement/update?cmd=update";
            }
            editForm.form("submit",{
                url:URL,
                onSubmit: function(){
                    // 处理富文本编辑器的值
                    info.val(editor.texteditor("getValue"));
                    // 返回数据校验的结果,根据结果决定是否执行URL对应的添加方法
                    return $(this).form("validate");
                },
                success: function(data){
                    // 将Json字符串转成JSON对象
                    var result = JSON.parse(data);
                    if(result.success){// 添加成功
                        $("#announcementDataGrid").datagrid("reload"); // 刷新数据
                        // 关闭添加框前,清空数据
                        editForm.form("clear");
                        // 关闭添加框
                        itsource.close();
                    } else {
                        $.messager.alert("错误原因","添加失败 \n"+result.mes,"error");
                    }
                }
            });
        },
        "close":function () {
            // 打开添加框前,清空数据
            editForm.form("clear");
            // 关闭添加面板
            editDialog.dialog("close");
        }
    };

    $('#announcementDataGrid').datagrid({
        remoteSort:false,
        singleSelect:true,
        nowrap:false,
        fitColumns:true,
        url:'/announcement/page',
        columns:[[
            {field:'title',title:'标题',width:80,align:'left',sortable:true},
            {field:'info',title:'内容',width:80,align:'left',sortable:true},
            {field:'employee',title:'发布者',width:30,align:'left',sortable:true,
                formatter:function (value,row,index) {
                    if(row.employee) {
                        return row.employee.username;
                    } else {
                        return "";
                    }
                }
            },
            {field:'anDate',title:'发布时间',width:40,align:'left',sortable:true}
        ]],
        view: detailview,
        /**课程详情页**/
        detailFormatter: function(rowIndex, rowData){
            return '<table><tr>' +
                '<td style="border:0">' +
                '<p><span class="detail">标题: ' + rowData.title + '</span></p>'+
                '<p><span class="detail">内容: <br/>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp' + rowData.info + '</span></p>'+
                '</td>' +
                '</tr></table>';
        }
    });
});