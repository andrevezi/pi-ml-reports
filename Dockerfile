FROM openjdk:11
MAINTAINER group9
VOLUME /tmp
EXPOSE 8088
ADD target/report-0.0.1-SNAPSHOT.jar report.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/report.jar"]