FROM eclipse-temurin:17.0.12_7-jre

WORKDIR /root

# Copia el JAR ya construido desde tu máquina local
COPY ./build/libs/traceability-0.0.1-SNAPSHOT.jar ./traceability-0.0.1-SNAPSHOT.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "/root/traceability-0.0.1-SNAPSHOT.jar"]
