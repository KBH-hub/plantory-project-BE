FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle* settings.gradle* ./

RUN chmod +x gradlew \
 && sed -i 's/\r$//' gradlew \
 && ./gradlew dependencies --no-daemon || true

COPY . .

RUN chmod +x gradlew \
 && sed -i 's/\r$//' gradlew \
 && ./gradlew clean bootJar -x test --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 9000
ENTRYPOINT ["java","-jar","/app/app.jar"]
