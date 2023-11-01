FROM openjdk:17

WORKDIR /app

COPY /build/libs/artx.jar /app/artx.jar

CMD ["java", "-jar", "/app/artx.jar"]