# 🚀 Jenkins Multibranch Pipeline - Panduan Sederhana

## 📋 Apa itu Multibranch Pipeline?

Multibranch Pipeline adalah fitur Jenkins yang **otomatis mendeteksi semua branch** di repository Git Anda dan membuat pipeline terpisah untuk setiap branch. Setiap kali Anda membuat branch baru atau push perubahan, Jenkins akan langsung mendeteksi dan menjalankan build.

### Keuntungan:
- ✅ Deteksi branch otomatis
- ✅ Build terpisah untuk setiap branch
- ✅ Cocok untuk workflow: dev → staging → production
- ✅ Tidak perlu konfigurasi manual per branch

---

## 🔧 Prasyarat

- ✅ Jenkins sudah terinstall dan berjalan
- ✅ Repository Git dengan akses (GitHub/GitLab/Bitbucket)
- ✅ Plugin Jenkins: **Pipeline**, **Git**, **GitHub** (atau sesuai platform Anda)

---

## 📝 Langkah 1: Buat Jenkinsfile

Di **root directory** setiap microservice, buat file bernama `Jenkinsfile`:

```groovy
pipeline {
    agent any
    
    environment {
        SERVICE_NAME = 'user-service'
        DOCKER_IMAGE = "${SERVICE_NAME}:${env.BRANCH_NAME}"
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo "Building branch: ${env.BRANCH_NAME}"
                checkout scm
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE} ."
                }
            }
        }
        
        stage('Deploy to Dev') {
            when {
                branch 'dev'
            }
            steps {
                script {
                    sh "docker rm -f ${SERVICE_NAME}-dev || true"
                    sh """
                        docker run -d \
                        --name ${SERVICE_NAME}-dev \
                        -p 8081:8081 \
                        ${DOCKER_IMAGE}
                    """
                }
            }
        }
        
        stage('Deploy to Staging') {
            when {
                branch 'staging'
            }
            steps {
                script {
                    sh "docker rm -f ${SERVICE_NAME}-staging || true"
                    sh """
                        docker run -d \
                        --name ${SERVICE_NAME}-staging \
                        -p 8082:8081 \
                        ${DOCKER_IMAGE}
                    """
                }
            }
        }
        
        stage('Deploy to Production') {
            when {
                branch 'main'
            }
            steps {
                input message: 'Deploy ke Production?', ok: 'Deploy!'
                script {
                    sh "docker rm -f ${SERVICE_NAME}-prod || true"
                    sh """
                        docker run -d \
                        --name ${SERVICE_NAME}-prod \
                        -p 8080:8081 \
                        ${DOCKER_IMAGE}
                    """
                }
            }
        }
    }
    
    post {
        success {
            echo '✅ Build berhasil!'
        }
        failure {
            echo '❌ Build gagal!'
        }
    }
}
```

**Push Jenkinsfile ke repository Anda!**

---

## 🔑 Langkah 2: Tambahkan Credentials di Jenkins

1. Buka Jenkins Dashboard
2. **Manage Jenkins** → **Manage Credentials**
3. Klik **(global)** → **Add Credentials**
4. Isi form:
   - **Kind**: Username with password
   - **Username**: Username GitHub/GitLab Anda
   - **Password**: Personal Access Token (buat di GitHub Settings → Developer settings → Personal access tokens)
   - **ID**: `github-credentials` (atau nama lain yang mudah diingat)
   - **Description**: GitHub Access Token
5. Klik **Create**

---

## 🎯 Langkah 3: Buat Multibranch Pipeline Job

### 3.1 Buat Job Baru

1. Dashboard Jenkins → **New Item**
2. Masukkan nama: `user-service-pipeline` (atau nama service Anda)
3. Pilih: **Multibranch Pipeline**
4. Klik **OK**

### 3.2 Konfigurasi Branch Sources

Di halaman konfigurasi:

**A. Tambahkan Source:**
- Scroll ke **Branch Sources**
- Klik **Add source** → **Git**

**B. Isi Konfigurasi Git:**
- **Project Repository**: `https://github.com/username/repository.git`
- **Credentials**: Pilih credentials yang tadi dibuat (`github-credentials`)

**C. Konfigurasi Behaviors (Opsional):**
- Klik **Add** di Behaviors
- Pilih **Filter by name (with wildcards)**
  - Include: `main dev staging feature/*`
  - Exclude: *(kosongkan)*

### 3.3 Build Configuration

- **Mode**: by Jenkinsfile
- **Script Path**: `Jenkinsfile`

### 3.4 Scan Multibranch Pipeline Triggers

- Centang: **Periodically if not otherwise run**
- Interval: **1 hour** (atau sesuai kebutuhan)

### 3.5 Orphaned Item Strategy

- **Days to keep old items**: 7
- **Max # of old items to keep**: 10

Klik **Save**!

---

## 🚀 Langkah 4: Scan Repository

Setelah save, Jenkins akan otomatis scan pertama kali.

Atau manual:
1. Klik nama job Anda
2. Klik **Scan Multibranch Pipeline Now**
3. Jenkins akan mendeteksi semua branch yang punya Jenkinsfile
4. Build otomatis dimulai! 🎉

---

## 🔔 Langkah 5: Setup Webhook (Opsional tapi Direkomendasikan)

Agar Jenkins langsung build saat ada push, setup webhook:

### Untuk GitHub:

1. Buka repository di GitHub
2. **Settings** → **Webhooks** → **Add webhook**
3. **Payload URL**: `http://your-jenkins-url:8080/github-webhook/`
4. **Content type**: `application/json`
5. **Which events**: Just the push event
6. Klik **Add webhook**

### Untuk GitLab:

1. Repository → **Settings** → **Webhooks**
2. **URL**: `http://your-jenkins-url:8080/project/user-service-pipeline`
3. **Trigger**: Push events, Merge request events
4. Klik **Add webhook**

---

## 📊 Struktur Branch yang Disarankan

```
repository/
├── main           → Production (port 8080)
├── staging        → Staging (port 8082)
├── dev            → Development (port 8081)
└── feature/login  → Testing (tidak auto-deploy)
```

---

## 💡 Cara Kerja

1. **Push ke branch `dev`** → Build otomatis → Deploy ke container dev (port 8081)
2. **Push ke branch `staging`** → Build otomatis → Deploy ke container staging (port 8082)
3. **Push ke branch `main`** → Build otomatis → **Minta konfirmasi** → Deploy ke production (port 8080)
4. **Branch lain** → Build saja, tidak deploy

---

## 🎨 Customize untuk Service Anda

Edit bagian ini di Jenkinsfile:

```groovy
environment {
    SERVICE_NAME = 'product-service'  // ← Ganti nama service
    DOCKER_IMAGE = "${SERVICE_NAME}:${env.BRANCH_NAME}"
}
```

Dan sesuaikan port di stage Deploy:

```groovy
-p 9001:8081  // ← Port host:container
```

---

## ✅ Checklist Setup

- [ ] Jenkinsfile sudah dibuat di root repository
- [ ] Credentials GitHub/GitLab sudah ditambahkan di Jenkins
- [ ] Multibranch Pipeline job sudah dibuat
- [ ] Repository berhasil di-scan
- [ ] Build pertama berhasil
- [ ] Webhook sudah dikonfigurasi (opsional)

---

## 🛠️ Troubleshooting Cepat

| Masalah | Solusi |
|---------|--------|
| Branch tidak terdeteksi | Pastikan Jenkinsfile ada di root branch tersebut |
| Build gagal "docker not found" | Pastikan Docker CLI terinstall di Jenkins |
| Webhook tidak jalan | Cek firewall, pastikan Jenkins bisa diakses dari internet |
| Port sudah digunakan | Hentikan container lama: `docker rm -f nama-container` |

---

## 📚 Referensi

- [Jenkins Multibranch Pipeline Docs](https://www.jenkins.io/doc/book/pipeline/multibranch/)
- [Jenkinsfile Syntax](https://www.jenkins.io/doc/book/pipeline/syntax/)

---

<div align="center">

**Selamat! Pipeline Multibranch Anda sudah siap! 🎉**

*Push kode ke branch mana saja dan lihat magic-nya terjadi!*

</div>
