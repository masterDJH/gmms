$(function () {
    // var  searchName = $("#name"); //获取搜索条件输入框
    // var searchForm = $("#searchForm");//获取form搜索表单
    // var oederForm = $("#oederForm1");//获取form订购表单

    //绑定操作函数,并执行用户对应的操作函数
    $("a[data-method]").on("click", function() {
        // var method = $(this).attr("data-method");
        var method = $(this).data("method"); //获取a标签中指定的函数名
        itsource[method](); //执行相应的函数
    });
    var itsource = {
        "order": function() {// 弃用

        },
        "search": function() {// 弃用
            // $.post("/courseOrder/search",{id:row.id},function (result) {},"json");

            // 发送搜索URL请求，根据返回结果，将body标签下的所有元素置空后再添加课程HTML代码，以实现搜索后课程跟着改变
            // $.post("/courseOrder/search",{courseName:searchName.textbox("getValue")},function (result) {
            //     // 拼接搜索结果的HTML页面
            //     var view ='<div id="toolbar" style="padding:2px 5px;">\n' +
            //         '            <form id="searchForm" action="" method="post">\n' +
            //         '                课程名: <input class="easyui-textbox" style="width:80px" id="name" name="name">\n' +
            //         '                <a href="#" data-method="search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>\n' +
            //         '            </form>\n' +
            //         '        </div>';
            //     // var courses = JSON.parse(result);
            //     console.log("长度:"+result.courses);
            //     var courses = result;
            //     for (var i=0;i<courses.length;i++){
            //         var course = courses.get(i);
            //         view += '<form id="oederForm${course.id}" method="get">\n' +
            //             '                <div class="co">\n' +
            //             '                    <div class="co_img">\n' +
            //             '                        <img src="'+course.pic+'" style="width: 150px;height:150px"/>\n' +
            //             '                    </div>\n' +
            //             '                    <div class="co_title">\n' +
            //             '                        <input type="hidden" id="orderId" name="orderId" >\n' +
            //             '                        <p>'+course.courseName+'</p>\n' +
            //             '                    </div>\n' +
            //             '                    <div class="co_desc">\n' +
            //             '                        <span>'+course.descs+'</span>\n' +
            //             '                    </div>\n' +
            //             '                    <div class="co_sell">\n' +
            //             '                        <p><span>￥'+course.courseCost+' 积分 </span><span>时长: '+course.courseTime+' 月</span></p>\n' +
            //             '                        <a href="/courseOrder/order?id='+course.id+'" class="easyui-linkbutton" plain="true">订购</a>\n' +
            //             '                    </div>\n' +
            //             '                </div>\n' +
            //             '            </form>';
            //     }
            //     console.log("页面:"+view);
            //     $("body").html(view);
            // });
        }
    };
});