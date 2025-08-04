1. Clonar el repositorio

git clone https://github.com/cabreu1981/candidates-management-service
cd tu-repo

Asegúrate de tener instalado Docker y Docker Compose en tu sistema.


2. Ejecutar el proyecto
Construye e inicia todos los contenedores necesarios con el siguiente comando:

docker-compose up --build

Esto levantará los siguientes servicios:

📦 Backend Java Spring Boot

🐘 Base de datos MySQL

🐳 Kafka + Zookeeper

📊 Kafdrop (visualización de eventos Kafka)

📈 Actuator para monitoreo y métricas (Prometheus ready)

3. Acceder a las herramientas

| Recurso               | URL                                                                                        | Descripción                                             |
| --------------------- | ------------------------------------------------------------------------------------------ | ------------------------------------------------------- |
| 🔧 Swagger API Docs   | [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) | Documentación interactiva de la API                     |
| 🧠 Kafdrop            | [http://localhost:9000/](http://localhost:9000/)                                           | Interfaz web para visualizar topics y mensajes en Kafka |
| 📊 Prometheus Metrics | [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus)     | Endpoint de métricas compatibles con Prometheus         |


✅ Requisitos
Java 17+
Maven 3.8+
Docker y Docker Compose


-------------------------------------------------------------------------

📚 Descripción del Proyecto y Stack Tecnológico

🎯 Objetivo del Proyecto


Este microservicio forma parte de un sistema de gestión de candidatos en proceso de selección y contratación. Su objetivo es proporcionar una API robusta, segura y escalable que permita:

Registrar nuevos candidatos (clientes).

Consultar estadísticas como edad promedio y desviación estándar.

Listar todos los candidatos junto con una estimación derivada (como la fecha estimada de fallecimiento).

Emitir eventos cuando ocurren acciones clave como registro o login.

🏗️ Arquitectura Utilizada

Se implementó arquitectura hexagonal (ports & adapters) para mantener el código desacoplado, testeable y fácilmente mantenible. Esta estructura favorece:
Separación clara entre lógica de negocio y detalles técnicos (como persistencia o transporte).

Alta flexibilidad para agregar nuevos canales de entrada (por ejemplo, REST, eventos Kafka) o de salida (base de datos, mensajería).

| Tecnología                    | Rol dentro del proyecto                                      | Motivo de elección                                                                                         |
| ----------------------------- | ------------------------------------------------------------ | ---------------------------------------------------------------------------------------------------------- |
| **Java 17 + Spring Boot 3.2** | Desarrollo del backend y APIs REST                           | Framework maduro, potente, con excelente soporte de comunidad y fácil integración con el ecosistema Spring |
| **MySQL 8**                   | Base de datos relacional                                     | Amplio soporte, alto rendimiento para transacciones, compatibilidad con Flyway                             |
| **Flyway**                    | Migración y versionamiento de la base de datos               | Permite mantener el esquema controlado y reproducible                                                      |
| **Kafka + Zookeeper**         | Comunicación asincrónica entre microservicios                | Ideal para eventos como registros o notificaciones de login                                                |
| **Kafdrop**                   | UI web para explorar topics y mensajes en Kafka              | Útil en entornos de desarrollo y debugging                                                                 |
| **Prometheus (Actuator)**     | Exposición de métricas para monitoreo                        | Facilita la integración con sistemas de observabilidad                                                     |
| **Swagger / OpenAPI**         | Documentación de los endpoints REST                          | Mejora la colaboración y testing automático de APIs                                                        |
| **Docker & Docker Compose**   | Orquestación de contenedores (backend, base de datos, kafka) | Facilita el despliegue local, testing e integración continua                                               |



⚙️ Diseño Preparado para Producción
El proyecto está pensado para ser desplegado en entornos cloud (como AWS, Azure o GCP), con separación de responsabilidades, tolerancia a fallos y soporte para monitoreo.
