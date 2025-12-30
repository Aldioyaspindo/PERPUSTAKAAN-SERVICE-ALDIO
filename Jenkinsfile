pipeline {
    agent any

    environment {
        CONTAINER_NAME = 'buku-service-deploy'
        HOST_PORT = '9001'
        CONTAINER_PORT = '8081'
    }

    stages {
        stage('Checkout Code') {
            steps { checkout scm }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    echo "Membangun image untuk branch: ${env.BRANCH_NAME}"
                    sh "docker build -t buku-service:${env.BRANCH_NAME} ."
                }
            }
        }

        // --- INI YANG BENAR (Tanpa 'when') ---
        stage('Deploy to Local Prod') {
            // Bagian 'when' sudah dihapus agar branch APAPUN bisa di-deploy
            steps {
                script {
                    echo "Mendeploy ke port ${HOST_PORT}..."
                    
                    // 1. Hapus container lama
                    sh "docker rm -f ${CONTAINER_NAME} || true"
                    
                    // 2. Jalankan container baru
                    sh """
                        docker run -d \
                        --name ${CONTAINER_NAME} \
                        --restart unless-stopped \
                        -p ${HOST_PORT}:${CONTAINER_PORT} \
                        buku-service:${env.BRANCH_NAME}
                    """
                }
            }
        }
    }
}