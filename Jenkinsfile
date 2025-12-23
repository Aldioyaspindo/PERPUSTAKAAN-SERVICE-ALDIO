pipeline {
    agent any

    environment {
        // Nama container saat dijalankan nanti
        CONTAINER_NAME = 'peminjaman-service-prod'
        // Port di laptop (Host) yang mau dipakai
        HOST_PORT = '9003'
        // Port di dalam container (sesuai EXPOSE Dockerfile anda)
        CONTAINER_PORT = '8083'
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    echo "Membangun image untuk branch: ${env.BRANCH_NAME}"
                    // Kita tag image dengan nama branch
                    sh "docker build -t peminjaman-service:${env.BRANCH_NAME} ."
                }
            }
        }

        // --- INI TAMBAHANNYA (CD) ---
        stage('Deploy to Local Prod') {
            // Hanya deploy jika yang di-push adalah branch 'main'
            when {
                branch 'main'
            }
            steps {
                script {
                    echo "Mendeploy ke port ${HOST_PORT}..."
                    
                    // 1. Hapus container lama jika ada (biar update)
                    // "|| true" agar tidak error jika container belum ada
                    sh "docker rm -f ${CONTAINER_NAME} || true"
                    
                    // 2. Jalankan container baru
                    sh """
                        docker run -d \
                        --name ${CONTAINER_NAME} \
                        --restart unless-stopped \
                        -p ${HOST_PORT}:${CONTAINER_PORT} \
                        peminjaman-service:${env.BRANCH_NAME}
                    """
                }
            }
        }
    }
}