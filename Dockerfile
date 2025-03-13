# Etapa 1: Construção da aplicação e testes
FROM maven:3.9.6-eclipse-temurin AS builder
WORKDIR /app

# Copia apenas o arquivo de dependências para otimizar cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o restante do código
COPY src ./src

# Build do JAR (sem rodar testes, para ser usado pela aplicação)
RUN mvn clean package -DskipTests

# Etapa 2A: Ambiente para execução dos TESTES
FROM maven:3.9.6-eclipse-temurin AS test-runner
WORKDIR /app

# Copia a aplicação da etapa anterior
COPY --from=builder /app /app

# Executa os testes ao rodar este container
ENTRYPOINT ["sh", "-c", "mvn clean verify"]

# Etapa 2B: Ambiente final para execução da APLICAÇÃO
FROM eclipse-temurin:21-jdk AS production
WORKDIR /app

# Copia apenas o JAR final para a aplicação
COPY --from=builder /app/target/*.jar app.jar

# Inicia a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]