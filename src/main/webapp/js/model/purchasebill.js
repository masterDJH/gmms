function statusFormat(value) {
    if(value==0){
        return "<span style='color: red;'>待审核</span>";
    }else if(value==1){
        return "<span style='color: green;'>审核通过</span>";
    }else{
        return "<s style='color: grey;'>作废</s>";
    }
}
function objFormat(value) {
    if(value) {
        return value.name || value.username;
    } else {
        return "";
    }
}


$(function () {
    var  purchasebillDataGrid = $("#purchasebillDataGrid"); //获取页面显示表格
    var searchForm = $("#searchForm");//获取form搜索表单
    var editDialog = $("#editDialog");//获取添加修改页面的数据表格
    var editForm = $("#editForm");//获取添加修改页面的form表单
    var dg = $("#itemsGrid");

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
            // 先居中,再打开添加框
            editDialog.dialog("center").dialog("open");
            // 把明细的grid数据清空
            dg.datagrid("loadData",[]);
        },
        // 只弹出修改页面,功能由save方法完成
        "update": function() {
            var row = purchasebillDataGrid.datagrid("getSelected");
            // var index = purchasebillDataGrid.datagrid("getRowIndex");
            // purchasebillDataGrid.datagrid("endEdit",index);
            if(row){
                // 修改面板先居中,再打开添加框
                editDialog.dialog("center").dialog("open");
                // 回显供应商的数据
                if(row.supplier){
                    //指定原先选定的供应商
                    // ecombox.combobox("select",row.types.id);
                    row["supplier.id"] = row.supplier.id;
                }
                // 回显采购员的数据
                if(row.buyer){
                    //指定原先选定的采购员
                    row["buyer.id"] = row.buyer.id;
                }
                editForm.form("load",row);
                // 回显明细
                var newRows = [...row.items];
                dg.datagrid("loadData",newRows);
            }else {
                $.messager.alert("提示", "你还没有选择修改哪一列哦!", "warnning");
                return ;
            };
        },
        "remove": function() {
            var row = purchasebillDataGrid.datagrid("getSelected");
            if(row) { // 判断是否选中一列数据
                $.messager.confirm("确认对话框", "你确定要删除这条采购订单吗？", function(res) {
                    if(res) { // 确定删除
                        $.post("/purchasebill/delete",{id:row.id},function (result) {
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
                            purchasebillDataGrid.datagrid("reload"); // 刷新数据
                        },200);
                    } else { // 取消删除
                        $.messager.alert("提示", "已取消删除!", "warnning");
                        purchasebillDataGrid.datagrid("reload"); // 刷新数据
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
            purchasebillDataGrid.datagrid('load',params);
        },
        "save":function () {
            var row = purchasebillDataGrid.datagrid("getSelected");
            var index = purchasebillDataGrid.datagrid("getRowIndex",row);
            purchasebillDataGrid.datagrid("endEdit",index);
            var URL = "/purchasebill/save";// 默认为 save 操作
            var purchasebillId = $("#purchasebillId").val();// 获取添加页面的隐藏域 id 值
            if (purchasebillId){// 添加面板的Employee的id不为空,表示是 update 操作
                URL = "/purchasebill/update?cmd=update";
            }
            editForm.form("submit",{
                url:URL,
                onSubmit: function(param){
                    // 获取明细中的所有行
                    var rows = dg.datagrid("getRows");
                    // 遍历行，拼接传递的数据
                    for (var i=0;i<rows.length;i++){
                        var row = rows[i];
                        // 拼接结构,将订单明细中的所有数据全部放入product.items集合中
                        param[`items[${i}].product.id`]=row.product.id;
                        param[`items[${i}].num`]=row.num;
                        param[`items[${i}].price`]=row.price;
                        param[`items[${i}].descs`]=row.descs;
                    }
                    // 返回数据校验的结果,根据结果决定是否执行URL对应的添加方法
                    return $(this).form("validate");
                },
                success: function(data){
                    // 将Json字符串转成JSON对象
                    //var result = eval("("+data+")");
                    var result = JSON.parse(data);
                    if(result.success){// 添加成功
                        $("#purchasebillDataGrid").datagrid("reload"); // 刷新数据
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
            // 打开添加框前,清空数据
            editForm.form("clear");
            // 关闭添加面板
            editDialog.dialog("close");
        },
    };
    /**
     * 找到对应的datagrid
     *  defaultRow:默认行数据
     *  insertPosition:插入数据的位置(bottom:下面 top:上面)
     */
    // var dg = $("#itemsGrid"),
    var defaultRow = { product: "", productColor: "", productImg: "", num: 0, price: 0, amount: "", descs: "" },
        insertPosition = "bottom";

    /**
     * 初始化创建咱们的编辑datagrid
     */
    var dgInit = function () {
        //grid中所有的列
        var getColumns = function () {
            var result = [];

            var normal = [
                {
                    field: 'product', title: '产品', width: 50,
                    editor: {
                        type: "combobox",
                        options: {
                            valueField:'id',
                            textField:'name',
                            panelHeight:'auto',
                            url:'/product/findAll',
                            required: true
                        }
                    },
                    //特别注意，这个属性和title是平级的
                    formatter:function(v){
                        //只显示产品的名称
                        return v ? v.name:"";
                    }
                },
                {
                    field: 'productColor', title: '颜色', width: 50,
                    //v:当前格子数据  r:当前行数据 i:行索引
                    formatter:function(v,r,i){
                        if(r && r.product){
                            return `<div style="width: 15px;height: 15px;background-color: ${r.product.color}">`;
                        }
                    }
                },
                {
                    field: 'productImg', title: '图片', width: 50,
                    //v:当前格子数据  r:当前行数据 i:行索引
                    formatter:function(v,r,i){
                        if(r && r.product){
                            return `<img src="${r.product.pic}" width="30px" height="30px"/>`;
                        }
                    }
                },
                {
                    field: 'num', title: '数量', width: 30,
                    editor: {
                        type: "numberbox",
                        options: {
                            min: 0,// 最小值
                            precision: 1,// 保留 1 位小数
                            required: true
                        }
                    }
                },{
                    field: 'price', title: '价格', width: 30,
                    editor: {
                        type: "numberbox",
                        options: {
                            min: 0,// 最小值
                            precision: 2,// 保留 2 位小数
                            required: true
                        }
                    }
                },{
                    field: 'amount', title: '小计', width: 40,
                    //v:当前格子数据  r:当前行数据 i:行索引
                    formatter:function(v,r){
                        if(r.num && r.price){// 当数量和价格都不为空时,才计算价格
                            var total = (r.num*r.price).toFixed(2);
                            return total;
                        }
                        // 数量和价格为空,返回 0
                        return 0;
                    }
                },{
                    field: 'descs', title: '备注', width: 60,
                    editor: {
                        type: "text"
                    }
                }
            ];
            result.push(normal);

            return result;
        };
        //grid中的属性
        var options = {
            // idField: "ID",
            rownumbers: true, //行号
            fitColumns: true,
            fit: true,
            border: true,
            singleSelect: true,
            columns: getColumns(),
            //表示开启单元格编辑功能
            enableCellEdit: true
        };
        //创建grid
        dg.datagrid(options);
    };
    //插入的行索引
    var getInsertRowIndex = function () {
        return insertPosition == "top" ? 0 : dg.datagrid("getRows").length;
    }

    //事件注册
    var buttonBindEvent = function () {
        //添加行
        $("#btnInsert").click(function () {
            var targetIndex = getInsertRowIndex(), targetRow = $.extend({}, defaultRow);
            dg.datagrid("insertRow", { index: targetIndex, row: targetRow });
            dg.datagrid("editCell", { index: targetIndex, field: "Code" });
        });
        //删除行
        $("#btnRemove").click(function () {
            // 获取选中的行数据
            var row = dg.datagrid("getSelected");
            if (row){
                // 获取选中的行索引
                var index = dg.datagrid("getRowIndex",row);
                // 删除指定索引(当前选中)的行
                dg.datagrid("deleteRow",index);
            }else {
                $.messager.alert("提示", "你还没有选择删除哪一列哦!", "warnning");
            }
        });
    };
    dgInit(); buttonBindEvent();
});