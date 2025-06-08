# 基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制构建好的 JAR 文件
COPY target/*.jar app.jar

# 暴露端口 (根据你的应用端口修改)
EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"]