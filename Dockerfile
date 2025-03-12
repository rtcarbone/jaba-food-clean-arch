# Etapa 1: Construção da aplicação
FROM maven:3.9.6-eclipse-temurin AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

# Pacote da aplicação sem rodar testes
RUN mvn clean package -DskipTests

# Etapa 2: Ambiente para execução dos testes
FROM maven:3.9.6-eclipse-temurin
WORKDIR /app

COPY --from=builder /app /app

# Permite rodar os testes no entrypoint
ENTRYPOINT ["mvn", "clean", "verify"]