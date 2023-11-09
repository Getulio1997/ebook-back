# Use a imagem oficial do OpenJDK como a imagem base
FROM adoptopenjdk:11-jre-hotspot

# Defina o diretório de trabalho no contêiner
WORKDIR /app

# Copie o arquivo JAR para o contêiner (o nome do JAR pode variar)
COPY target/api-0.0.1-SNAPSHOT.jar /app/api.jar

# Comando para executar a aplicação quando o contêiner iniciar
CMD ["java", "-jar", "api.jar"]
