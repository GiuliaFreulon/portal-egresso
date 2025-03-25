# Usar imagem base do Java
FROM eclipse-temurin:21-jdk-jammy

# Diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR (ajuste o nome do seu arquivo)
COPY target/PortalEgresso-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta da aplicação
EXPOSE 8080

# Comando para executar a aplicação
CMD ["java", "-jar", "app.jar"]