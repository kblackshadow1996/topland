FROM openjdk:11.0.4

# 安装wkhtmltopdf
RUN mkdir /usr/local/data
COPY libs/wkhtmltox-0.12.6-1.rpm /usr/local/data/wkhtmltox.rpm
RUN yum install -y /usr/local/data/wkhtmltox.rpm

# 缓存位置
VOLUME /usr/local/tmp

ADD build/libs/topland-1.0.0.jar topland.jar

ENTRYPOINT ("java", "-jar", "-Xmx1024m", "-Xms1024m", "topland.jar")