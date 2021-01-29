/*禁止表单提交*/
var form = document.getElementsByTagName('form')[0];
form.addEventListener('submit', function (e) {
    e.preventDefault();
});


/*弹框*/
function showAlert(icon, msg) {
    $modal({
        type: 'message', //弹框类型  'alert' or  'confirm' or 'message'    message提示(开启之前如果之前含有弹框则清除)
        icon: icon, // 提示图标显示 'info' or 'success' or 'warning' or 'error'  or 'question'
        timeout: 2000, // 单位 ms  显示多少毫秒后关闭弹框 （ confirm 下无效 | 不传默认为 2000ms | 最短显示时间为500ms）
        content: msg, // 提示文字
        top: 100, //距离顶部距离 单位px
        transition: 300, //过渡动画 默认 200  单位ms
        closable: false, // 是否显示可关闭按钮  默认为 false
    })
}


function asyncGet(url, param) {
    let result;
    $.ajaxSettings.async = false;
    $.get(url, param, (data) => {
        result = data;
    });
    return result;
}
