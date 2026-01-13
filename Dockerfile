# Menggunakan image Jenkins LTS resmi sebagai base
FROM jenkins/jenkins:lts
# Ganti user ke root agar bisa menginstall program
USER root
# Update paket dan install dependensi dasar
RUN apt-get update && apt-get install -y lsb-release
# Download dan install Docker CLI resmi
RUN curl -fsSLo /usr/share/keyrings/docker-archive-keyring.asc \
  https://download.docker.com/linux/debian/gpg
  
RUN echo "deb [arch=$(dpkg --print-architecture) \
  signed-by=/usr/share/keyrings/docker-archive-keyring.asc] \
  https://download.docker.com/linux/debian \
  $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list
RUN apt-get update && apt-get install -y docker-ce-cli