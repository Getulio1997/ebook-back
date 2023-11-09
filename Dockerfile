# Estágio de compilação
FROM adoptopenjdk:11-jdk-hotspot as build
WORKDIR /app
COPY . .
RUN chmod +x mvnw && ./mvnw clean package

# Instalando o Maven
RUN apt-get update
RUN apt-get install -y maven

# Estágio de produção
FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=build /app/target/classes /app
CMD ["java", "-cp", ".:api.jar", "com.back.api.ApiApplication"]


