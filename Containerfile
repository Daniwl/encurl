# Estágio 1: Build
FROM maven:3.9-eclipse-temurin-17-alpine AS builder
WORKDIR /app

# Cache das dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Compilação
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Execução
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Segurança: utilizador sem privilégios de root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copia o JAR com o nome fixo definido no pom.xml
COPY --from=builder /app/target/encurl-app.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]