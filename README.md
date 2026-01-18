# 📦 Microservice Repository - Perpustakaan

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)
![Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white)
![Eureka](https://img.shields.io/badge/Eureka-00ADD8?style=for-the-badge&logo=spring&logoColor=white)
![Jenkins](https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white)
![ELK](https://img.shields.io/badge/ELK_Stack-005571?style=for-the-badge&logo=elastic&logoColor=white)

**Repository untuk implementasi dan pembelajaran arsitektur microservice**

</div>

---

## 👤 Identitas

| Field | Detail |
|-------|--------|
| **Nama** | Muhamad Aldio Yaspindo |
| **NIM** | 2311081025 |
| **Kelas** | 3D TRPL |

---

## 📚 Deskripsi Repository

Selamat datang di repository **Microservice Perpustakaan** saya! 

Repository ini dibuat untuk mengimplementasikan dan mempelajari berbagai konsep arsitektur microservice dalam konteks sistem manajemen perpustakaan. Proyek ini mencakup implementasi lengkap dari berbagai teknologi modern yang umum digunakan dalam ekosistem microservice.

### 🎯 Sistem Perpustakaan

Repository ini terdiri dari **4 service utama**:

| Service | Deskripsi | Port |
|---------|-----------|------|
| **📖 Buku Service** | Mengelola data buku (CRUD, pencarian, kategori) | `8081` |
| **👥 Anggota Service** | Mengelola data anggota perpustakaan | `8082` |
| **📤 Peminjaman Service** | Mengelola transaksi peminjaman buku | `8083` |
| **📥 Pengembalian Service** | Mengelola transaksi pengembalian buku | `8084` |

---

## 🌿 Branch & Implementasi

Repository ini memiliki beberapa branch yang masing-masing digunakan untuk mengimplementasikan konsep microservice tertentu:

### 📂 Struktur Branch

```
main
├── rabbitmq-implementation      → Message Queue dengan RabbitMQ
├── eureka-implementation        → Service Discovery dengan Eureka
├── kafka-cqrs-implementation    → CQRS Pattern dengan Apache Kafka
├── jenkins-cicd                 → CI/CD Pipeline dengan Jenkins
└── elk-stack-implementation     → Monitoring & Logging dengan ELK
```

### 🔍 Detail Setiap Branch

#### 1️⃣ `rabbitmq-implementation`
Implementasi komunikasi antar service menggunakan **RabbitMQ** sebagai message broker.

**Fitur:**
- Async messaging antar service
- Event-driven communication
- Message queuing untuk reliability
- Publisher-Subscriber pattern

**Use Case:**
- Notifikasi saat peminjaman/pengembalian buku
- Update stok buku secara asynchronous
- Event logging untuk audit trail

---

#### 2️⃣ `eureka-implementation`
Implementasi **Netflix Eureka** untuk service discovery dan load balancing.

**Fitur:**
- Service registration otomatis
- Client-side load balancing
- Health check monitoring
- Dynamic service discovery

**Arsitektur:**
```
Eureka Server (8761)
    ├── Buku Service
    ├── Anggota Service
    ├── Peminjaman Service
    └── Pengembalian Service
```

---

#### 3️⃣ `kafka-cqrs-implementation`
Implementasi **CQRS (Command Query Responsibility Segregation)** menggunakan Apache Kafka.

**Fitur:**
- Pemisahan Command dan Query model
- Event sourcing dengan Kafka
- Real-time data streaming
- Eventual consistency

**Pattern:**
- **Command**: Write operations (Create, Update, Delete)
- **Query**: Read operations (Get, Search, List)
- **Event Store**: Kafka topics untuk menyimpan events

---

#### 4️⃣ `jenkins-cicd`
Implementasi **CI/CD Pipeline** menggunakan Jenkins dengan Docker.

**Fitur:**
- Multibranch Pipeline
- Automated build & test
- Docker containerization
- Auto-deployment per environment

**Pipeline Flow:**
```
Push Code → Jenkins Webhook → Build Docker Image → Run Tests → Deploy to Environment
```

**Environments:**
- `dev` branch → Deploy ke Development (port 808x)
- `staging` branch → Deploy ke Staging (port 809x)
- `main` branch → Deploy ke Production (port 80xx)

---

#### 5️⃣ `elk-stack-implementation`
Implementasi **ELK Stack** untuk centralized logging dan monitoring.

**Stack:**
- **Elasticsearch**: Data storage & search engine
- **Logstash**: Log processing & aggregation
- **Kibana**: Visualization & dashboard

**Fitur:**
- Centralized logging dari semua service
- Real-time log monitoring
- Custom dashboards untuk monitoring
- Log analysis & alerting

**Dashboard Kibana:**
- Request/Response logs
- Error rate monitoring
- Performance metrics
- Business metrics (jumlah peminjaman, pengembalian, dll)

---

## 🛠️ Teknologi Stack

### Backend Framework
- **Spring Boot 3.x** - Framework utama untuk microservice
- **Spring Cloud** - Tools untuk distributed systems
- **Maven** - Dependency management

### Message Brokers
- **RabbitMQ** - Message queue untuk async communication
- **Apache Kafka** - Event streaming platform untuk CQRS

### Service Discovery
- **Netflix Eureka** - Service registry & discovery

### CI/CD
- **Jenkins** - Automation server untuk CI/CD
- **Docker** - Containerization platform
- **Docker Compose** - Multi-container orchestration

### Monitoring & Logging
- **Elasticsearch** - Search & analytics engine
- **Logstash** - Log processing pipeline
- **Kibana** - Data visualization

### Database
- **MySQL** / **PostgreSQL** - Relational database untuk setiap service
- **MongoDB** (opsional) - NoSQL untuk event store

---

## 🎯 Tujuan Pembelajaran

Melalui repository ini, saya bertujuan untuk:

✅ **Memahami konsep microservice architecture**
   - Service decomposition
   - Database per service pattern
   - API Gateway pattern
   - Circuit breaker pattern

✅ **Mengimplementasikan message broker & service discovery**
   - Asynchronous communication dengan RabbitMQ
   - Event-driven architecture dengan Kafka
   - Service registration dengan Eureka
   - Client-side load balancing

✅ **Menerapkan CI/CD menggunakan Jenkins**
   - Automated build pipeline
   - Continuous integration
   - Continuous deployment
   - Infrastructure as Code

✅ **Monitoring dan logging menggunakan ELK Stack**
   - Centralized logging
   - Real-time monitoring
   - Log analysis dan troubleshooting
   - Performance metrics tracking

---

## 🚀 Quick Start

### Prasyarat
- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- Git

### Clone Repository

```bash
git clone https://github.com/username/microservice-perpustakaan.git
cd microservice-perpustakaan
```

### Checkout ke Branch yang Diinginkan

```bash
# Untuk RabbitMQ implementation
git checkout rabbitmq-implementation

# Untuk Eureka implementation
git checkout eureka-implementation

# Untuk Kafka CQRS implementation
git checkout kafka-cqrs-implementation

# Untuk Jenkins CI/CD
git checkout jenkins-cicd

# Untuk ELK Stack
git checkout elk-stack-implementation
```

### Jalankan Services

Setiap branch memiliki file `docker-compose.yml` untuk menjalankan semua services:

```bash
docker-compose up -d
```

---

## 📖 Dokumentasi Per Branch

Setiap branch memiliki dokumentasi lengkap di folder `/docs`:

```
docs/
├── SETUP.md           → Panduan instalasi dan konfigurasi
├── API_DOCS.md        → Dokumentasi API endpoints
├── ARCHITECTURE.md    → Penjelasan arsitektur sistem
└── TROUBLESHOOTING.md → Panduan troubleshooting
```

---

## 📊 Arsitektur Sistem

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────┐
│                     API Gateway                          │
│                    (Port 8080)                           │
└────────────┬────────────────────────────────────────────┘
             │
    ┌────────┴────────┐
    │                 │
┌───▼────┐      ┌────▼─────┐
│ Eureka │      │ Config   │
│ Server │      │ Server   │
│ (8761) │      │ (8888)   │
└────────┘      └──────────┘
    │
    ├─────────────┬─────────────┬─────────────┐
    │             │             │             │
┌───▼────┐   ┌───▼────┐   ┌───▼────┐   ┌───▼────┐
│  Buku  │   │Anggota │   │Pinjam  │   │Kembali │
│Service │   │Service │   │Service │   │Service │
│ (8081) │   │ (8082) │   │ (8083) │   │ (8084) │
└───┬────┘   └───┬────┘   └───┬────┘   └───┬────┘
    │            │            │            │
    └────────────┴────────────┴────────────┘
                      │
              ┌───────▼────────┐
              │   RabbitMQ     │
              │   / Kafka      │
              └───────┬────────┘
                      │
              ┌───────▼────────┐
              │   ELK Stack    │
              │                │
              │ Elasticsearch  │
              │   Logstash     │
              │    Kibana      │
              └────────────────┘
```

---

## 🔗 API Endpoints

### Buku Service (Port 8081)
```
GET    /api/buku              → List semua buku
GET    /api/buku/{id}         → Detail buku
POST   /api/buku              → Tambah buku baru
PUT    /api/buku/{id}         → Update buku
DELETE /api/buku/{id}         → Hapus buku
GET    /api/buku/search?q=    → Cari buku
```

### Anggota Service (Port 8082)
```
GET    /api/anggota           → List semua anggota
GET    /api/anggota/{id}      → Detail anggota
POST   /api/anggota           → Daftar anggota baru
PUT    /api/anggota/{id}      → Update anggota
DELETE /api/anggota/{id}      → Hapus anggota
```

### Peminjaman Service (Port 8083)
```
GET    /api/peminjaman                    → List peminjaman
POST   /api/peminjaman                    → Pinjam buku
GET    /api/peminjaman/anggota/{id}       → Riwayat peminjaman
GET    /api/peminjaman/active             → Peminjaman aktif
```

### Pengembalian Service (Port 8084)
```
POST   /api/pengembalian                  → Kembalikan buku
GET    /api/pengembalian/{id}             → Detail pengembalian
GET    /api/pengembalian/denda            → List denda
```

---

## 📝 Catatan Penting

- Setiap service memiliki database terpisah (Database per Service pattern)
- Service berkomunikasi melalui REST API dan Message Queue
- Setiap branch independen dan bisa dijalankan sendiri-sendiri
- Dokumentasi lengkap ada di setiap branch

---

## 🤝 Kontribusi

Repository ini adalah bagian dari pembelajaran pribadi. Namun, saran dan feedback sangat diterima!

---

<div align="center">

**⭐ Star repository ini jika bermanfaat untuk pembelajaran Anda!**

*Dibuat dengan ❤️ untuk pembelajaran Microservice Architecture*

**Last Updated: Januari 2026**

</div>
