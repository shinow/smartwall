FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD exam-service-1.0.jar app.jar
RUN sh -c 'touch /app.jar'
RUN echo "Asia/Shanghai" > /etc/timezone;
ENV JAVA_OPTS="-server -Xms64m -Xmx64m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=128m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]