FROM gradle:jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM tomcat:10-jdk17
COPY --from=build /home/gradle/src/build/libs/*.war /usr/local/tomcat/webapps/myapp.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
