pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                // Mengambil kodingan dari branch saat ini
                checkout scm
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    echo "Membangun image Spring Boot untuk branch: ${env.BRANCH_NAME}"
                    
                    // Format nama image: nama-service:nama-branch
                    // Contoh hasil: buku-service:main atau buku-service:feature-login
                    // Kita gunakan nama lowercase 'buku-service' agar standar docker
                    sh "docker build -t buku-service:${env.BRANCH_NAME} ."
                }
            }
        }

        stage('Verifikasi Image') {
            steps {
                // Mengecek apakah image berhasil dibuat
                sh "docker images | grep anggota-service"
            }
        }
    }
}