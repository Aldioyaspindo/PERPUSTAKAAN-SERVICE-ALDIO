# --- Tahap 1: BUILD (Memasak Kodingan) ---
# Kita pakai image Maven untuk compile Java
FROM maven:3.8.5-openjdk-17 AS builder

# Set folder kerja di dalam container
WORKDIR /app

# Copy file pom.xml dan source code dari repo ke dalam container
COPY pom.xml .
COPY src ./src

# Perintah ajaib untuk mengubah kodingan mentah jadi .jar
# (Skip test biar cepat dan tidak error memori)
RUN mvn clean package -DskipTests

# --- Tahap 2: RUNTIME (Menjalankan Aplikasi) ---
# Kita pakai image Java yang ringan untuk menjalankan hasilnya
FROM openjdk:17-jdk-slim

# Set folder kerja
WORKDIR /app

# COPY hasil jar DARI Tahap 1 (builder) ke Tahap 2
# Perhatikan bagian --from=builder
COPY --from=builder /app/target/*.jar app.jar

# Buka port (sesuaikan port aplikasi Anda)
EXPOSE 8081

# Jalankan aplikasi
ENTRYPOINT ["java", "-jar", "app.jar"]