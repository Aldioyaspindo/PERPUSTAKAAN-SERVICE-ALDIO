# ELK Stack Monitoring untuk Spring Boot Microservices (Lightweight Setup)

Repositori ini berisi konfigurasi untuk memonitoring aplikasi Spring Boot menggunakan **ELK Stack (Elasticsearch, Kibana)** dengan pendekatan yang ringan menggunakan **Filebeat**.

## 🚀 Arsitektur

Dalam konfigurasi ini, kita **tidak menggunakan Logstash** untuk menghemat sumber daya (memori & CPU). Alur log adalah sebagai berikut:

1. **Spring Boot App**: Menulis log ke `Console` (Stdout) dalam format JSON menggunakan `logstash-logback-encoder`.
2. **Docker Daemon**: Menyimpan keluaran console tersebut ke dalam berkas log container.
3. **Filebeat**: Membaca berkas log container secara langsung, menambahkan metadata Docker, dan mengirimkannya ke Elasticsearch.
4. **Elasticsearch**: Menyimpan dan mengindeks data log.
5. **Kibana**: Visualisasi log.

```
Spring Boot (JSON) → Docker Stdout → Filebeat → Elasticsearch → Kibana
```

---

## 🛠️ Prasyarat

* Docker & Docker Compose terpasang.
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
```

### 2. Konfigurasi Logback

Buat berkas `src/main/resources/logback-spring.xml`. Konfigurasi ini akan mengubah keluaran console menjadi format JSON yang terstruktur.

```xml
<configuration>
    <appender name="JSON" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="net.logstash.logback.encoder.LogstashEncoder" />
    </appender>

    <root level="INFO">
        <appender-ref ref="JSON"/>
    </root>
</configuration>
```

---

## 🐳 Konfigurasi Infrastruktur (ELK + Filebeat)

### Struktur Folder

Pastikan struktur folder untuk konfigurasi ELK seperti berikut:

```
/project-root
├── docker-compose.yml
└── filebeat
    └── filebeat.yml
```

### 1. Filebeat Configuration (filebeat/filebeat.yml)

Berkas ini bertugas mengambil log dari container Docker.

```yaml
filebeat.inputs:
- type: container
  paths:
    - '/var/lib/docker/containers/*/*.log'
  processors:
    - add_docker_metadata:
        host: "unix:///var/run/docker.sock"

output.elasticsearch:
  hosts: ["elasticsearch:9200"]
```

### 2. Docker Compose (docker-compose.yml)

Jalankan stack ini untuk memulai Elasticsearch, Kibana, dan Filebeat.

> ⚠️ **PENTING UNTUK PENGGUNA WINDOWS**: Perhatikan bagian `volumes` pada service `filebeat`. Pastikan path lokal mengarah ke lokasi berkas `filebeat.yml` yang benar di komputer Anda.

```yaml
version: '3.8'

services:
  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:8.10.4
    container_name: elasticsearch
    environment:
      - discovery.type=single-node
      - xpack.security.enabled=false
      - xpack.security.http.ssl.enabled=false
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
      - bootstrap.memory_lock=true
    ulimits:
      memlock:
        soft: -1
        hard: -1
    deploy:
      resources:
        limits:
          memory: 1g
    ports:
      - "9200:9200"
    networks:
      - elk

  kibana:
    image: docker.elastic.co/kibana/kibana:8.10.4
    container_name: kibana
    environment:
      ELASTICSEARCH_HOSTS: "http://elasticsearch:9200"
      XPACK_SECURITY_ENABLED: "false"
    deploy:
      resources:
        limits:
          memory: 512m
    ports:
      - "5601:5601"
    depends_on:
      - elasticsearch
    networks:
      - elk

  filebeat:
    image: docker.elastic.co/beats/filebeat:8.10.4
    container_name: filebeat
    user: root
    # Perintah ini mematikan pengecekan permission yang ketat agar dapat berjalan di Windows/WSL
    command: filebeat -e --strict.perms=false
    deploy:
      resources:
        limits:
          memory: 200m
    volumes:
      # GANTI PATH INI SESUAI LOKASI PROYEK ANDA
      # Contoh untuk Windows: D:/path/ke/proyek/filebeat/filebeat.yml
      # Contoh untuk Linux/Mac: ./filebeat/filebeat.yml
      - ./filebeat/filebeat.yml:/usr/share/filebeat/filebeat.yml:ro
      
      # Mounting socket dan container logs
      - /var/lib/docker/containers:/var/lib/docker/containers:ro
      - /var/run/docker.sock:/var/run/docker.sock:ro
    depends_on:
      - elasticsearch
    networks:
      - elk

networks:
  elk:
    driver: bridge
```

---

## ▶️ Cara Menjalankan

### 1. Jalankan ELK Stack

Buka terminal di folder root proyek dan jalankan:

```bash
docker-compose up -d
```

### 2. Jalankan Aplikasi Spring Boot

Jalankan aplikasi microservice Anda (bisa dijalankan melalui IDE atau Docker). Pastikan aplikasi terhubung ke jaringan yang sama atau log-nya dapat ditangkap oleh Docker daemon.

### 3. Akses Kibana

Buka browser dan akses: `http://localhost:5601`

### 4. Setup Data View

1. Pergi ke menu **Stack Management** → **Data Views**.
2. Klik **Create data view**.
3. Pada kolom **Name**, ketik: `filebeat-*`.
4. **Timestamp field**: pilih `@timestamp`.
5. Klik **Create data view**.

### 5. Lihat Log

Pergi ke menu **Analytics** → **Discover**. Anda akan melihat log aplikasi Spring Boot Anda yang sudah terformat JSON.

---

## 💡 Troubleshooting

### Filebeat Keluar dengan Kode 1 (Permissions)

Jika terjadi kesalahan permission pada `filebeat.yml`, pastikan perintah `--strict.perms=false` ada pada docker-compose. Masalah ini sering terjadi jika memasang berkas dari sistem berkas Windows ke container Linux.

**Solusi**:
- Pastikan baris `command: filebeat -e --strict.perms=false` ada di konfigurasi service filebeat.
- Periksa kembali path volume yang dipasang sudah benar.

### Log Tidak Muncul di Kibana

Pastikan aplikasi Spring Boot Anda benar-benar menghasilkan log ke console (stdout). Filebeat dikonfigurasi untuk membaca `/var/lib/docker/containers`, jadi berkas log lokal (seperti `spring.log`) tidak akan terbaca kecuali dipasang secara spesifik.

**Checklist**:
- Aplikasi Spring Boot berjalan dan menghasilkan log.
- Berkas `logback-spring.xml` sudah dikonfigurasi dengan benar.
- Container Filebeat berjalan tanpa error: `docker logs filebeat`.
- Elasticsearch dapat diakses: `curl http://localhost:9200`.

### Elasticsearch Tidak Dapat Diakses

Jika Elasticsearch gagal diakses atau container terus restart:

**Solusi**:
- Periksa apakah port 9200 sudah digunakan oleh aplikasi lain.
- Untuk pengguna WSL, tingkatkan `vm.max_map_count`:
  ```bash
  wsl -d docker-desktop
  sysctl -w vm.max_map_count=262144
  ```
- Periksa log container: `docker logs elasticsearch`.

---

## 📊 Keunggulan Konfigurasi Ini

1. **Command `--strict.perms=false`**: Sangat membantu untuk lingkungan pengembangan di Windows/WSL, karena Filebeat biasanya sangat ketat dalam memeriksa permission berkas konfigurasi.

2. **LogstashEncoder**: Mengubah log menjadi JSON di level aplikasi (Java) jauh lebih efisien daripada melakukan parsing teks biasa menggunakan Regex/Grok di Logstash/Filebeat. Ini mengurangi beban CPU secara signifikan.

3. **Tanpa Logstash**: Menghemat sekitar 500MB-1GB RAM dengan mengirim log langsung dari Filebeat ke Elasticsearch.

4. **Resource Limits**: Setiap service dibatasi penggunaan memorinya untuk mencegah laptop hang saat development.

---

## 📚 Referensi

- [Elasticsearch Documentation](https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html)
- [Kibana Documentation](https://www.elastic.co/guide/en/kibana/current/index.html)
- [Filebeat Documentation](https://www.elastic.co/guide/en/beats/filebeat/current/index.html)
- [Logstash Logback Encoder](https://github.com/logfellow/logstash-logback-encoder)

---

**Selamat! Setup monitoring Anda sudah siap digunakan! 🎉**
