# Finaro - Payment Gateway

Take home assignment for a Java/Spring developer.

The detailed requirements are found in [ASSIGNMENT.md](ASSIGNMENT.md).

type of application: REST API

description: microservice with REST API gateway that accepts credit card payments for processing and maintains a
transaction history.

### External dependency

| name          | version   | description                  |
|---------------|-----------|------------------------------|
|postgresql     | \>= 13    | for storing payment info     |

### ENV VARIABLES MUST BE DECLARED:

| VARIABLES                 | DESCRIPTION              | FORMAT             | REPRESENTS               | DEFAULT VALUE              | EXAMPLE                    | AVAILABLE VALUES |
|---------------------------|--------------------------|--------------------|--------------------------|----------------------------|----------------------------|------------------|
|POSTGRES_URL               |                          |jdbc:postgresql://localhost:5432/paymentdb?currentSchema=public|jdbcUrl for connecting to DB|
|POSTGRES_USERNAME          |                          |admin               |DB user                   |
|POSTGRES_PASSWORD          |                          |passw0rd            |DB password               |

### ENV VARIABLES CAN BE OVERRIDDEN:

| VARIABLES                                   | DESCRIPTION                     | FORMAT  | REPRESENTS                      | DEFAULT VALUE  | EXAMPLE           | AVAILABLE VALUES                                                                                           |
|---------------------------------------------|---------------------------------|---------|---------------------------------|----------------|-------------------|------------------------------------------------------------------------------------------------------------|
| SPRING_APPLICATION_NAME                     | Application name                | String  | app name                        | java-paymentgateway| java-paymentgateway             | java-paymentgateway                                                                      |
| SPRING_APPLICATION_PORT                     | Application port                | Integer | app port                        | 8080           | 8080              | any Integer                                                                                                |
| LOGGING_LEVEL_ROOT | INFO | Logging level of application. Can be { ERROR, WARN, DEBUG, TRACE, INFO} |

## ENV VARIABLES TO CONFIGURE PROMETHEUS METRICS

| VARIABLES                                       | DEFAULT VALUE                                                   | DESCRIPTION                                        |
|-------------------------------------------------|-----------------------------------------------------------------|----------------------------------------------------|
| MANAGEMENT_SERVER_PORT                          | 8080                                                            | port for metrics                                   |
| MANAGEMENT_ENDPOINTS_WEB_BASEPATH               | /                                                               | base path for metrics                              |
| MANAGEMENT_ENDPOINTS_WEB_PATHMAPPING_METRICS    | originmetrics                                                   | original metric path                               |
| MANAGEMENT_ENDPOINTS_WEB_PATHMAPPING_PROMETHEUS | metrics                                                         | Prometheus metric path                             |
| MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE       | health, info, loggers, metrics, prometheus, threaddump, caches  | Exported Actuator's modules (includes Prometheus)  |

### Additional info

Liveness and Readiness application's health checks are available with paths: `/livenessState` and `/readinessState`

API into are described in [swagger.yml](http://localhost:8080/swagger-ui/index.html)

### Build application

`mvn clean package`

In target library there will generated `java-paymentgateway.jar`

### Build application

`mvn clean test`

### Run application locally

Run docker image `./docker/docker-compose.yml` with PostgreDB by command:

`docker-compose up`

Run application by command:

`mvn spring-boot:run -Dspring-boot.run.profiles=local`



