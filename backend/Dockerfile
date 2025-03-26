# Use imagem oficial com tag válida (Maven + JDK 17)
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Imagem final leve
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/PortalEgresso-*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]