FROM openjdk:11.0.4

# 安装wkhtmltopdf
RUN apt-get update && \
    apt-get -yq update && \
    apt-get install -y xvfb && \
    apt-get install -y xvfb libxfont1 xfonts-encodings xfonts-utils xfonts-base xfonts-75dpi && \
    apt-get install -y wkhtmltopdf \
RUN apt-get install -y jdk

ADD build/libs/topland-1.0.0-plain.jar topland.jar

ENTRYPOINT ("java", "-jar", "-Xmx1024m","-Xms1024m", "/topland.jar")