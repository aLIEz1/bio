FROM java:8
MAINTAINER zhangfuqi <1742720898@qq.com>
VOLUME /tmp
ADD bio-0.0.1-SNAPSHOT.jar bio.jar
ENTRYPOINT [ "sh", "-c", "java -jar /bio.jar"]