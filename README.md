# 📚 Library Service (Service Perpustakaan)

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![H2](https://img.shields.io/badge/H2-0000BB?style=for-the-badge&logo=database&logoColor=white)

**Microservice untuk manajemen data buku dalam sistem perpustakaan**

[Fitur](#-fitur-utama) • [Teknologi](#️-teknologi--dependensi) • [Instalasi](#️-instalasi) • [API Endpoints](#-api-endpoints) • [Pengembangan](#-rencana-pengembangan)

</div>

---

## 📋 Daftar Isi

- [Tentang Proyek](#-tentang-proyek)
- [Fitur Utama](#-fitur-utama)
- [Teknologi & Dependensi](#️-teknologi--dependensi)
- [Arsitektur Aplikasi](#️-arsitektur-aplikasi)
- [Instalasi](#️-instalasi)
- [Konfigurasi](#-konfigurasi)
- [API Endpoints](#-api-endpoints)
- [Cara Menggunakan](#-cara-menggunakan)
- [Rencana Pengembangan](#-rencana-pengembangan)
- [Kontribusi](#-kontribusi)

---

## 📖 Tentang Proyek

Proyek ini adalah implementasi **Service Buku**, bagian pertama dari sistem Perpustakaan berbasis **Microservices**. Dibangun menggunakan **Java Spring Boot**, service ini menyediakan RESTful API untuk mengelola data buku secara lengkap.

### Apa itu Java Spring Boot?

**Java Spring Boot** adalah framework berbasis Java yang bersifat open-source, digunakan untuk membuat aplikasi stand-alone, production-grade, dan mudah dijalankan. Spring Boot sangat populer untuk membangun arsitektur Microservices karena:

- ✅ **Auto-configuration**: Konfigurasi otomatis yang mempercepat development
- ✅ **Embedded Server**: Server tertanam seperti Tomcat, tanpa perlu instalasi eksternal
- ✅ **Production-ready**: Siap deploy dengan monitoring dan health check bawaan
- ✅ **Ekosistem Lengkap**: Integrasi mudah dengan berbagai library dan tools

Dengan Spring Boot, pengembang dapat fokus pada logika bisnis tanpa dipusingkan oleh konfigurasi infrastruktur yang rumit.

---

## 🚀 Fitur Utama

Service Buku ini menyediakan RESTful API untuk mengelola data buku dengan operasi CRUD lengkap:

| Fitur | Deskripsi |
|-------|-----------|
| **➕ Tambah Buku** | Menambahkan data buku baru (Judul, Pengarang, Penerbit, Tahun Terbit) |
| **📖 Lihat Semua** | Mengambil daftar seluruh buku yang tersedia |
| **🔍 Cari per ID** | Mengambil detail data buku berdasarkan ID spesifik |
| **✏️ Update Buku** | Memperbarui informasi buku yang sudah ada |
| **🗑️ Hapus Buku** | Menghapus data buku dari database |

---

## 🛠️ Teknologi & Dependensi

### Tech Stack

| Komponen | Teknologi | Versi |
|----------|-----------|-------|
| **Bahasa** | Java | 17+ |
| **Framework** | Spring Boot | 3.x.x |
| **Build Tool** | Maven | - |
| **Database** | H2 Database | In-Memory |

### Dependensi Utama

```xml
<dependencies>
    <!-- Spring Web: Untuk membangun RESTful API -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Spring Data JPA: Untuk abstraksi database dan query -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <!-- Lombok: Untuk mengurangi boilerplate code -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    
    <!-- H2 Database: Database ringan untuk testing/development -->
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
    
    <!-- DevTools: Untuk fitur live reload saat pengembangan -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
</dependencies>
```

---

## 🏗️ Arsitektur Aplikasi

Aplikasi ini mengikuti pola **Layered Architecture** untuk pemisahan tanggung jawab yang jelas:

```
┌─────────────────────────────────────────┐
│           Controller Layer              │
│  (Mengatur routing & HTTP request)      │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│            Service Layer                │
│  (Business logic & validasi)            │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│          Repository Layer               │
│  (Akses database via JPA)               │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│            Model Layer                  │
│  (Entity & skema database)              │
└─────────────────────────────────────────┘
```

### 1. Model (Entity)

Berisi skema database untuk tabel Buku dengan atribut:
- `id` - Primary key (auto-generated)
- `judul` - Judul buku
- `pengarang` - Nama pengarang
- `penerbit` - Nama penerbit
- `tahunTerbit` - Tahun terbit buku

### 2. Repository

Interface yang meng-extend `JpaRepository`, berfungsi sebagai jembatan antara aplikasi dengan database. Menyediakan method bawaan untuk operasi CRUD tanpa perlu menulis query manual.

### 3. Service

Berisi business logic aplikasi. Mengelola aliran data antara Controller dan Repository, serta melakukan validasi dan transformasi data jika diperlukan.

### 4. Controller

Mengatur routing endpoint API. Menerima request dari client dan mengembalikan response dalam format JSON.

---

## ⚙️ Instalasi

### Prasyarat

Pastikan sistem Anda memiliki:
- ✅ **Java JDK 17** atau versi lebih tinggi
- ✅ **Maven 3.6+** (atau gunakan Maven wrapper yang disediakan)
- ✅ **Git** (untuk clone repository)

### Langkah Instalasi

1. **Clone repository**
   ```bash
   git clone https://github.com/username/library-service.git
   cd library-service
   ```

2. **Build project**
   ```bash
   mvn clean install
   ```

3. **Jalankan aplikasi**
   ```bash
   mvn spring-boot:run
   ```

4. **Verifikasi aplikasi berjalan**
   
   Buka browser dan akses: `http://localhost:8080`

### Menggunakan IDE

**IntelliJ IDEA / Eclipse:**
1. Import project sebagai Maven project
2. Tunggu hingga dependency selesai diunduh
3. Jalankan class `LibraryServiceApplication.java`

---

## 📝 Konfigurasi

Konfigurasi aplikasi terdapat pada berkas `src/main/resources/application.yml`:

```yaml
spring:
  application:
    name: library-service
  
  # Konfigurasi Database H2
  datasource:
    url: jdbc:h2:mem:librarydb
    driverClassName: org.h2.Driver
    username: sa
    password: password
  
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: update      # Membuat schema otomatis
    show-sql: true          # Menampilkan query SQL di console

  # Mengaktifkan H2 Console (Akses via browser)
  h2:
    console:
      enabled: true
      path: /h2-console

server:
  port: 8080                # Port aplikasi berjalan
```

### Akses H2 Console

Untuk melihat data di database H2:

1. Buka browser: `http://localhost:8080/h2-console`
2. Masukkan konfigurasi:
   - **JDBC URL**: `jdbc:h2:mem:librarydb`
   - **Username**: `sa`
   - **Password**: `password`
3. Klik **Connect**

---

## 🔌 API Endpoints

### Base URL
```
http://localhost:8080/api/books
```

### Daftar Endpoint

| Method | Endpoint | Deskripsi | Request Body |
|--------|----------|-----------|--------------|
| `POST` | `/api/books` | Menambahkan buku baru | ✅ Required |
| `GET` | `/api/books` | Mengambil semua data buku | - |
| `GET` | `/api/books/{id}` | Mengambil data buku berdasarkan ID | - |
| `PUT` | `/api/books/{id}` | Mengupdate data buku | ✅ Required |
| `DELETE` | `/api/books/{id}` | Menghapus data buku | - |

### Request/Response Examples

#### 1. Tambah Buku Baru

**Request:**
```http
POST /api/books
Content-Type: application/json

{
  "judul": "Laskar Pelangi",
  "pengarang": "Andrea Hirata",
  "penerbit": "Bentang Pustaka",
  "tahunTerbit": 2005
}
```

**Response:**
```json
{
  "id": 1,
  "judul": "Laskar Pelangi",
  "pengarang": "Andrea Hirata",
  "penerbit": "Bentang Pustaka",
  "tahunTerbit": 2005
}
```

#### 2. Lihat Semua Buku

**Request:**
```http
GET /api/books
```

**Response:**
```json
[
  {
    "id": 1,
    "judul": "Laskar Pelangi",
    "pengarang": "Andrea Hirata",
    "penerbit": "Bentang Pustaka",
    "tahunTerbit": 2005
  },
  {
    "id": 2,
    "judul": "Bumi Manusia",
    "pengarang": "Pramoedya Ananta Toer",
    "penerbit": "Hasta Mitra",
    "tahunTerbit": 1980
  }
]
```

#### 3. Cari Buku per ID

**Request:**
```http
GET /api/books/1
```

**Response:**
```json
{
  "id": 1,
  "judul": "Laskar Pelangi",
  "pengarang": "Andrea Hirata",
  "penerbit": "Bentang Pustaka",
  "tahunTerbit": 2005
}
```

#### 4. Update Buku

**Request:**
```http
PUT /api/books/1
Content-Type: application/json

{
  "judul": "Laskar Pelangi (Edisi Revisi)",
  "pengarang": "Andrea Hirata",
  "penerbit": "Bentang Pustaka",
  "tahunTerbit": 2008
}
```

**Response:**
```json
{
  "id": 1,
  "judul": "Laskar Pelangi (Edisi Revisi)",
  "pengarang": "Andrea Hirata",
  "penerbit": "Bentang Pustaka",
  "tahunTerbit": 2008
}
```

#### 5. Hapus Buku

**Request:**
```http
DELETE /api/books/1
```

**Response:**
```http
HTTP/1.1 204 No Content
```

---

## 💻 Cara Menggunakan

### Menggunakan cURL

```bash
# Tambah buku baru
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{"judul":"Laskar Pelangi","pengarang":"Andrea Hirata","penerbit":"Bentang Pustaka","tahunTerbit":2005}'

# Lihat semua buku
curl http://localhost:8080/api/books

# Lihat buku dengan ID 1
curl http://localhost:8080/api/books/1

# Update buku dengan ID 1
curl -X PUT http://localhost:8080/api/books/1 \
  -H "Content-Type: application/json" \
  -d '{"judul":"Laskar Pelangi (Edisi Revisi)","pengarang":"Andrea Hirata","penerbit":"Bentang Pustaka","tahunTerbit":2008}'

# Hapus buku dengan ID 1
curl -X DELETE http://localhost:8080/api/books/1
```

### Menggunakan Postman

1. Import collection atau buat request manual
2. Atur method dan URL sesuai endpoint
3. Untuk POST/PUT, tambahkan JSON body
4. Klik **Send**

---
