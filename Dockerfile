# Estágio de compilação
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copie apenas o arquivo pom.xml para aproveitar o cache do Docker
COPY pom.xml .
# Copie os arquivos do projeto
COPY src ./src

# Compile o projeto
RUN mvn clean package -DskipTests

# Estágio de produção
FROM adoptopenjdk:17-jre-hotspot
WORKDIR /app

# Copie os arquivos compilados do estágio de compilação
COPY --from=build /app/target/classes /app
CMD ["java", "-cp", ".:api.jar", "com.back.api.ApiApplication"]