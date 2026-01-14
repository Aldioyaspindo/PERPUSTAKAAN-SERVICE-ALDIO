# 📚 Peminjaman Service (Service Perpustakaan)

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![H2](https://img.shields.io/badge/H2-0000BB?style=for-the-badge&logo=database&logoColor=white)

**Microservice untuk manajemen data peminjaman dalam sistem perpustakaan**

[Fitur](#-fitur-utama) • [Teknologi](#️-teknologi--dependensi) • [Instalasi](#️-instalasi) • [API Endpoints](#-api-endpoints) • [Pengembangan](#-rencana-pengembangan)

</div>

---

## 📋 Daftar Isi

- [Tentang Proyek](#-tentang-proyek)
- [Fitur Utama](#-fitur-utama)
- [Teknologi & Dependensi](#️-teknologi--dependensi)
- [Arsitektur Aplikasi](#️-arsitektur-aplikasi)
- [Langkah-langkah Pembuatan](#-langkah-langkah-pembuatan)
- [Konfigurasi](#-konfigurasi)
- [API Endpoints](#-api-endpoints)
- [Value Object (VO)](#-value-object-vo)
- [Cara Menggunakan](#-cara-menggunakan)
- [Rencana Pengembangan](#-rencana-pengembangan)

---

## 📖 Tentang Proyek

Proyek ini adalah implementasi **Service Peminjaman**, bagian dari sistem Perpustakaan berbasis **Microservices**. Dibangun menggunakan **Java Spring Boot**, service ini menyediakan RESTful API untuk mengelola data peminjaman buku secara lengkap.

### Apa itu Java Spring Boot?

**Java Spring Boot** adalah framework berbasis Java yang bersifat open-source, digunakan untuk membuat aplikasi stand-alone, production-grade, dan mudah dijalankan. Spring Boot sangat populer untuk membangun arsitektur Microservices karena:

- ✅ **Auto-configuration**: Konfigurasi otomatis yang mempercepat development
- ✅ **Embedded Server**: Server tertanam seperti Tomcat, tanpa perlu instalasi eksternal
- ✅ **Production-ready**: Siap deploy dengan monitoring dan health check bawaan
- ✅ **Ekosistem Lengkap**: Integrasi mudah dengan berbagai library dan tools

Dengan Spring Boot, pengembang dapat fokus pada logika bisnis tanpa dipusingkan oleh konfigurasi infrastruktur yang rumit.

---

## 🚀 Fitur Utama

Service Peminjaman ini menyediakan RESTful API untuk mengelola data peminjaman buku dengan operasi CRUD lengkap:

| Fitur | Deskripsi |
|-------|-----------|
| **➕ Tambah Peminjaman** | Mencatat peminjaman baru (Tanggal Pinjam, Tanggal Kembali, ID Anggota, ID Buku) |
| **📖 Lihat Semua** | Mengambil daftar seluruh data peminjaman |
| **🔍 Cari per ID** | Mengambil detail data peminjaman berdasarkan ID spesifik |
| **✏️ Update Peminjaman** | Memperbarui informasi peminjaman yang sudah ada |
| **🗑️ Hapus Peminjaman** | Menghapus data peminjaman dari database |
| **🔗 Integrasi Service** | Mengambil data Buku dan Anggota dari service lain |

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
│  (Business logic & komunikasi antar     │
│   service melalui RestTemplate)         │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│          Repository Layer               │
│  (Akses database via JPA)               │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│         Model & VO Layer                │
│  (Entity, Value Objects, Response       │
│   Template untuk integrasi service)     │
└─────────────────────────────────────────┘
```

### Komponen Utama

#### 1. Model (Entity)

Berisi skema database untuk tabel Peminjaman dengan atribut:
- `id` - Primary key (auto-generated)
- `tanggalPinjam` - Tanggal peminjaman buku
- `tanggalKembali` - Tanggal pengembalian buku
- `anggotaId` - ID anggota yang meminjam (foreign key)
- `bukuId` - ID buku yang dipinjam (foreign key)

#### 2. Repository

Interface yang meng-extend `JpaRepository`, berfungsi sebagai jembatan antara aplikasi dengan database. Menyediakan method bawaan untuk operasi CRUD.

#### 3. Service

Berisi business logic aplikasi yang:
- Mengelola aliran data antara Controller dan Repository
- Melakukan komunikasi dengan service lain (Buku dan Anggota)
- Melakukan validasi dan transformasi data

#### 4. Controller

Mengatur routing endpoint API. Menerima request dari client dan mengembalikan response dalam format JSON.

#### 5. Value Object (VO)

Kelas-kelas helper untuk mengambil dan menggabungkan data dari berbagai service:
- **Buku VO**: Untuk menerima data dari Service Buku
- **Anggota VO**: Untuk menerima data dari Service Anggota
- **Response Template**: Untuk menggabungkan data Peminjaman dengan detail Buku dan Anggota

---

## 📝 Langkah-langkah Pembuatan

### 1. Inisiasi Project (Spring Initializr)

Buka [Spring Initializr](https://start.spring.io/) dan konfigurasikan:

| Parameter | Nilai | Keterangan |
|-----------|-------|------------|
| **Project** | Maven Project | Build tool yang digunakan |
| **Language** | Java | Bahasa pemrograman |
| **Spring Boot** | 3.x.x | Versi Spring Boot (pilih stable terbaru) |
| **Group** | `com.perpustakaan` | Identitas organisasi/project |
| **Artifact** | `peminjaman-service` | Nama aplikasi |
| **Package Name** | `com.perpustakaan.peminjaman` | Package struktur kode |
| **Packaging** | Jar | Format packaging aplikasi |
| **Java** | 17 atau 21 | Versi Java yang digunakan |

**Dependencies yang dipilih:**
- ✅ Spring Web
- ✅ Spring Data JPA
- ✅ H2 Database
- ✅ Lombok
- ✅ Spring Boot DevTools

Klik **Generate** untuk mengunduh project.

---

### 2. Buat Model (Entity)

Buat class `Peminjaman.java` di package `model`:

```java
package com.perpustakaan.peminjaman.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "peminjaman")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Peminjaman {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String tanggalPinjam;
    
    @Column(nullable = false)
    private String tanggalKembali;
    
    @Column(nullable = false)
    private Long anggotaId;
    
    @Column(nullable = false)
    private Long bukuId;
}
```

**Penjelasan Anotasi:**
- `@Entity`: Menandai class sebagai entity JPA
- `@Table`: Mendefinisikan nama tabel di database
- `@Data`: Lombok - generate getter, setter, toString, dll
- `@NoArgsConstructor` & `@AllArgsConstructor`: Generate constructor
- `@Id`: Menandai field sebagai primary key
- `@GeneratedValue`: Auto-increment ID
- `@Column`: Konfigurasi kolom database

---

### 3. Buat Repository

Buat interface `PeminjamanRepository.java` di package `repository`:

```java
package com.perpustakaan.peminjaman.repository;

import com.perpustakaan.peminjaman.model.Peminjaman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeminjamanRepository extends JpaRepository<Peminjaman, Long> {
    // Method CRUD sudah tersedia dari JpaRepository
    // Bisa ditambahkan custom query jika diperlukan
}
```

**Penjelasan:**
- Extends `JpaRepository<Peminjaman, Long>` menyediakan method seperti `save()`, `findAll()`, `findById()`, `delete()`
- Generic `<Peminjaman, Long>` artinya entity Peminjaman dengan ID bertipe Long

---

### 4. Buat Value Object (VO)

#### a. Buku VO

```java
package com.perpustakaan.peminjaman.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Buku {
    private Long id;
    private String judul;
    private String pengarang;
    private String penerbit;
    private Integer tahunTerbit;
}
```

#### b. Anggota VO

```java
package com.perpustakaan.peminjaman.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Anggota {
    private Long id;
    private String nama;
    private String nim;
    private String alamat;
    private String jenis_kelamin;
}
```

#### c. Response Template

```java
package com.perpustakaan.peminjaman.vo;

import com.perpustakaan.peminjaman.model.Peminjaman;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplate {
    private Peminjaman peminjaman;
    private Buku buku;
    private Anggota anggota;
}
```

---

### 5. Buat Service

Buat class `PeminjamanService.java` di package `service`:

```java
package com.perpustakaan.peminjaman.service;

import com.perpustakaan.peminjaman.model.Peminjaman;
import com.perpustakaan.peminjaman.repository.PeminjamanRepository;
import com.perpustakaan.peminjaman.vo.Anggota;
import com.perpustakaan.peminjaman.vo.Buku;
import com.perpustakaan.peminjaman.vo.ResponseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class PeminjamanService {
    
    @Autowired
    private PeminjamanRepository repository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    // Tambah peminjaman baru
    public Peminjaman savePeminjaman(Peminjaman peminjaman) {
        return repository.save(peminjaman);
    }
    
    // Ambil semua peminjaman
    public List<Peminjaman> getAllPeminjaman() {
        return repository.findAll();
    }
    
    // Ambil peminjaman berdasarkan ID
    public Optional<Peminjaman> getPeminjamanById(Long id) {
        return repository.findById(id);
    }
    
    // Ambil peminjaman dengan detail Buku dan Anggota
    public ResponseTemplate getPeminjamanWithDetails(Long id) {
        ResponseTemplate response = new ResponseTemplate();
        Peminjaman peminjaman = repository.findById(id).orElse(null);
        
        if (peminjaman != null) {
            // Ambil data Buku dari Service Buku
            Buku buku = restTemplate.getForObject(
                "http://localhost:8081/api/books/" + peminjaman.getBukuId(),
                Buku.class
            );
            
            // Ambil data Anggota dari Service Anggota
            Anggota anggota = restTemplate.getForObject(
                "http://localhost:8082/api/anggota/" + peminjaman.getAnggotaId(),
                Anggota.class
            );
            
            response.setPeminjaman(peminjaman);
            response.setBuku(buku);
            response.setAnggota(anggota);
        }
        
        return response;
    }
    
    // Update peminjaman
    public Peminjaman updatePeminjaman(Long id, Peminjaman peminjaman) {
        if (repository.existsById(id)) {
            peminjaman.setId(id);
            return repository.save(peminjaman);
        }
        return null;
    }
    
    // Hapus peminjaman
    public void deletePeminjaman(Long id) {
        repository.deleteById(id);
    }
}
```

**Penjelasan:**
- `RestTemplate`: Digunakan untuk melakukan HTTP request ke service lain
- Method `getPeminjamanWithDetails()`: Menggabungkan data dari 3 service berbeda

---

### 6. Konfigurasi RestTemplate

Buat class konfigurasi `AppConfig.java` di package `config`:

```java
package com.perpustakaan.peminjaman.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

---

### 7. Buat Controller

Buat class `PeminjamanController.java` di package `controller`:

```java
package com.perpustakaan.peminjaman.controller;

import com.perpustakaan.peminjaman.model.Peminjaman;
import com.perpustakaan.peminjaman.service.PeminjamanService;
import com.perpustakaan.peminjaman.vo.ResponseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/peminjaman")
public class PeminjamanController {
    
    @Autowired
    private PeminjamanService service;
    
    // Tambah peminjaman baru
    @PostMapping
    public ResponseEntity<Peminjaman> createPeminjaman(@RequestBody Peminjaman peminjaman) {
        Peminjaman saved = service.savePeminjaman(peminjaman);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    
    // Ambil semua peminjaman
    @GetMapping
    public ResponseEntity<List<Peminjaman>> getAllPeminjaman() {
        List<Peminjaman> list = service.getAllPeminjaman();
        return ResponseEntity.ok(list);
    }
    
    // Ambil peminjaman berdasarkan ID
    @GetMapping("/{id}")
    public ResponseEntity<Peminjaman> getPeminjamanById(@PathVariable Long id) {
        Optional<Peminjaman> peminjaman = service.getPeminjamanById(id);
        return peminjaman.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }
    
    // Ambil peminjaman dengan detail lengkap
    @GetMapping("/{id}/details")
    public ResponseEntity<ResponseTemplate> getPeminjamanWithDetails(@PathVariable Long id) {
        ResponseTemplate response = service.getPeminjamanWithDetails(id);
        if (response.getPeminjaman() != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Update peminjaman
    @PutMapping("/{id}")
    public ResponseEntity<Peminjaman> updatePeminjaman(
            @PathVariable Long id, 
            @RequestBody Peminjaman peminjaman) {
        Peminjaman updated = service.updatePeminjaman(id, peminjaman);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Hapus peminjaman
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeminjaman(@PathVariable Long id) {
        service.deletePeminjaman(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## 📝 Konfigurasi

### application.yml

Buat/edit berkas `src/main/resources/application.yml`:

```yaml
spring:
  application:
    name: peminjaman-service
  
  # Konfigurasi Database H2
  datasource:
    url: jdbc:h2:mem:peminjamandb
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
  port: 8083                # Port service peminjaman
```

**Penjelasan Konfigurasi:**

| Parameter | Nilai | Keterangan |
|-----------|-------|------------|
| `spring.application.name` | `peminjaman-service` | Nama aplikasi untuk identifikasi |
| `datasource.url` | `jdbc:h2:mem:peminjamandb` | Database H2 in-memory |
| `datasource.username` | `sa` | Username database |
| `datasource.password` | `password` | Password database |
| `jpa.hibernate.ddl-auto` | `update` | Auto-create/update schema |
| `jpa.show-sql` | `true` | Tampilkan query di console |
| `h2.console.enabled` | `true` | Aktifkan H2 web console |
| `server.port` | `8083` | Port aplikasi berjalan |

### Akses H2 Console

1. Buka browser: `http://localhost:8083/h2-console`
2. Masukkan konfigurasi:
   - **JDBC URL**: `jdbc:h2:mem:peminjamandb`
   - **Username**: `sa`
   - **Password**: `password`
3. Klik **Connect**

---

## 🔌 API Endpoints

### Base URL
```
http://localhost:8083/api/peminjaman
```

### Daftar Endpoint

| Method | Endpoint | Deskripsi | Request Body |
|--------|----------|-----------|--------------|
| `POST` | `/api/peminjaman` | Menambahkan peminjaman baru | ✅ Required |
| `GET` | `/api/peminjaman` | Mengambil semua data peminjaman | - |
| `GET` | `/api/peminjaman/{id}` | Mengambil peminjaman berdasarkan ID | - |
| `GET` | `/api/peminjaman/{id}/details` | Mengambil peminjaman dengan detail Buku & Anggota | - |
| `PUT` | `/api/peminjaman/{id}` | Mengupdate data peminjaman | ✅ Required |
| `DELETE` | `/api/peminjaman/{id}` | Menghapus data peminjaman | - |

---

### Request/Response Examples

#### 1. Tambah Peminjaman Baru

**Request:**
```http
POST /api/peminjaman
Content-Type: application/json

{
  "tanggalPinjam": "2025-01-15",
  "tanggalKembali": "2025-01-22",
  "anggotaId": 1,
  "bukuId": 1
}
```

**Response:**
```json
{
  "id": 1,
  "tanggalPinjam": "2025-01-15",
  "tanggalKembali": "2025-01-22",
  "anggotaId": 1,
  "bukuId": 1
}
```

#### 2. Lihat Semua Peminjaman

**Request:**
```http
GET /api/peminjaman
```

**Response:**
```json
[
  {
    "id": 1,
    "tanggalPinjam": "2025-01-15",
    "tanggalKembali": "2025-01-22",
    "anggotaId": 1,
    "bukuId": 1
  },
  {
    "id": 2,
    "tanggalPinjam": "2025-01-16",
    "tanggalKembali": "2025-01-23",
    "anggotaId": 2,
    "bukuId": 3
  }
]
```

#### 3. Lihat Detail Peminjaman (dengan info Buku & Anggota)

**Request:**
```http
GET /api/peminjaman/1/details
```

**Response:**
```json
{
  "peminjaman": {
    "id": 1,
    "tanggalPinjam": "2025-01-15",
    "tanggalKembali": "2025-01-22",
    "anggotaId": 1,
    "bukuId": 1
  },
  "buku": {
    "id": 1,
    "judul": "Laskar Pelangi",
    "pengarang": "Andrea Hirata",
    "penerbit": "Bentang Pustaka",
    "tahunTerbit": 2005
  },
  "anggota": {
    "id": 1,
    "nama": "Aldio Yaspindo",
    "nim": "2311081035",
    "alamat": "Padang"
    "jenis_kelamin": LK,
  }
}
```

#### 4. Update Peminjaman

**Request:**
```http
PUT /api/peminjaman/1
Content-Type: application/json

{
  "tanggalPinjam": "2025-01-15",
  "tanggalKembali": "2025-01-25",
  "anggotaId": 1,
  "bukuId": 1
}
```

**Response:**
```json
{
  "id": 1,
  "tanggalPinjam": "2025-01-15",
  "tanggalKembali": "2025-01-25",
  "anggotaId": 1,
  "bukuId": 1
}
```

#### 5. Hapus Peminjaman

**Request:**
```http
DELETE /api/peminjaman/1
```

**Response:**
```http
HTTP/1.1 204 No Content
```

---

## 🔗 Value Object (VO)

### Apa itu Value Object?

**Value Object (VO)** adalah kelas yang digunakan untuk merepresentasikan data yang diterima dari service lain atau untuk menggabungkan data dari berbagai sumber. Dalam konteks microservices, VO sangat penting untuk:

- 📦 **Menerima data dari service eksternal** (Buku, Anggota)
- 🔗 **Menggabungkan data dari berbagai service** dalam satu response
- 🎯 **Memisahkan model database** dari model transfer data

### Struktur VO dalam Project

```
vo/
├── Buku.java              ← Data dari Service Buku
├── Anggota.java           ← Data dari Service Anggota
└── ResponseTemplate.java  ← Menggabungkan semua data
```

### Cara Kerja Response Template

```
┌─────────────────┐
│   Client        │
└────────┬────────┘
         │ GET /api/peminjaman/1/details
         ▼
┌─────────────────────────────────┐
│   Peminjaman Service            │
│   (Port 8083)                   │
└────────┬──────────┬─────────────┘
         │          │
         │          └──────────────┐
         │                         │
         ▼                         ▼
┌──────────────┐          ┌──────────────┐
│ Service Buku │          │Service Anggota│
│ (Port 8081)  │          │ (Port 8082)  │
└──────────────┘          └──────────────┘
         │                         │
         └──────────┬──────────────┘
                    │
                    ▼
            Response Template
            (Gabungan Data)
```

---

## 💻 Cara Menggunakan

### Prasyarat

Pastikan service lain sudah berjalan:
- ✅ **Service Buku** di port 8081
- ✅ **Service Anggota** di port 8082

### Menggunakan cURL

```bash
# Tambah peminjaman baru
curl -X POST http://localhost:8083/api/peminjaman \
  -H "Content-Type: application/json" \
  -d '{"tanggalPinjam":"2025-01-15","tanggalKembali":"2025-01-22","anggotaId":1,"bukuId":1}'

# Lihat semua peminjaman
curl http://localhost:8083/api/peminjaman

# Lihat detail peminjaman (dengan info buku & anggota)
curl http://localhost:8083/api/peminjaman/1/details

# Update peminjaman
curl -X PUT http://localhost:8083/api/peminjaman/1 \
  -H "Content-Type: application/json" \
  -d '{"tanggalPinjam":"2025-01-15","tanggalKembali":"2025-01-25","anggotaId":1,"bukuId":1}'

# Hapus peminjaman
curl -X DELETE http://localhost:8083/api/peminjaman/1
```
---
## 📚 Referensi

- [Spring Boot Documentation](https://spring.io/projects/spring-
