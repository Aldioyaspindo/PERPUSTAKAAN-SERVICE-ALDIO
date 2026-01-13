# ELK Stack Monitoring untuk Spring Boot Microservices (Lightweight Setup)

Repositori ini berisi konfigurasi untuk memonitoring aplikasi Spring Boot menggunakan **ELK Stack (Elasticsearch, Kibana)** dengan pendekatan yang ringan menggunakan **Filebeat**.

## 🚀 Arsitektur

Dalam konfigurasi ini, kita **tidak menggunakan Logstash** untuk menghemat resource (memori & CPU). Alur log adalah sebagai berikut:

1.  **Spring Boot App**: Menulis log ke `Console` (Stdout) dalam format JSON menggunakan `logstash-logback-encoder`.
2.  **Docker Daemon**: Menyimpan output console tersebut ke dalam log file container.
3.  **Filebeat**: Membaca log file container secara langsung, menambahkan metadata Docker, dan mengirimkannya ke Elasticsearch.
4.  **Elasticsearch**: Menyimpan dan mengindeks data log.
5.  **Kibana**: Visualisasi log.

`Spring Boot (JSON)` -> `Docker Stdout` -> `Filebeat` -> `Elasticsearch` -> `Kibana`

---

## 🛠️ Prasyarat

* Docker & Docker Compose terinstall.
* Java JDK (untuk menjalankan service Spring Boot).
* Maven.

---

## 📝 Konfigurasi Spring Boot Service

Agar Filebeat dapat memproses log dengan mudah, setiap Microservice harus mengeluarkan log dalam format JSON.

### 1. Tambahkan Dependency
Tambahkan dependency berikut pada `pom.xml` di setiap service:

```xml
<dependency>
    <groupId>net.logstash.logback</groupId>
    <artifactId>logstash-logback-encoder</artifactId>
    <version>7.4</version>
</dependency>
