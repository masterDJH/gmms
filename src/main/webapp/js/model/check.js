$(function () {
    var  announcementDataGrid = $("#announcementDataGrid"); //获取页面显示表格
    var searchForm = $("#searchForm");//获取form搜索表单

    //绑定操作函数,并执行用户对应的操作函数
    $("a[data-method]").on("click", function() {
        // var method = $(this).attr("data-method");
        var method = $(this).data("method"); //获取a标签中指定的函数名
        itsource[method](); //执行相应的函数
    });
    var itsource = {
        "search": function() {
            // 获取指定表单的所有参数,并转为JSON格式的数据
            var params = searchForm.serializeObject();

            // 使用原有的DataGrid表格的URL发送请求,再加上下面的参数,最后将数据加载到原有的DataGrid表格中
            announcementDataGrid.datagrid('load',params);
        }
    };

    $('#announcementDataGrid').datagrid({
        remoteSort:false,
        singleSelect:true,
        nowrap:false,
        fitColumns:true,
        url:'/announcement/page',
        columns:[[
            {field:'title',title:'标题',width:30,align:'left',sortable:true},
            {field:'info',title:'内容',width:80,align:'left',sortable:true},
            {field:'employee',title:'发布者',width:80,align:'left',sortable:true,
                formatter:function (value,row,index) {
                    if(row.employee) {
                        return row.employee.username;
                    } else {
                        return "";
                    }
                }
            },
            {field:'anDate',title:'发布时间',width:80,align:'left',sortable:true}
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