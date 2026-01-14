# 📚 Pengembalian Service (Return Service)

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![H2](https://img.shields.io/badge/H2-0000BB?style=for-the-badge&logo=database&logoColor=white)

**Microservice untuk manajemen pengembalian buku dengan sistem denda otomatis**

[Fitur](#-fitur-utama) • [Teknologi](#️-teknologi--dependensi) • [Instalasi](#-langkah-langkah-pembuatan) • [API Endpoints](#-api-endpoints)

</div>

---

## 📖 Tentang Proyek

Proyek ini adalah implementasi **Service Pengembalian**, bagian dari sistem Perpustakaan berbasis **Microservices**. Dibangun menggunakan **Java Spring Boot**, service ini menyediakan RESTful API untuk mengelola data pengembalian buku dengan **perhitungan denda otomatis** berdasarkan keterlambatan.

### Apa itu Java Spring Boot?

**Java Spring Boot** adalah framework berbasis Java yang bersifat open-source, digunakan untuk membuat aplikasi stand-alone, production-grade, dan mudah dijalankan. Spring Boot sangat populer untuk membangun arsitektur Microservices karena:

- ✅ **Auto-configuration**: Konfigurasi otomatis yang mempercepat development
- ✅ **Embedded Server**: Server tertanam seperti Tomcat
- ✅ **Production-ready**: Siap deploy dengan monitoring bawaan
- ✅ **Ekosistem Lengkap**: Integrasi mudah dengan berbagai library

---

## 🚀 Fitur Utama

Service Pengembalian ini menyediakan RESTful API untuk mengelola data pengembalian buku:

| Fitur | Deskripsi |
|-------|-----------|
| **➕ Tambah Pengembalian** | Mencatat pengembalian buku dengan perhitungan denda otomatis |
| **📊 Perhitungan Denda** | Menghitung denda berdasarkan keterlambatan (Rp 1.000/hari) |
| **📖 Lihat Semua** | Mengambil daftar seluruh data pengembalian |
| **🔍 Cari per ID** | Mengambil detail pengembalian berdasarkan ID |
| **✏️ Update Data** | Memperbarui informasi pengembalian |
| **🗑️ Hapus Data** | Menghapus data pengembalian dari database |
| **🔗 Integrasi Service** | Mengambil data Peminjaman untuk validasi |

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

- ✅ **Spring Web**: Untuk membangun RESTful API
- ✅ **Spring Data JPA**: Untuk abstraksi database
- ✅ **Lombok**: Mengurangi boilerplate code
- ✅ **H2 Database**: Database ringan untuk development
- ✅ **Spring Boot DevTools**: Fitur live reload

---

## 🏗️ Arsitektur Aplikasi

```
┌─────────────────────────────────────────┐
│           Controller Layer              │
│  (Routing & HTTP Request)               │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│            Service Layer                │
│  (Business Logic & Perhitungan Denda)   │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│          Repository Layer               │
│  (Database Access)                      │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│         Model & DTO Layer               │
│  (Entity, VO, DTO)                      │
└─────────────────────────────────────────┘
```

### Komponen Utama

#### 1. Model (Entity)
Skema database dengan atribut:
- `id` - Primary key
- `peminjamanId` - ID peminjaman yang dikembalikan
- `tanggalDikembalikan` - Tanggal pengembalian aktual
- `terlambat` - Jumlah hari keterlambatan
- `denda` - Total denda yang harus dibayar

#### 2. DTO (Data Transfer Object)
Untuk perhitungan denda:
- `peminjamanId` - ID peminjaman
- `tanggalDikembalikan` - Tanggal dikembalikan
- `lamaPinjam` - Total hari peminjaman
- `terlambat` - Hari keterlambatan
- `denda` - Nominal denda

#### 3. VO (Value Object)
Untuk integrasi dengan service lain:
- **Peminjaman VO**: Data dari Service Peminjaman
- **Buku VO**: Data dari Service Buku
- **Anggota VO**: Data dari Service Anggota
- **Response Template**: Menggabungkan semua data

---

## 📝 Langkah-langkah Pembuatan

### 1. Inisiasi Project

Buka [Spring Initializr](https://start.spring.io/) dan konfigurasikan:

| Parameter | Nilai |
|-----------|-------|
| **Project** | Maven Project |
| **Language** | Java |
| **Spring Boot** | 3.x.x (terbaru) |
| **Group** | `com.perpustakaan` |
| **Artifact** | `pengembalian-service` |
| **Package Name** | `com.perpustakaan.pengembalian` |
| **Packaging** | Jar |
| **Java** | 17 atau 21 |

**Dependencies**: Spring Web, Spring Data JPA, H2 Database, Lombok, DevTools

---

### 2. Buat Model (Entity)

```java
package com.perpustakaan.pengembalian.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pengembalian")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pengembalian {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long peminjamanId;
    
    @Column(nullable = false)
    private String tanggalDikembalikan;
    
    @Column(nullable = false)
    private String terlambat;
    
    @Column(nullable = false)
    private String denda;
}
```

---

### 3. Buat DTO (Data Transfer Object)

```java
package com.perpustakaan.pengembalian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PengembalianDTO {
    private Long peminjamanId;
    private String tanggalDikembalikan;
    private long lamaPinjam;
    private long terlambat;
    private int denda;
}
```

---

### 4. Buat Value Object (VO)

#### Peminjaman VO
```java
package com.perpustakaan.pengembalian.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Peminjaman {
    private Long id;
    private String tanggalPinjam;
    private String tanggalKembali;
    private Long anggotaId;
    private Long bukuId;
}
```

#### Buku VO
```java
package com.perpustakaan.pengembalian.vo;

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

#### Anggota VO
```java
package com.perpustakaan.pengembalian.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Anggota {
    private Long id;
    private String nama;
    private String email;
    private String telepon;
    private String alamat;
}
```

#### Response Template
```java
package com.perpustakaan.pengembalian.vo;

import com.perpustakaan.pengembalian.model.Pengembalian;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplate {
    private Pengembalian pengembalian;
    private Peminjaman peminjaman;
    private Buku buku;
    private Anggota anggota;
}
```

---

### 5. Buat Repository

```java
package com.perpustakaan.pengembalian.repository;

import com.perpustakaan.pengembalian.model.Pengembalian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PengembalianRepository extends JpaRepository<Pengembalian, Long> {
}
```

---

### 6. Buat Service dengan Logika Perhitungan Denda

```java
package com.perpustakaan.pengembalian.service;

import com.perpustakaan.pengembalian.dto.PengembalianDTO;
import com.perpustakaan.pengembalian.model.Pengembalian;
import com.perpustakaan.pengembalian.repository.PengembalianRepository;
import com.perpustakaan.pengembalian.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class PengembalianService {
    
    @Autowired
    private PengembalianRepository repository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    private static final int DENDA_PER_HARI = 1000; // Rp 1.000 per hari
    
    // Tambah pengembalian dengan perhitungan denda
    public Pengembalian savePengembalian(PengembalianDTO dto) {
        // Ambil data peminjaman
        Peminjaman peminjaman = restTemplate.getForObject(
            "http://localhost:8083/api/peminjaman/" + dto.getPeminjamanId(),
            Peminjaman.class
        );
        
        if (peminjaman == null) {
            throw new RuntimeException("Data peminjaman tidak ditemukan");
        }
        
        // Hitung keterlambatan
        LocalDate tanggalKembali = LocalDate.parse(peminjaman.getTanggalKembali());
        LocalDate tanggalDikembalikan = LocalDate.parse(dto.getTanggalDikembalikan());
        
        long hariTerlambat = ChronoUnit.DAYS.between(tanggalKembali, tanggalDikembalikan);
        
        // Jika tidak terlambat, set 0
        if (hariTerlambat < 0) {
            hariTerlambat = 0;
        }
        
        // Hitung denda
        int totalDenda = (int) (hariTerlambat * DENDA_PER_HARI);
        
        // Simpan ke database
        Pengembalian pengembalian = new Pengembalian();
        pengembalian.setPeminjamanId(dto.getPeminjamanId());
        pengembalian.setTanggalDikembalikan(dto.getTanggalDikembalikan());
        pengembalian.setTerlambat(String.valueOf(hariTerlambat));
        pengembalian.setDenda(String.valueOf(totalDenda));
        
        return repository.save(pengembalian);
    }
    
    // Ambil semua pengembalian
    public List<Pengembalian> getAllPengembalian() {
        return repository.findAll();
    }
    
    // Ambil pengembalian berdasarkan ID
    public Optional<Pengembalian> getPengembalianById(Long id) {
        return repository.findById(id);
    }
    
    // Ambil pengembalian dengan detail lengkap
    public ResponseTemplate getPengembalianWithDetails(Long id) {
        ResponseTemplate response = new ResponseTemplate();
        Pengembalian pengembalian = repository.findById(id).orElse(null);
        
        if (pengembalian != null) {
            // Ambil data peminjaman
            Peminjaman peminjaman = restTemplate.getForObject(
                "http://localhost:8083/api/peminjaman/" + pengembalian.getPeminjamanId(),
                Peminjaman.class
            );
            
            if (peminjaman != null) {
                // Ambil data buku
                Buku buku = restTemplate.getForObject(
                    "http://localhost:8081/api/books/" + peminjaman.getBukuId(),
                    Buku.class
                );
                
                // Ambil data anggota
                Anggota anggota = restTemplate.getForObject(
                    "http://localhost:8082/api/anggota/" + peminjaman.getAnggotaId(),
                    Anggota.class
                );
                
                response.setPengembalian(pengembalian);
                response.setPeminjaman(peminjaman);
                response.setBuku(buku);
                response.setAnggota(anggota);
            }
        }
        
        return response;
    }
    
    // Update pengembalian
    public Pengembalian updatePengembalian(Long id, Pengembalian pengembalian) {
        if (repository.existsById(id)) {
            pengembalian.setId(id);
            return repository.save(pengembalian);
        }
        return null;
    }
    
    // Hapus pengembalian
    public void deletePengembalian(Long id) {
        repository.deleteById(id);
    }
}
```

---

### 7. Konfigurasi RestTemplate

```java
package com.perpustakaan.pengembalian.config;

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

### 8. Buat Controller

```java
package com.perpustakaan.pengembalian.controller;

import com.perpustakaan.pengembalian.dto.PengembalianDTO;
import com.perpustakaan.pengembalian.model.Pengembalian;
import com.perpustakaan.pengembalian.service.PengembalianService;
import com.perpustakaan.pengembalian.vo.ResponseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pengembalian")
public class PengembalianController {
    
    @Autowired
    private PengembalianService service;
    
    // Tambah pengembalian baru
    @PostMapping
    public ResponseEntity<Pengembalian> createPengembalian(@RequestBody PengembalianDTO dto) {
        Pengembalian saved = service.savePengembalian(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    
    // Ambil semua pengembalian
    @GetMapping
    public ResponseEntity<List<Pengembalian>> getAllPengembalian() {
        List<Pengembalian> list = service.getAllPengembalian();
        return ResponseEntity.ok(list);
    }
    
    // Ambil pengembalian berdasarkan ID
    @GetMapping("/{id}")
    public ResponseEntity<Pengembalian> getPengembalianById(@PathVariable Long id) {
        Optional<Pengembalian> pengembalian = service.getPengembalianById(id);
        return pengembalian.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }
    
    // Ambil pengembalian dengan detail lengkap
    @GetMapping("/{id}/details")
    public ResponseEntity<ResponseTemplate> getPengembalianWithDetails(@PathVariable Long id) {
        ResponseTemplate response = service.getPengembalianWithDetails(id);
        if (response.getPengembalian() != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Update pengembalian
    @PutMapping("/{id}")
    public ResponseEntity<Pengembalian> updatePengembalian(
            @PathVariable Long id, 
            @RequestBody Pengembalian pengembalian) {
        Pengembalian updated = service.updatePengembalian(id, pengembalian);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Hapus pengembalian
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePengembalian(@PathVariable Long id) {
        service.deletePengembalian(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## 📝 Konfigurasi

### application.yml

```yaml
spring:
  application:
    name: pengembalian-service
  
  # Konfigurasi Database H2
  datasource:
    url: jdbc:h2:mem:pengembaliandb
    driverClassName: org.h2.Driver
    username: sa
    password: password
  
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: update
    show-sql: true

  # H2 Console
  h2:
    console:
      enabled: true
      path: /h2-console

server:
  port: 8084  # Port service pengembalian
```

---

## 🔌 API Endpoints

### Base URL
```
http://localhost:8084/api/pengembalian
```

### Daftar Endpoint

| Method | Endpoint | Deskripsi |
|--------|----------|-----------|
| `POST` | `/api/pengembalian` | Tambah pengembalian dengan perhitungan denda otomatis |
| `GET` | `/api/pengembalian` | Ambil semua data pengembalian |
| `GET` | `/api/pengembalian/{id}` | Ambil pengembalian berdasarkan ID |
| `GET` | `/api/pengembalian/{id}/details` | Ambil pengembalian dengan detail lengkap |
| `PUT` | `/api/pengembalian/{id}` | Update data pengembalian |
| `DELETE` | `/api/pengembalian/{id}` | Hapus data pengembalian |

---

## 💡 Contoh Penggunaan

### 1. Tambah Pengembalian

**Request:**
```http
POST /api/pengembalian
Content-Type: application/json

{
  "peminjamanId": 1,
  "tanggalDikembalikan": "2025-01-25"
}
```

**Response:**
```json
{
  "id": 1,
  "peminjamanId": 1,
  "tanggalDikembalikan": "2025-01-25",
  "terlambat": "3",
  "denda": "3000"
}
```

### 2. Lihat Detail Lengkap

**Request:**
```http
GET /api/pengembalian/1/details
```

**Response:**
```json
{
  "pengembalian": {
    "id": 1,
    "peminjamanId": 1,
    "tanggalDikembalikan": "2025-01-25",
    "terlambat": "3",
    "denda": "3000"
  },
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
    "nama": "Budi Santoso",
    "email": "budi@email.com",
    "telepon": "08123456789",
    "alamat": "Jakarta"
  }
}
```
