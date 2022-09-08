FROM adoptopenjdk/openjdk11
COPY target/demo-0.0.1-SNAPSHOT.jar /usr/app/demo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/usr/app/demo.jar"]
