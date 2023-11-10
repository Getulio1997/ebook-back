# Estágio de compilação
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copie apenas o arquivo pom.xml para aproveitar o cache do Docker
COPY pom.xml .
# Copie os arquivos do projeto
COPY src ./src

# Compile o projeto
RUN mvn clean install

# Estágio de produção
FROM maven:3.8.4-openjdk-17
WORKDIR /app

# Copie os arquivos compilados do estágio de compilação
COPY --from=build /app/target/api-*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]