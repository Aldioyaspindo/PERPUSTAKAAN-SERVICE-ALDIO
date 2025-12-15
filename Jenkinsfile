pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build Docker') {
            steps {
                script {
                    // env.BRANCH_NAME akan otomatis mengambil nama branch (misal: "service-payment")
                    // env.BUILD_NUMBER untuk versi build (misal: 1, 2, 3)
                    
                    echo "Membangun image untuk service: ${env.BRANCH_NAME}"
                    
                    // Membuat nama image: nama-repo:nama-branch
                    // Contoh hasil: microservice:service-payment
                    sh "docker build -t myproject:${env.BRANCH_NAME} ."
                }
            }
        }
    }
}