# Stage 1 - Build com Maven e JDK 17 (versão compatível)
FROM maven:3.8.7-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2 - Imagem final com Temurin 17
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/target/PortalEgresso-*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]