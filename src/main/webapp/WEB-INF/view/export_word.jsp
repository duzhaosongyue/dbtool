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
    <link type="text/css" rel="stylesheet" href="../static/css/common.css"/>
    <link type="text/css" rel="stylesheet" href="../static/css/export.css"/>
</head>

<body>
<div class="container" id="app">
    <div class="h1">数据库导出文档</div>
    <div class="content_box">
        <form οnsubmit="return false">
            <ul>
                <li>
                    <span class="li_check">
						 <input type="checkbox" v-on:click="checkAll"/>
						 <span class="name">表名称</span>
					   </span>
                    <span class="describe">表注释</span>
                </li>
                <li v-for="table in tables">
					  <span class="li_check">
						 <input type="checkbox" v-model="checkData" :value="table.tableName" class="checkItem"/>
						 <span class="name" :title="table.tableName">{{interceptStr(table.tableName)}}</span>
					   </span>
                    <span class="describe" :title="table.tableComment">{{interceptStr(table.tableComment)}}</span>
                </li>

            </ul>
            <div class="button_box">
                <button v-on:click="downloadDoc">导出word</button>
                <button class="warn_btn" v-on:click="returnIndex">返回</button>
            </div>
        </form>
    </div>
</div>
<!--隐藏表单-->
<form action="/downloadDoc" method="post" id="downloadDoc">
    <div style="float: right;">
        <input type="hidden" class="form-control" name="tableNames" id="tableNames">
        <input type="hidden" class="form-control" name="ip" value="${bean.ip}" id="ip">
        <input type="hidden" class="form-control" name="port" value="${bean.port}" id="port">
        <input type="hidden" class="form-control" name="user" value="${bean.user}" id="user">
        <input type="hidden" class="form-control" name="pwd" value="${bean.pwd}" id="pwd">
        <input type="hidden" class="form-control" name="database" value="${bean.database}" id="database">
        <input type="hidden" class="form-control" name="databaseName" value="${bean.databaseName}" id="databaseName">
    </div>
</form>
<script src="http://code.jquery.com/jquery-2.2.4.js" integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI="
        crossorigin="anonymous"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="https://cdn.staticfile.org/vue/2.2.2/vue.min.js"></script>
<script src="static/js/vue.js" type="text/javascript" charset="utf-8"></script>
<script src="static/js/modal_dialog.js"></script>
<script src="static/js/common.js"></script>
<script>
    new Vue({
        el: "#app",
        data: {
            tables: [],
            checkData: [],
            ip: "",
            port: "",
            user: "",
            pwd: "",
            database: "",
            databaseName: ""
        },
        created: function () {
            this.initTable();
        },
        methods: {
            initTable: function () {
                this.tables = asyncGet("selectByTableName", {
                    "ip": $("#ip").val(),
                    "port": $("#port").val(),
                    "user": $("#user").val(),
                    "pwd": $("#pwd").val(),
                    "database": $("#database").val(),
                    "databaseName": $("#databaseName").val()
                });
            },
            checkAll: function (event) { // 点击全选事件函数
                var checkObj = $('.checkItem'); // 获取所有checkbox项
                if (event.target.checked) { // 判定全选checkbox的勾选状态
                    for (var i = 0; i < checkObj.length; i++) {
                        if (!checkObj[i].checked) { // 将未勾选的checkbox选项push到绑定数组中
                            this.checkData.push(checkObj[i].value);
                        }
                    }
                } else { // 如果是去掉全选则清空checkbox选项绑定数组
                    this.checkData = [];
                }
            },
            downloadDoc: function () {
                var tableNames = this.checkData;
                if (tableNames.length < 1) {
                    showAlert('info', '请选择至少一张表！');
                    return;
                }
                $('#tableNames').val(tableNames);
                $('#downloadDoc').submit();
            },
            interceptStr: function (value) {
                if (value.length <= 25) {
                    return value;
                }
                return value.substring(0, 25) + "...";
            },
            returnIndex: function () {
                location.href = "/";
            }
        }
    })

    //禁止表单提交
    var form = document.getElementsByTagName('form')[0];
    form.addEventListener('submit', function (e) {
        e.preventDefault();
    });

    //弹框
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
</body>
</html>