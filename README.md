# QUARKUS

Existen varias dificultades que nos podemos encontrar al usar Java en un entorno cloud, y como Quarkus nos ayuda a conseguir superar estas dificultades.

Java
•	Carga dinámica de clases
•	Reflexión

Quarkus
•	Compila en código nativo
•	Agile Time Compilation

Generación de instancia masiva, aun siendo con lazy loading, los tiempos de respuesta van siendo peores. Esto empeora cuando las aplicaciones corren en contenedores. Podría llegar un punto en el que la aplicación Java podría sobrepasar los límites configurados en la creación del contendor, Java 11 mejora algo esto pero no llega a solventarlo.
Kubernetes pararía el contenedor los destruye por este motivo.

# PROYECTO EJEMPLO CON ARQUETIPO MAVEN Y NATIVE COMPILATION

Creación del proyecto
```
mvn io.quarkus:quarkus-maven-plugin:0.18.0:create -DprojectGroupId=org.acme -DprojectArtifactId=getting-started -DclassName="org.acme.quickstart.GreetingResource" -Dpath="/hello"
```
Compilo en modo dev (en la propia carpeta generada ya queda el binario de maven-plugin)
```
./mvnw clean compile quarkus:dev
```

Empaquetado del proyecto en modo nativo
```
./mvnw package -Pnative -Dnative-image.docker-build=true -DskipTests
```

Construcción de la imagen docker
```
docker build -f  src\main\docker\Dockerfile.native -t quarkus/getting-started .
```

Ejecución en docker de la aplicación en el contenedor, se observa que en solo 5 minisegundo se arranca
```
docker run -i --rm -p 8080:8080 quarkus/getting-started
2020-04-24 04:04:36,772 INFO  [io.quarkus] (main) Quarkus 0.18.0 started in 0.005s. Listening on: http://0.0.0.0:8080
```

Probar que está funcionando
```
curl localhost:8080/hello
```

# SUSTITUCION DE PROPIEDADES
En la compilación se pueden sustituir propiedades p.e.
```
./mvnw compile quarkus:dev -Dgreeting.message=Aloha
```
Tambien en la ejecución como variables de entorno
```
export GREETING_MESSAGE="Aloha Beach"
./mvnw compile quarkus:dev
```

## PERFILES DE CONFIGURACION SEGÚN EL DESTINO PRODUCCION, DESARROLLO Y TEST 
Utilizando un directorio config y utilizar el aplication.properties en esa ruta, realizo un empaquetado para producción el perfil es PRODUCCION en fichero application.properties
```
%dev.greeting.message=Hola 
greeting.message=Hola 
./mvnw compile package
java -jar target/getting-started-1.0-SNAPSHOT-runner.jar
``` 
## AÑADIR NUEVOS PERFILES INDEPENDIENTEMENTE DE LOS ANTERIORES 
P.E. Entorno staging como otro en fichero application.properties
```
%staging.greeting.message=Hello
```
Compilación para entorno staging
```
./mvnw compile quarkus:dev -Dquarkus-profile=staging
```

# INYECCIÓN DE DEPENDENCIAS UTILIZA LA ESPECIFICACION CDI
Se utiliza la anotación Inject
```
Logger logger = Logger.getLogger(GreetingResource.class);

@ConfigProperty(name = "greeting.message")
String message;

@Inject
GreetingsService service;

@GET
@Produces(MediaType.TEXT_PLAIN)
public String hello() {
	logger.debug("Hello from debug log");
	return service.toUpperCase(message);
}
```

# MARSHALLING DE DOCUMENTOS JSON
Despues de hacer los cambios para producir un MediaType.APPLICATION_JSON es necesario incluir alguna extensión, para ver las extensiones usamos el goal list-extensions
De esta lista usaremos RESTEasy - JSON-B - quarkus-resteasy-jsonb y ahora si lo deserializa
```
./mvnw quarkus:list-extensions
./mvnw quarkus:add-extension -Dextensions="quarkus-resteasy-jsonb"
```
que lo que hace es incluir la dependencia en el pom.xml
```
<dependency>
  <groupId>io.quarkus</groupId>
  <artifactId>quarkus-resteasy-jsonb</artifactId>
</dependency>
```
Para insertar un valor, crear el método post que consume un json
```
curl -d '{"capacity": 500,"name":"San Miguel"}' -H "Content-type: application/json" -X POST localhost:8080/beer -v
```

# VALIDACION DE LOS PARAMETROS DE ENTRADA
Para añadir esta validación quarkus se integra con java bean validation spec y la implementación que implementa esta es quarkus hibernate validator
```
./mvnw quarkus:add-extension -Dextensions="quarkus-hibernate-validator"
curl -d '{"capacity": 50,"name":""}' -H "Content-type: application/json" -X POST localhost:8080/beer -v
```
Validaciones
```
public class Beer {

    @NotNull
    @NotBlank
    private String name;

    @Min(100)
    private int capacity;

    @NotExpired
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate expired;

    public Beer() {
    }        
```

## VALIDACIONES PROPIAS
CONFIGURANDO EL LOGIN en nuestro application.properties quarkus.log.category."org.acme.quickstart".level=DEBUG

# MICROPROFILE REST CLIENT
```
$ ./mvnw quarkus:list-extensions
$ ./mvnw quarkus:add-extension -Dextensions="quarkus-smallrye-rest-client"
```
## MICROPROFILE REST CLIENT - PASO DE HEADERS

## TESTING EN QUARKUS 
Con QuarkusTest
```
./mvnw test
```

# ACCESO A DATOS
Quarkus usa el estandar JPA como forma de acceso a datos y su implementación Hibernate. Además de esta integración Quarkus también usa Panache como otra implementación de patrones de persistencia. 
Quarkus necesita que los drivers estén compilados de forma nativa hasta ahora H2 MariaDB Microsoft SQL Postgress SQL

Corremos un MariaDB en docker
```
docker run -ti --rm -e MYSQL_ROOT_PASSWORD=developer -e MYSQL_USER=developer -e MYSQL_PASSWORD=developer -p 3306:3306 mariadb:10.4.4
```
Proyecto nuevo
```
mvn io.quarkus:quarkus-maven-plugin:0.18.0:create -DprojectGroupId=org.acme -DprojectArtifactId=getting-started -DclassName="org.acme.quickstart.GreetingResource" -Dpath="/hello"
```
Incluimos las dependencias
```
mvnw quarkus:list-extensions
mvnw quarkus:add-extension -Dextensions="quarkus-resteasy-jsonb, quarkus-smallrye-rest-client, quarkus-jdbc-mariadb, quarkus-jsonb, quarkus-hibernate-orm"
```
Probamos
```
curl -d '{"id": 1,"name":"JC"}' -H "Content-type: application/json" -X POST localhost:8080/dev -v
curl localhost:8080/dev/1
```
## PERSISTENCIA Y PANACHE 
Panache es un framework que implementa DAO Pattern y Active Record Pattern, se podrá utilizar uno u otro
```
mvnw quarkus:add-extension -Dextensions="quarkus-hibernate-orm-panache"
curl -d '{"name":"jc"}' -H "Content-type: application/json" -X POST localhost:8080/entity -v
curl localhost:8080/entity/1
```
Probamos
```
$ curl localhost:8080/entity/maria/35
{"id":2,"age":35,"name":"maria"}
$ curl localhost:8080/entity/maria
{"id":2,"age":35,"name":"maria"}
$ curl localhost:8080/entity
[{"id":1,"name":"jc"},{"id":2,"age":35,"name":"maria"}]
```

## DAO PARTERN CON PANACHE

### SCRIPT DE MIGRACION DE BASE DE DATOS FLYWAY

```
$ curl localhost:8080/dao/JC
{"age":44,"id":1,"name":"JC"}
$ curl -d '{"id": 2,"name":"Ana","age": 44}' -H "Content-type: application/json" -X POST localhost:8080/dao -v
```

```
@ApplicationScoped
public class MigrationService {
    // You can Inject the object if you want to use it manually
    @Inject
    Flyway flyway; 

    public void checkMigration() {
        // This will print 1.0.0
        System.out.println(flyway.info().current().getVersion().toString());
    }
}
```

# QUARKUS REACTIVO

## PROGRAMACION REACTIVA
Utilizamos la programación reactiva o asincrona para crear un hilo para cada una de las request de esta forma cuando se procese se devolverá la respuesta al pool y este dará la respuesta al cliente, con esto se consigue procesar más peticiones por segundo.
```
./mvnw quarkus:list-extensions
./mvnw quarkus:add-extension -Dextensions="quarkus-smallrye-reactive-streams-operators"
```
Probamos
```
$ curl localhost:8080/hello/stream
data: 0
data: 1
data: 2
data: 3
data: 4
```

# REACTIVE MESSAGING IN MEMORY

Comunicación con streams en memoria uso de productor/consumidor y productor a través de request QUARKUS ya tiene IN_MEMORY_MESSAGING 
```
./mvnw quarkus:list-extensions
./mvnw quarkus:add-extension -Dextensions="quarkus-smallrye-reactive-messaging"
```
Probamos
```
$ curl localhost:8080/hello/emit/7
```

## REACTIVE MESSAGING CON KAFKA
El sistema de mensajes es exactamente igual solo cambia la configuración
```
./mvnw quarkus:list-extensions
./mvnw quarkus:add-extension -Dextensions="quarkus-smallrye-reactive-messaging-kafka"
```
Probamos
```
$ curl localhost:8080/message/emit/35
```

## REACTIVE MESSAGING CON AMQP
```
./mvnw quarkus:add-extension -Dextensions="quarkus-smallrye-reactive-messaging-amqp"
$ curl localhost:8080/message/emit/35
```
La cola de mensajeria de Artemis ActiveMQ está en http://localhost:8161/console/login segun el docker-compose user quarkus pass quarkus

## CLIENTE REST ASINCRONO
Usamos el microprofile rest client
```
./mvnw quarkus:add-extension -Dextensions="quarkus-resteasy-jsonb, quarkus-smallrye-rest-client"
```

# QUARKUS CLOUD

## SEGURIDAD CON JWT
```
./mvnw quarkus:list-extensions
./mvnw quarkus:add-extension -Dextensions="quarkus-smallrye-jwt"
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlivFI8qB4D0y2jy0CfEq
sIYPd99ltwxTHjr3npfv/3Lw50bAkbT4HeLFxTx4flEoZLKO/g0bAoV2uqBhkA9x
nQIDAQAB
-----END PUBLIC KEY-----
```
Proyecto security-jwt-quickstart, para la generación de tokens
```
$ mvn exec:java -Dexec.mainClass=org.acme.security.jwt.GenerateToken -Dexec.classpathScope=test
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------< org.acme:security-jwt-quickstart >------------------
[INFO] Building security-jwt-quickstart 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- exec-maven-plugin:1.6.0:java (default-cli) @ security-jwt-quickstart ---
eyJraWQiOiIvcHJpdmF0ZUtleS5wZW0iLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJp
./mvnw clean compile quarkus:dev
curl -H "Authorization: Bearer eyJraWQiOiIvcHJpdmF0ZUtleS5wZW0iLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3F1YXJrdXMuaW8vdXNpbmctand0LXJiYWMiLCJqdGkiOiJhLTEyMyIsInN1YiI6Impkb2UtdXNpbmctand0LXJiYWMiLCJ1cG4iOiJqZG9lQHF1YXJrdXMuaW8iLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJqZG9lIiwiYXVkIjoidXNpbmctand0LXJiYWMiLCJiaXJ0aGRhdGUiOiIyMDAxLTA3LTEzIiwicm9sZU1hcHBpbmdzIjp7Imdyb3VwMSI6Ikdyb3VwMU1hcHBlZFJvbGUiLCJncm91cDIiOiJHcm91cDJNYXBwZWRSb2xlIn0sImdyb3VwcyI6WyJFY2hvZXIiLCJUZXN0ZXIiLCJTdWJzY3JpYmVyIiwiZ3JvdXAyIl0sImlhdCI6MTU4Nzk2ODM2NywiYXV0aF90aW1lIjoxNTg3OTY4MzY3LCJleHAiOjE1ODc5Njg2Njd9.NVKn7UMmaASEEgMVQJqrLPkBS8NO_MbOkKAjtT1wUWV0eUdZuv6tcg4XhXsu59-huWzI1Q8G4DVSjgxu6YMGaHSJL1m9ZvffCyfKpcHIP5TCp8iEd3g6r3_lfOT5LC-S7A04wm1DWXaq5Ozi0KxhHQZkloB0OiIK4lW70zz1QehhD-cDvuobpC0uAx82l7HgbCpbzzpHMWxcaqDRaViqzU4fkLAhGrEXyRh6XbQEsPvJOJ2l2b0TVlxfPx3xDC82_EhRafx_BkgXUYxuHs9cxEETCMDb7ercTq8oKFlGXy4y-TXTN1vv9BXG1yrhC6eqFllg35rlmu9DXP1SZpBQbw" http://localhost:8080/hello
```

## TOLERANCIA A FALLOS PRINCIPALMENTE EN EL ACCESO A OTROS SERVICIOS
```
./mvnw quarkus:add-extension -Dextensions="quarkus-smallrye-fault-tolerance"
```
## HEALT CHECK 
Define dos tipos liveness check si esta preparado para dar respuestas, readiness check si el servicio está arrancado. De mucha utilidad en Kubernetesd
```
./mvnw quarkus:add-extension -Dextensions="quarkus-smallrye-health"
```
Simplemente con esto ya tenemos el health check si hacemos ...
```
$ curl localhost:8080/health
{
    "status": "UP",
    "checks": [
    ]
}
```
Con las clases implementadas
```
$ curl localhost:8080/health/live
{
    "status": "UP",
    "checks": [
        {
            "name": "WorldClockAPIHealtCheck",
            "status": "UP"
        }
    ]
}
$ curl localhost:8080/health/ready
{
    "status": "UP",
    "checks": [
        {
            "name": "Greetings",
            "status": "UP"
        }
    ]
}
```

## METRICAS 
Como se está comportando nuestra aplicación memoria, disco u otras relacionadas con el negocio
```
./mvnw quarkus:add-extension -Dextensions="quarkus-smallrye-metrics"
curl localhost:8080/metrics
```
Si queremos una metrica especial para un método ver MetricsResource.java invocando varias veces a curl localhost:8080/clockwithmetrics y pidiendo metricas ...
```
curl localhost:8080/metrics
application:org_acme_quickstart_metrics_resource_check_time_get_now_seconds_count 6.0
application:org_acme_quickstart_metrics_resource_check_time_get_now_seconds{quantile="0.5"} 0.0017264
application:org_acme_quickstart_metrics_resource_check_time_get_now_seconds{quantile="0.75"} 0.0021085
application:org_acme_quickstart_metrics_resource_check_time_get_now_seconds{quantile="0.95"} 0.3301274
application:org_acme_quickstart_metrics_resource_check_time_get_now_seconds{quantile="0.98"} 0.3301274
application:org_acme_quickstart_metrics_resource_check_time_get_now_seconds{quantile="0.99"} 0.3301274
application:org_acme_quickstart_metrics_resource_check_time_get_now_seconds{quantile="0.999"} 0.3301274
```
Para metricas de logica de negocio ver @Gauge
```
application:org_acme_quickstart_metrics_resource_max_temp 50.0
```

## OPENTRACING
Necesitamos un sevidor donde mandar las trazas
```
docker run -e COLLECTOR_ZIPKIN_HTTP_PORT=9411 -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268 -p 9411:9411 jaegertracing/all-in-one:latest
//añadimos la extension de open-tracing
./mvnw quarkus:list-extensions
./mvnw quarkus:add-extension -Dextensions="quarkus-smallrye-opentracing"
```
Simplemente configurando aplication.properties con estos datos ya disponemos en la url de Jaeger UI http://localhost:16686 los datos de las trazas sobre invocaciones
```
# Configuracion de OpenTracing
quarkus.jaeger.service-name=Greetings
quarkus.jaeger.sampler-type=const
quarkus.jaeger.sampler-param=1
quarkus.jaeger.endpoint=http://localhost:14268/api/traces
```
Si queremos incluir nuestra propia información inyectamos la clase Tracer

# DESPLIEGUE Y ESCALADO 
En Kubernetes
```
./mvnw quarkus:add-extension -Dextensions="quarkus-kubernetes, quarkus-container-image-jib"
```
Esta extesión nos permite que cuando hacemos un package nos incluya los ficheros de kubernetes además se integran con microprofile-test
```
./mvnw compile package -DskipTests
```
Empaquetado
```
./mvnw clean package -Dquarkus.kubernetes.deploy=true -DskipTests
```

# OPEN API
```
./mvnw quarkus:list-extensions
./mvnw quarkus:add-extension -Dextensions="quarkus-smallrye-openapi"
./mvnw clean compile quarkus:dev
curl localhost:8080/openapi
---
openapi: 3.0.1
info:
  title: Generated API
  version: "1.0"
paths:
  /clock:
    get:
      responses:
        200:
          description: OK
          content:
            application/json:
              schema:
                type: object
                properties:
                  currentDateTime:
                    type: string
					
```
Además cuento con swagger en localhost:8080/swagger-ui

# MANDAR EMAIL
```
./mvnw quarkus:list-extensions
./mvnw quarkus:add-extension -Dextensions="quarkus-mailer"
```
En dev el correo se simula por la consola cuando se invoca
```
2020-04-27 13:59:47,775 INFO  [quarkus-mailer] (executor-thread-1) Sending email Han consultado el tiempo from test@example.com to [clock@sample.com], text body:
La hora cet es: 2020-04-27T13:59+02:00
html body:
null
```
# QUARKUS SCHEDULER

```
./mvnw quarkus:list-extensions
./mvnw quarkus:add-extension -Dextensions="quarkus-scheduler"
./mvnw compile quarkus:dev
```