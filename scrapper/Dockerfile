FROM openjdk:19-oracle

WORKDIR /app

COPY target/scrapper-1.0-SNAPSHOT.jar /app/scrapper.jar

ENTRYPOINT ["java", "-jar", "scrapper.jar"]