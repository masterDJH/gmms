// 入口函数
$(function () {
    // $("body").bind('contextmenu',function(e){
    //     // 阻止原右键事件
    //     e.preventDefault();
    //     // 显示自定义的右键菜单
    //     $('#rcmenu').menu('show', {
    //         left: e.pageX,
    //         top: e.pageY
    //     });
    // });
    $(".easyui-tabs").bind('contextmenu',function(e){
        // 阻止原右键事件
        e.preventDefault();
        // 显示自定义的右键菜单
        $('#rcmenu').menu('show', {
            left: e.pageX,
            top: e.pageY
        });
    });
    //关闭所有标签页
    $("#closeall").bind("click",function(){
        var tablist = $('#euiTabs').tabs('tabs');
        for(var i=tablist.length-1;i>=0;i--){
            $('#euiTabs').tabs('close',i);
        }
    });
    //关闭其他页面（先关闭右侧，再关闭左侧）
    $("#closeother").bind("click",function(){
        var tablist = $('#euiTabs').tabs('tabs');
        var tab = $('#euiTabs').tabs('getSelected');
        var index = $('#euiTabs').tabs('getTabIndex',tab);
        for(var i=tablist.length-1;i>index;i--){
            $('#euiTabs').tabs('close',i);
        }
        var num = index-1;
        if(num < 0){// 左侧没有其他页面
            return;
        }else{
            for(var i=num;i>=0;i--){
                $('#euiTabs').tabs('close',i);
            }
            $("#euiTabs").tabs("select", 1);
        }
    });
    //关闭右边的选项卡
    $("#closeright").bind("click",function(){
        var tablist = $('#euiTabs').tabs('tabs');
        var tab = $('#euiTabs').tabs('getSelected');
        var index = $('#euiTabs').tabs('getTabIndex',tab);
        for(var i=tablist.length-1;i>index;i--){
            $('#euiTabs').tabs('close',i);
        }
    });
    //关闭左边的选项卡
    $("#closeleft").bind("click",function(){
        var tablist = $('#euiTabs').tabs('tabs');
        var tab = $('#euiTabs').tabs('getSelected');
        var index = $('#euiTabs').tabs('getTabIndex',tab);
        var num = index-1;
        if(num < 0){
            return;
        }else{
            for(var i=num;i>=0;i--){
                $('#euiTabs').tabs('close',i);
            }
            $("#euiTabs").tabs("select", 1);
        }
    });
});