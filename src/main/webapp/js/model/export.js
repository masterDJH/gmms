function imageformat(value, row, index) {
    if (value){
        return "<img src='" + value + "' alt='没有头像' style='width: 50px;height: 50px;' />";
        // return `<img src=' + ${value} + ' alt="没有头像" style="width: 50px;height: 50px;" /> `;
    }else {
        return "没有头像";
    }
}

function member(value) {
    if(value) {
        return value.memberName;
    } else {
        return "";
    }
}
$(function () {
    var  employeeDataGrid = $("#employeeDataGrid"); //获取用户页面显示表格
    var  courseDataGrid = $("#courseDataGrid"); //获取课程页面显示表格
    var exportEmpForm = $("#exportEmpForm");//获取导出用户表单
    var exportCouForm = $("#exportCouForm");//获取导出课程表单

    //绑定操作函数,并执行用户对应的操作函数
    $("a[data-method]").on("click", function() {
        // var method = $(this).attr("data-method");
        var method = $(this).data("method"); //获取a标签中指定的函数名
        itsource[method](); //执行相应的函数
    });
    var itsource = {
        // 导出用户（弃用）
        "exportEmp": function() {
            // 获取指定表单的所有参数,并转为JSON格式的数据
            var paramsEmp = exportEmpForm.serializeObject();

            $.post("/export/exportEmpXlsx",{username:paramsEmp.username,phone:paramsEmp.phone},function (result) {
                if(result.success){// 导出成功
                    $.messager.alert("导出成功",result.mes,"info");
                }else {// 删除失败
                    $.messager.alert("错误原因","导出失败，失败原因："+result.mes,"error");
                }
            },"json");
        },
        // 导出课程（弃用）
        "exportCou": function() {
            // 获取指定表单的所有参数,并转为JSON格式的数据
            var paramsCou = exportCouForm.serializeObject();

            $.post("/export/exportCouXlsx",{courseName:paramsCou.courseName,courseCost:paramsCou.courseCost},function (result) {
                if(result.success){// 导出成功
                    $.messager.alert("导出成功",result.mes,"info");
                }else {// 删除失败
                    $.messager.alert("错误原因","导出失败，失败原因："+result.mes,"error");
                }
            },"json");
        },
        "empSearch": function(){
            // 获取指定表单的所有参数,并转为JSON格式的数据
            var paramsEmp = exportEmpForm.serializeObject();
            // 使用原有的DataGrid表格的URL发送请求,再加上下面的参数,最后将数据加载到原有的DataGrid表格中
            employeeDataGrid.datagrid('load',paramsEmp);
        },
        "couSearch": function() {
            // 获取指定表单的所有参数,并转为JSON格式的数据
            var paramsCou = exportCouForm.serializeObject();
            // 使用原有的DataGrid表格的URL发送请求,再加上下面的参数,最后将数据加载到原有的DataGrid表格中
            courseDataGrid.datagrid('load',paramsCou);
        }
    };

    $('#courseDataGrid').datagrid({
        remoteSort:false,
        singleSelect:true,
        nowrap:false,
        fitColumns:true,
        url:'/course/page',
        columns:[[
            {field:'courseName',title:'课程名称',width:100,sortable:true},
            {field:'courseCost',title:'原价',width:30,align:'left',sortable:true},
            {field:'descs',title:'课程描述',width:80,align:'left',sortable:true},
            {field:'pic',title:'图片',width:100,sortable:true},
            {field:'courseTime',title:'课程时长(月)',width:60,align:'center'}
        ]],
        view: detailview,
        /**课程详情页**/
        detailFormatter: function(rowIndex, rowData){
            return '<table><tr>' +
                '<td rowspan=2 style="border:0"><img src="' + rowData.pic + '" style="height:50px;"></td>' +
                '<td style="border:0">' +
                '<p><span class="detail">课程名称: ' + rowData.courseName + '</span><span class="detail">原价: ' + rowData.courseCost + '</span></p>'+
                '<p><span class="detail">课程描述: ' + rowData.descs + '</span><span class="detail">课程时长(月): ' + rowData.courseTime + '</span></p>'+
                '</td>' +
                '</tr></table>';
        }
    });
});