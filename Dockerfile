FROM arm64v8/openjdk
EXPOSE 8080
ADD target/daily-costs-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]