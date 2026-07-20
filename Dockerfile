# ---------- STAGE 1: build ----------
FROM gradle:8.8-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle build -x test --no-daemon

# ---------- STAGE 2: run ----------
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/build/libs/discografia-1.war app.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.war"]