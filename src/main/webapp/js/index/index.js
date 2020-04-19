function showHead() {
    // 查询、显示登录用户的头像
    $.post("/head",{},function (result) {
        document.getElementById("head").src = result;
    },"json");
}
$(function(){
    // 显示登录用户的头像
    showHead();

    $("#menuTree").tree({
        url:'/menu/findChildrenMenus', //查询菜单数据
        method:'get',
        onClick:function(node){
            //调优速度
            var etab = $("#euiTabs");
            // 方法提供了一个参数, 即节点对象
            if(node.url){//判断节点是否有 url 属性
                //调优速度
                var tabName = node.text;
                if (etab.tabs("exists",tabName)) {//判断是否已经打开该选项卡
                    etab.tabs("select",tabName);//跳至该选项卡
                } else{
                    //创建新选项卡
                    $("#euiTabs").tabs("add",{
                        "title":node.text,
                        closable:true,
                        content:"<iframe src="+ node.url+" frameborder='0' width='100%' height='100%'></iframe>"
                    });
                }
            }
        }
    });
});