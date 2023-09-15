FROM gradle:7.5.1-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home
WORKDIR /home
RUN gradle build --no-daemon

FROM openjdk:11
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/build/libs/*.jar /app/
CMD ["java", "-jar", "/app/core-0.0.1-SNAPSHOT.jar", "--server.tomcat.max-connections=16384", "--spring.profiles.active=prod", "-Djava.security.egd=file:/dev/./urandom"]