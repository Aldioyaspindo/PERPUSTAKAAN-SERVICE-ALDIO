# 📚 Library Service (Service Perpustakaan)

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![H2](https://img.shields.io/badge/H2-0000BB?style=for-the-badge&logo=database&logoColor=white)

**Microservice untuk manajemen data anggota dalam sistem perpustakaan**

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

Proyek ini adalah implementasi **Service anggota**, bagian pertama dari sistem Perpustakaan berbasis **Microservices**. Dibangun menggunakan **Java Spring Boot**, service ini menyediakan RESTful API untuk mengelola data anggota secara lengkap.

### Apa itu Java Spring Boot?

**Java Spring Boot** adalah framework berbasis Java yang bersifat open-source, digunakan untuk membuat aplikasi stand-alone, production-grade, dan mudah dijalankan. Spring Boot sangat populer untuk membangun arsitektur Microservices karena:

- ✅ **Auto-configuration**: Konfigurasi otomatis yang mempercepat development
- ✅ **Embedded Server**: Server tertanam seperti Tomcat, tanpa perlu instalasi eksternal
- ✅ **Production-ready**: Siap deploy dengan monitoring dan health check bawaan
- ✅ **Ekosistem Lengkap**: Integrasi mudah dengan berbagai library dan tools

Dengan Spring Boot, pengembang dapat fokus pada logika bisnis tanpa dipusingkan oleh konfigurasi infrastruktur yang rumit.

---

## 🚀 Fitur Utama

Service anggota ini menyediakan RESTful API untuk mengelola data anggota dengan operasi CRUD lengkap:

| Fitur | Deskripsi |
|-------|-----------|
| **➕ Tambah Anggota** | Menambahkan data anggota baru (menambahkan nama, nim, alamat dan jenis kelamin) |
| **📖 Lihat Semua** | Mengambil daftar seluruh anggota yang ada |
| **🔍 Cari per ID** | Mengambil detail data anggota berdasarkan ID spesifik |
| **✏️ Update Anggota** | Memperbarui informasi anggota yang sudah ada |
| **🗑️ Hapus Anggota** | Menghapus data anggota dari database |

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

Berisi skema database untuk tabel anggota dengan atribut:
- `id` - Primary key (auto-generated)
- `nama` - nama anggota
- `nim` - nomor induk mahasiswa
- `alamat` - alamat anggota
- `Jenis_kelamin` - jenis kelamin anggota

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
  port: 8082                # Port aplikasi berjalan
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
http://localhost:8080/api/anggota
```

### Daftar Endpoint

| Method | Endpoint | Deskripsi | Request Body |
|--------|----------|-----------|--------------|
| `POST` | `/api/anggota` | Menambahkan anggota baru | ✅ Required |
| `GET` | `/api/anggota` | Mengambil semua data anggota | - |
| `GET` | `/api/anggota/{id}` | Mengambil data anggota berdasarkan ID | - |
| `PUT` | `/api/anggota/{id}` | Mengupdate data anggota | ✅ Required |
| `DELETE` | `/api/anggota/{id}` | Menghapus data anggota | - |

### Request/Response Examples

#### 1. Tambah anggota Baru

**Request:**
```http
POST /api/anggota
Content-Type: application/json

{
  "nama": "Aldio",
  "nim": "2311081025",
  "alamat": "Padang",
  "jenik_kelamin": LK
}
```

**Response:**
```json
{
  "id": 1,
  "nama": "Aldio",
  "nim": "2311081025",
  "alamat": "Padang",
  "jenik_kelamin": LK
}
```

#### 2. Lihat Semua anggota

**Request:**
```http
GET /api/anggota
```

**Response:**
```json
[
  {
    "id": 1,
    "nama": "Aldio",
    "nim": "2311081025",
    "alamat": "Padang",
    "jenik_kelamin": LK
  },
  {
    "id": 2,
    "nama": "Yaspindo",
    "nim": "2311081028",
    "alamat": "Pasar Baru",
    "jenik_kelamin": LK
  }
]
```

#### 3. Cari anggota per ID

**Request:**
```http
GET /api/anggota/1
```

**Response:**
```json
{
  "id": 1,
  "nama": "Aldio",
  "nim": "2311081025",
  "alamat": "Padang",
  "jenik_kelamin": LK
}
```

#### 4. Update anggota

**Request:**
```http
PUT /api/anngota/1
Content-Type: application/json

{
  "nama": "Aldio Yaspindo",
  "nim": "2311081025",
  "alamat": "Padang",
  "jenik_kelamin": LK
}
```

**Response:**
```json
{
  "id": 1,
  "nama": "Aldio Yaspindo",
  "nim": "2311081025",
  "alamat": "Padang",
  "jenik_kelamin": LK
}
```

#### 5. Hapus anggota

**Request:**
```http
DELETE /api/anggota/1
```

**Response:**
```http
HTTP/1.1 204 No Content
```

---

## 💻 Cara Menggunakan

### Menggunakan cURL

```bash
# Tambah anggota baru
curl -X POST http://localhost:8080/api/anggota \
  -H "Content-Type: application/json" \
  -d '{"nama": "Aldio", "nim": "2311081025", "alamat": "Padang", "jenik_kelamin": LK}'

# Lihat semua anggota
curl http://localhost:8081/api/anggota

# Lihat anggota dengan ID 1
curl http://localhost:8081/api/anggota/1

# Update anggota dengan ID 1
curl -X PUT http://localhost:8081/api/anggota/1 \
  -H "Content-Type: application/json" \
  -d '{"nama": "Aldio Yaspindo", "nim": "2311081025", "alamat": "Padang", "jenik_kelamin": LK}'

# Hapus anggota dengan ID 1
curl -X DELETE http://localhost:8081/api/anggota/1
```

### Menggunakan Postman

1. Import collection atau buat request manual
2. Atur method dan URL sesuai endpoint
3. Untuk POST/PUT, tambahkan JSON body
4. Klik **Send**

---
