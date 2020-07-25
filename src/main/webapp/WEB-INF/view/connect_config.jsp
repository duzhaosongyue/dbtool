<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>数据库文档生成工具</title>
    <meta name="keywords" content="dbtool,数据文档导出工具,mysql文档导出工具,数据库文档生成工具，程三发，技术宅"/>
    <meta name="description" content="dbtool-数据库文档生成工具,通过对数据库连接信息配置可以直接生成数据库的结构文档。
			  其内容包括：表名、表注释、字段、类型、长度、字段描述、索引、组合索引等。
			  通过生成的文档可以快速了解数据库的结构。"/>
    <link rel="shortcut icon" href="../static/favicon.ico" type="image/x-icon">
    <link type="text/css" rel="stylesheet" href="../static/css/connect.css"/>
    <link type="text/css" rel="stylesheet" href="../static/css/common.css"/>
</head>
<body>
<div id="app" class="container">
    <div class="h1">创建数据库连接</div>
    <div class="content_box">
        <form οnsubmit="return false">
            <div class="sql_type">
                <span>数据库:</span>
                <div class="select_box">
                    <select v-model="database" class="select_list">
                        <option value="">请选择数据库</option>
                        <option v-for="(item, index) in databaseType" :value="index">{{item}}</option>
                    </select>
                </div>
            </div>
            <ul>
                <li>
                    <span>主机：</span>
                    <input placeholder="请输入域名或者ip" v-model="ip"/>
                </li>
                <li>
                    <span>端口:</span>
                    <input placeholder="请输入端口号" v-model="port"/>
                </li>
                <li>
                    <span>数据库名称(服务名):</span>
                    <input placeholder="请输入数据库名称" v-model="databaseName"/>
                </li>
                <li>
                    <span>用户名：</span>
                    <input placeholder="请输入用户名" v-model="user"/>
                </li>
                <li>
                    <span>密码:</span>
                    <input placeholder="请输入密码" type="password" v-model="pwd"/>
                </li>
            </ul>
            <div class="button_box">
                <button :disabled="buttonStatus == 0" @click="testConnect(this)">测试连接</button>
                <button :disabled="buttonStatus == 0" @click="connect(this)">连接</button>
            </div>

        </form>
    </div>
</div>
</body>
<script src="http://code.jquery.com/jquery-2.2.4.js" integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI="
        crossorigin="anonymous"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="static/js/vue.js" type="text/javascript" charset="utf-8"></script>
<script src="static/js/modal_dialog.js"></script>
<script src="static/js/common.js"></script>
<script>
    new Vue({
        el: "#app",
        data: {
            ip: "127.0.0.1",
            port: 3306,
            databaseName: "bladex",
            user: "root",
            pwd: "~wow1VSlol2",
            databaseType: ["MYSQL", "Oracle"],
            database: 0,
            severName: "",
            buttonStatus: 1
        },
        created: function () {
        },
        methods: {
            testConnect: function (obj) {
                this.buttonStatus = 0;
                if (this.tips()) {
                    const result = asyncGet("connection", {
                        "ip": this.ip,
                        "port": this.port,
                        "user": this.user,
                        "pwd": this.pwd,
                        "database": this.database,
                        "databaseName": this.databaseName
                    });
                    showAlert(result == 1 ? 'info' : 'error', result == 1 ? "连接成功！":"连接失败");
                }
                this.buttonStatus = 1;
            },
            connect: function () {
                this.buttonStatus = 0;
                if (this.tips()) {
                    const result = asyncGet("connection", {
                        "ip": this.ip,
                        "port": this.port,
                        "user": this.user,
                        "pwd": this.pwd,
                        "database": this.database,
                        "databaseName": this.databaseName
                    });
                    if (result == 1) {
                        location.href = '/list?ip=' + this.ip + "&port=" + this.port + '&user=' + this.user + '&pwd=' + this.pwd + '&database=' + this.database + '&databaseName=' + this.databaseName;
                    } else {
                        showAlert('error', "连接失败！");
                    }
                }
                this.buttonStatus = 1;
            },
            tips: function () {
                return this.checkParameter();
            },
            checkParameter: function () {
                var ip_reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
                var domain_name_reg = /^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$/
                var port_reg = /^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/;
                if (!ip_reg.test(this.ip) || !domain_name_reg.test(this.ip)) {
                    showAlert('info', '请输入正确的域名或ip！');
                    return false;
                }
                if (!port_reg.test(this.port)) {
                    showAlert("info", '请输入正确的端口！');
                    return false;
                }
                if (this.user == '' || this.pwd == '') {
                    showAlert("info", "用户名和密码不能为空！");
                    return false;
                }
                return true;
            }
        }
    })

    var form = document.getElementsByTagName('form')[0];
    form.addEventListener('submit', function (e) {
        e.preventDefault();
    });


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
</script>

</html>
