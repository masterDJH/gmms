//入口函数：页面读取所有元素再执行
$(function () {

    //把常用的一些组件获取
    var  purchaseBillItemGrid = $("#purchaseBillItemGrid");
    var  searchForm = $("#searchForm"); //查询表单
    var  editDialog = $("#editDialog"); //添加弹出框
    var  editForm = $("#editForm"); //添加表单

    //只要有data-method属性的元素我都要为它注册事件
    $("*[data-method]").on("click",function () {
        itsource[$(this).data("method")]();
    })
    //准备了相应的方法功能
    itsource = {
        //高级查询
        "search": function(){
            //JQuery没有提供拿到json格式的数据
            //直接拿到要查询的值  {username:xx,...}
            var params =searchForm.serializeObject();
            // //grid刷新
            purchaseBillItemGrid.datagrid('load',params);
        }
    };

    purchaseBillItemGrid.datagrid({
        nowrap:false,
        fitColumns:true,
        fit:true,
        fixed:true,
        fitColumns:true,
        toolbar:'#toolbar',
        url:'/purchasebillitem/findItems',
        columns:[[
            {field:'id',title:'编号',width:100},
            {field:'supplier',title:'供应商',width:100},
            {field:'buyer',title:'采购员',width:100},
            {field:'product',title:'产品',width:100},
            {field:'productType',title:'产品类型',width:100},
            {field:'vdate',title:'日期',width:100},
            {field:'num',title:'数量',width:100},
            {field:'price',title:'单价',width:100},
            {field:'amount',title:'小计',width:100},
            {field:'status',title:'状态',width:100,formatter:function (action) {
                    var data = {
                        0:"<div style='color:red;'>待审</div>",
                        1:"<div style='color: green'>已审</div>",
                        "-1":"<div><s>作废</s></div>"
                    };
                    return data[action];
                }}
        ]],
        groupField:'groupField',
        view: groupview,
        groupFormatter:function(value, rows){
            var totalNum = 0;
            var totalAmount = 0;
            for(var i=0;i<rows.length;i++){
                totalNum += rows[i].num;
                totalAmount += rows[i].amount;
            }
            return value + ' - ' + rows.length + ' 条数据' +"  <span style='color:green;'>共"+totalNum+"件商品</span>" +"<span style='color:#5d2f80;'>总金额:"+totalAmount+"</span>";
        }
    });
})