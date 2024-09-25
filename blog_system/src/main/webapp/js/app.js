 // 创建一个新的函数：用于限制强制登录
 function getLoginStatus() {
    $.ajax({
        type: 'get',
        url: 'login',
        success: function(body) {
            // 200 登录状态，不进行任何处理
            console.log("当前用户已经登录！")
        },
        error: function() {
            // 非2开头的状态码都会触发这个error分支
            // 让页面跳转到login.html页面
            console.log("当前用户尚未登录！");
            location.assign("login.html");
        }
    });
}