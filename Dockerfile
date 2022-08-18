FROM openjdk:19
ARG JAVA_ARGS=
ENV JAVA_ARGS ${JAVA_ARGS}
#ARG JAR_FILE=target/*.jar
ARG JAR_FILE=build/libs/fella-0.0.1-SNAPSHOT.jar
ADD docker/docker-entrypoint.sh /
COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-server" ${JAVA_ARGS},"-jar","/app.jar"]

ENTRYPOINT ["/docker-entrypoint.sh"]
CMD java -server ${JAVA_ARGS} -jar /app.jar
