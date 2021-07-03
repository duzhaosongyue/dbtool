FROM openjdk:8
# 镜像是从 openjdk:8-jdk-alpin 继承而来的

VOLUME /root/tmp
# 表示挂载了 /root/tmp 目录到容器中

ADD target/dbtool-0.0.1-SNAPSHOT.jar apprun.jar
# 将bootJar 添加到镜像中根目录下 命令为 apprun.jar


ENTRYPOINT ["java","-jar","/apprun.jar"]
# ENTRYPOINT 在容器启动后执行 java 命令来运行程序

# 设置容器时间
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# ======= 其它的一些Dockerfile命令 ========== 这里我们没有用到不过还是提一下
#COPY package.json /usr/src/app/
#ADD 更高级的复制文件
#ADD 指令和 COPY 的格式和性质基本一致。但是在 COPY 基础上增加了一些功能。
#CMD 指令就是用于指定默认的容器主进程的启动命令的。
#ENV 设置环境变量
#HEALTHCHECK 健康检查
#EXPOSE 指令是声明运行时容器提供服务端口，这只是一个声明，在运行时并不会因为这个声明应用就会开启这个端口的服务
EXPOSE 8000