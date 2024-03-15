FROM openjdk:11-jre-slim

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR da sua aplicação para o contêiner
COPY target/FiapHotel.jar /app/FiapHotel.jar

# Comando para executar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "FiapHotel.jar"]
