# MeLi Challenge SpringBoot API

Proyecto realizado en [Spring Boot](http://projects.spring.io/spring-boot/) para prueba técnica.<br>
La aplicación se comporta como una API REST donde básicamente se pueden verificar secuencias de ADN representadas en un array de strings, de manera que la API validará si la secuencia es de un humano o si se trata de un mutante. Una secuencia de ADN mutante tiene la particularidad de que en su estructura, de tipo matricial, se repiten al menos más de un grupo de 4 bases nitrogenadas consecutivas ya sea de manera vertical, horizontal u oblicua. <br>
La API también registra un historial de cada ADN verificado que se aloja en una base de datos de MongoDB, y así disponemos de un endpoint para consultar las estadísticas de la cantidad de secuencias de ADN mutante, humano y el ratio y/o relación entre ellas.

## Requerimientos

Para construir y ejecutar la aplicación es necesario:

- [JDK 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)

## Ejecutar la aplicación de forma local

Hay muchas maneras de ejecutar una aplicación Spring Boot en tu entorno local. Una de ellas es ejecutar el método `main` en la clase `com.meli.challenge.DnaApplication` desde su IDE.

Alternativamente puede usar [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) de la siguiente forma:

```shell
mvn spring-boot:run
```

## Uso de la aplicación

La aplicación tiene disponibles los siguientes endpoints:

```shell
GET /dna/

Retorna la lista completa de secuencias de ADN verificadas

Respuestas: 
 - status 200 | Operación exitosa
 - status 404 No encontrado
```

```shell
GET /dna/stats

Retorna la información de la cantidad de secuencias de ADN mutantes, humanos y el ratio.

Respuestas: 
 - status 200 | Operación exitosa
```

```shell
POST /dna/mutant

Analiza si una secuencia de ADN es de un humano o mutante y guarda la secuencia en base de datos.

Ejemplo Payload:
  {
    "dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
  } 

Respuestas: 
 - status 200 | Secuencia de ADN Mutante
 - status 403 | Secuencia de ADN Humano
 - status 400 | Secuencia con errores
```
<br>
La aplicación también se encuentra alojada en AWS Elastic Beanstalk y usa codePipeline para CI/CD con github. <br>
Por lo tanto podrá probar de manera inmediata con los siguientes endpoints:

```shell
curl --location --request GET 'http://dna-challenge.us-east-1.elasticbeanstalk.com/dna/'

curl --location --request GET 'http://dna-challenge.us-east-1.elasticbeanstalk.com/dna/stats/'

curl --location --request POST 'http://dna-challenge.us-east-1.elasticbeanstalk.com/dna/mutant/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "dna":["ATGCGA","CAGTGC","TTAcTGT","AGAAGG","CCCCTA","TCACTG"]
}'
```


## Powered by
Carlos Fabián Zuluaga Alzate
