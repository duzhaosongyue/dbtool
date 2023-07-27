# dbtool
dbtool-数据库文档生成工具,通过对数据库连接信息配置可以直接生成数据库的结构文档。其内容包括：表名、表注释、字段、类型、长度、字段描述、索引、组合索引等。
>目前支持mysql5.6、5.7、8.0、oracle11g、达梦数据库、postgresql数据库。
需要jdk1.8以上的环境支持。

演示地址: http://www.duzhaosongyue.com:8000

可以直接使用的包百度网盘下载: 链接: https://pan.baidu.com/s/1DjVXbt2fCm4P3fxYggFZ3A 提取码: rt2g

jar包运行:

     java -jar dbtool-0.0.1-SNAPSHOT.jar

docker运行:

       docker pull docker pull duzhaosongyue/dbtool:v1.0.0.4
       docker run -d -p 8000:8000 --name dbtool duzhaosongyue/dbtool:v1.0.0.4

默认访问地址:  http://localhost:8000


效果图:

![配置链接](https://duzhaosongyue-1300426457.cos.ap-nanjing.myqcloud.com/git/1625305108539.jpg "配置链接")

![选择导出](https://duzhaosongyue-1300426457.cos.ap-nanjing.myqcloud.com/git/1625305113399.jpg "选择导出")

![导出结果](https://duzhaosongyue-1300426457.cos.ap-nanjing.myqcloud.com/git/1625305117414.jpg "导出结果")
