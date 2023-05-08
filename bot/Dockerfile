FROM openjdk:19-oracle

WORKDIR /app

COPY bot/target/bot-1.0-SNAPSHOT.jar /app/bot.jar

ENTRYPOINT ["java", "-jar", "bot.jar"]