# Estágio de compilação
FROM adoptopenjdk:11-jdk-hotspot as build
WORKDIR /app
COPY . .
RUN ./mvnw clean package

# Estágio de produção
FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=build /app/target/classes /app
CMD ["java", "-cp", ".:api.jar", "com.back.api.ApiApplication"]
