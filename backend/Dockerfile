FROM gradle:8-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:17.0
EXPOSE 8081
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar", "--dbHost=${DB_HOST}", "--dbUser=${DB_USER}", "--dbPassword=${DB_PASSWORD}", "--serverIp=${NUXT_PUBLIC_SERVER_IP}"]
