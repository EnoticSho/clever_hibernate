FROM tomcat:10-jdk17

ADD build/libs/*.war /usr/local/tomcat/webapps/myapp.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
