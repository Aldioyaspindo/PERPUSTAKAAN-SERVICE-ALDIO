# 🚀 Jenkins CI/CD Pipeline untuk Spring Boot Microservices

<div align="center">

![Jenkins](https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Windows](https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white)

**Pipeline CI/CD otomatis yang dioptimalkan untuk laptop Windows dengan RAM terbatas**

[Fitur](#-fitur) • [Mulai Cepat](#-mulai-cepat) • [Dokumentasi](#-dokumentasi) • [Pemecahan Masalah](#-pemecahan-masalah)

</div>

---

## 📋 Daftar Isi

- [Gambaran Umum](#-gambaran-umum)
- [Fitur](#-fitur)
- [Prasyarat](#-prasyarat)
- [Mulai Cepat](#-mulai-cepat)
- [Pengaturan Lengkap](#-pengaturan-lengkap)
- [Struktur Proyek](#-struktur-proyek)
- [Penggunaan](#-penggunaan)
- [Pemecahan Masalah](#-pemecahan-masalah)
- [Praktik Terbaik](#-praktik-terbaik)

---

## 🎯 Gambaran Umum

Repositori ini menyediakan panduan lengkap untuk mengatur pipeline Jenkins CI/CD yang berjalan di Docker pada Windows, khususnya dioptimalkan untuk laptop dengan RAM terbatas (4GB+). Pipeline ini mengotomatiskan proses build dan deployment untuk microservices Spring Boot.

### Mengapa Menggunakan Pengaturan Ini?

- **💾 Efisien Memori**: Pembatasan memori WSL2 mencegah sistem hang
- **🐳 Docker-in-Docker**: Jenkins dapat membangun image Docker secara native
- **📦 Multi-Stage Builds**: Image akhir di bawah 200MB
- **🔄 Dukungan Multi-Branch**: Deployment otomatis untuk branch dev, staging, dan production
- **⚡ Build Cepat**: Caching dependency Maven mengurangi waktu build hingga 70%

---

## ✨ Fitur

- ✅ **Pembuatan image Docker otomatis** untuk setiap microservice
- ✅ **Dukungan pipeline multi-branch** (deteksi otomatis)
- ✅ **Konfigurasi yang dioptimalkan memori** untuk mesin spesifikasi rendah
- ✅ **Multi-stage Docker builds** untuk ukuran image minimal
- ✅ **Deployment container otomatis** setelah build berhasil
- ✅ **Integrasi GitHub** dengan dukungan webhook
- ✅ **Pelacakan status build** dan pencatatan console
- ✅ **Konfigurasi persisten** lintas restart container

---

## 🔧 Prasyarat

### Kebutuhan Perangkat Lunak

| Alat | Versi | Tujuan |
|------|---------|---------|
| **Windows** | 10/11 | Sistem operasi host |
| **WSL 2** | Terbaru | Backend Docker |
| **Docker Desktop** | 4.0+ | Runtime container |
| **Git** | 2.0+ | Kontrol versi |

### Kebutuhan Perangkat Keras

| Komponen | Minimum | Direkomendasikan |
|-----------|---------|-------------|
| **RAM** | 4GB | 8GB+ |
| **Penyimpanan** | 10GB tersedia | 20GB+ tersedia |
| **CPU** | 2 core | 4 core |

---

## 🚀 Mulai Cepat

### 1. Batasi Memori WSL2

Buat berkas `C:\Users\[NamaAnda]\.wslconfig`:

```toml
[wsl2]
memory=4GB
processors=2
```

Restart WSL:

```powershell
wsl --shutdown
```

### 2. Bangun Image Jenkins Kustom

```powershell
mkdir C:\jenkins-setup
cd C:\jenkins-setup
```

Buat berkas `Dockerfile`:

```dockerfile
FROM jenkins/jenkins:lts
USER root

RUN apt-get update && \
    apt-get install -y lsb-release && \
    curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg && \
    echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null && \
    apt-get update && \
    apt-get install -y docker-ce-cli

USER jenkins
```

Bangun dan jalankan:

```powershell
docker build -t jenkins-docker-windows .

docker run -d `
  --name jenkins-server `
  -u root `
  -p 8080:8080 -p 50000:50000 `
  -v //var/run/docker.sock:/var/run/docker.sock `
  -v jenkins_home:/var/jenkins_home `
  --memory="1g" `
  --cpus="1.0" `
  --restart=on-failure `
  jenkins-docker-windows
```

### 3. Akses Jenkins

Buka browser di `http://localhost:8080`

Dapatkan kata sandi awal:

```powershell
docker exec jenkins-server cat /var/jenkins_home/secrets/initialAdminPassword
```

### 4. Konfigurasi Jenkins

1. Pasang plugin minimal: **Git**, **Pipeline**, **Docker Pipeline**
2. Atur executor menjadi **1** (Manage Jenkins → Nodes → Built-In Node)
3. Tambahkan kredensial GitHub (Manage Jenkins → Credentials)

### 5. Atur Microservice Anda

Tambahkan ke root setiap service:

**`Dockerfile`**:

```dockerfile
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**`Jenkinsfile`**:

```groovy
pipeline {
    agent any
    environment {
        CONTAINER_NAME = 'my-service-prod'
        HOST_PORT = '9001'
        CONTAINER_PORT = '8081'
    }
    stages {
        stage('Checkout') {
            steps { checkout scm }
        }
        stage('Build Image') {
            steps {
                script {
                    sh "docker build -t my-service:${env.BRANCH_NAME} ."
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    sh "docker rm -f ${CONTAINER_NAME} || true"
                    sh """
                        docker run -d \
                        --name ${CONTAINER_NAME} \
                        --restart unless-stopped \
                        -p ${HOST_PORT}:${CONTAINER_PORT} \
                        my-service:${env.BRANCH_NAME}
                    """
                }
            }
        }
    }
}
```

### 6. Buat Job Pipeline

1. Dashboard Jenkins → **New Item**
2. Pilih **Multibranch Pipeline**
3. Konfigurasi repositori Git dan kredensial
4. Simpan dan lihat Jenkins mendeteksi branch secara otomatis!

---

## 📂 Struktur Proyek

```
repositori-anda/
├── service-user/
│   ├── src/
│   │   └── main/
│   │       └── java/
│   ├── pom.xml
│   ├── Dockerfile          ← Build multi-stage
│   └── Jenkinsfile         ← Definisi pipeline
├── service-product/
│   ├── src/
│   ├── pom.xml
│   ├── Dockerfile
│   └── Jenkinsfile
├── service-order/
│   └── ...
└── README.md
```

---

## 💻 Penggunaan

### Memicu Build Secara Manual

1. Buka Dashboard Jenkins
2. Pilih pipeline Anda
3. Pilih branch → **Build Now**

### Build Otomatis

Konfigurasi webhook GitHub:

1. Repositori GitHub → Settings → Webhooks
2. Tambahkan webhook: `http://url-jenkins-anda:8080/github-webhook/`
3. Push kode → Jenkins akan build otomatis!

### Melihat Status Build

- **Biru** = Build sedang berjalan
- **Hijau** = Build berhasil ✅
- **Merah** = Build gagal ❌

Klik nomor build → **Console Output** untuk log detail.

---

## 🛠️ Pemecahan Masalah

<details>
<summary><b>🔴 UI Jenkins Terus Loading</b></summary>

```powershell
docker restart jenkins-server
# Tunggu 60 detik, lalu muat ulang browser
```
</details>

<details>
<summary><b>🔴 Permission Denied pada Docker Socket</b></summary>

```powershell
docker exec -u 0 -it jenkins-server chmod 666 /var/run/docker.sock
```
</details>

<details>
<summary><b>🔴 Build Gagal: "openjdk not found"</b></summary>

Gunakan base image yang benar:
```dockerfile
FROM eclipse-temurin:17-jre-alpine
```
</details>

<details>
<summary><b>🔴 Laptop Hang Saat Build</b></summary>

1. Kurangi memori WSL di `.wslconfig`:
   ```toml
   memory=3GB
   ```
2. Perbarui memori Jenkins:
   ```powershell
   docker update jenkins-server --memory="768m"
   docker restart jenkins-server
   ```
3. Pastikan executor = 1
</details>

<details>
<summary><b>🔴 Port Sudah Digunakan</b></summary>

```powershell
docker stop <nama-container-lama>
docker rm <nama-container-lama>
```
</details>

---

## 📚 Pengaturan Lengkap

Untuk panduan langkah demi langkah lengkap termasuk:

- Detail konfigurasi WSL2
- Penjelasan Dockerfile kustom
- Manajemen plugin Jenkins
- Pengaturan token GitHub
- Konfigurasi pipeline lanjutan

Lihat [dokumentasi lengkap](docs/SETUP_GUIDE.md).

---

## 🎓 Praktik Terbaik

### Manajemen Memori

- Tutup aplikasi yang tidak perlu saat build
- Build satu service pada satu waktu
- Jalankan `docker system prune -a` setiap minggu
- Pantau RAM di Task Manager → Performance → WSL

### Alur Kerja Pengembangan

```
feature-branch → dev → staging → main (production)
```

- Uji di branch `dev` terlebih dahulu
- Gunakan semantic versioning untuk image: `service:v1.0.0`
- Tag rilis production

### Rutinitas Pemeliharaan

- **Mingguan**: Bersihkan cache Docker
- **Dua Mingguan**: Restart container Jenkins
- **Bulanan**: Cadangkan volume `jenkins_home`
- **Kuartalan**: Perbarui versi Jenkins LTS

---

## 🔒 Catatan Keamanan

- Jenkins terekspos di `localhost:8080` secara bawaan (tidak publik)
- Untuk production, gunakan HTTPS dan otentikasi yang tepat
- Jaga keamanan token GitHub (jangan pernah commit ke repositori)
- Gunakan penyimpanan kredensial Jenkins untuk data sensitif

---

## 📊 Tolok Ukur Performa

| Metrik | Sebelum Optimasi | Setelah Optimasi |
|--------|-------------------|-------------------|
| Waktu Build | ~8 menit | ~3 menit |
| Ukuran Image | 550MB | 180MB |
| Penggunaan RAM | Tidak terbatas (8GB+) | Terbatas (1GB) |
| Laptop Hang | Sering | Tidak pernah |

---

## 🙏 Penghargaan

- [Dokumentasi Resmi Jenkins](https://www.jenkins.io/doc/)
- [Dokumentasi Docker](https://docs.docker.com/)
- [Panduan Spring Boot Docker](https://spring.io/guides/topicals/spring-boot-docker/)

---

<div align="center">

**⭐ Beri bintang repositori ini jika membantu Anda!**

Dibuat dengan ❤️ untuk pengembang dengan sumber daya terbatas

*Terakhir diperbarui: Desember 2024*

</div>
