# --- Tahap 1: BUILD (Memasak) ---
# Kita ganti base image ke Maven dengan Eclipse Temurin (Versi Alpine biar downloadnya cepat/kecil)
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Build menjadi .jar
RUN mvn clean package -DskipTests

# --- Tahap 2: RUNTIME (Menghidangkan) ---
# Kita ganti image runtime ke Eclipse Temurin JRE (Versi Alpine sangat ringan, cuma ~50MB)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Ambil hasil dari builder
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]