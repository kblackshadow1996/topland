FROM adoptopenjdk/openjdk11:x86_64-centos-jdk-11.0.12_7

# 安装wkhtmltopdf
COPY libs/wkhtmltox-0.12.6-1.rpm /usr/local/data/wkhtmltox.rpm
RUN yum install -y /usr/local/data/wkhtmltox.rpm

COPY src/main/resources/fonts/微软雅黑.ttf /usr/local/data/fonts/微软雅黑.ttf
COPY src/main/resources/img/logo.png /usr/local/data/img/logo.png

ADD build/libs/topland-1.0.0.jar topland.jar

CMD java -jar -Xmx1024m -Xms1024m -Dfile.encoding=UTF-8 topland.jar