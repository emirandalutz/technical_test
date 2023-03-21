## Contenido
1. [Información General](#informacion-general)
2. [Tecnología](#tecnologia)
3. [Instalación](#instalacion)
4. [Pruebas](#pruebas) 
5. [Bonus](#bonus)

### Informacion General
***
Este proyecto se ha desarrollado como un microservicio encargado de simular 
la acción de registrarse en una plataforma.

## Tecnologías
***
Las tecnologías usadas en el proyecto son:
* Java 11
* Maven
* Spring-Boot con tomcat embebido
* Postman

## Dependencias
***
Para el proyecto se han utilizado las siguientes dependencias:
* Spring Security
* Spring JPA
* Spring Web
* Spring Validation
* Open API(Swagger)
* JWT
* Spock Framework


## Instalación
***
Descargar proyecto desde repositorio
```
$ git clone https://github.com/emirandalutz/technical_test

```
Una vez descargado el proyecto nos posicionamos en la carpeta raíz y ejecutamos los comandos en el siguiente
orden en el terminal a elección, para efectos de este manual fue utilizado git bash.

```
$ ./mvnw clean install

$ ./mvnw spring-boot:run

```
Al ejecutar el comando clean install se ejecutaran los test realizados y que son necesarios
para la generación del artefacto de la aplicación.

## Pruebas
***
En postman generamos un request de tipo POST a la siguiente URL:

(localhost:8080/register)

Nos dirigimos a la pestaña Body -> raw y seleccionamos formato JSON, se presenta imagen de referencia

![Imagen de ejemplo](https://github.com/emirandalutz/technical_test/blob/main/src/main/resources/static/postman_config.jpg)

Dentro del body la estructura de la información debe ser como el siguiente ejemplo:

```
{
    "name": "Pedro Fernandez",
    "email": "p.fernandez@test.com",
    "password": "Technical*2023",
    "phones": [
                {
                "number": "1234567",
                "cityCode": "1",
                "countryCode": "57"
                },
                {
                "number": "7654321",
                "cityCode": "2",
                "countryCode": "54"
                }
            ]
}
```
### Consideraciones

Para los campos name, email y password se ejecutan las siguientes validaciones:

* Validación de cadenas vacias.
* Email es validada a traves de una expresion regular que verifica que el correo cumpla
con el formato. Algunos ejemplos de correo:
  * test@dominio.com
  * test1@dominio.com
  * test.dos@dominio.com
  * test-dos@dominio.com
  * test_dos@dominio.com
  * @dominio.com (Inválido)
* Password es validado a traves de expresion regular y debe tener las siguientes condiciones
  * Al menos una letra minuscula
  * Al menos una letra mayuscula
  * Al menos un número
  * Al menos un caracter especial indicado ($#-*)
  * Largo de 8 a 20 caracteres.

Las expresiones regulares pueden ser modificadas en el archivo que se encuentra en src->main->resources->application.properties.

### Respuesta API

La API entregara un resultado en JSON que podra ser consumido si es necesario.

```
{
    "id": "8da58fde-488e-4426-88f8-0e13dbb5deae",
    "created": "2023-03-20T16:30:44.6088277",
    "modified": "2023-03-20T16:30:44.6088277",
    "last_login": "2023-03-20T16:30:44.6088277",
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NzkzNDA2NTQsImlhdCI6MTY3OTM0MDY0NCwianRpIjoiOGRhNThmZGUtNDg4ZS00NDI2LTg4ZjgtMGUxM2RiYjVkZWFlIn0.59DW_MmvRe1yx773pBuc0nZwa1ff_hCSGqjWfHGjz_8",
    "active": true
}
```
##  Bonus
***
### Carga de base de datos H2

Para la creación de la base de datos y sus tablas se han dispuesto los script iniciales
en un archivo llamado "schema.sql" que se encuentra en el directorio src->main->resources.

![Propiedad de BD H2](https://github.com/emirandalutz/technical_test/blob/main/src/main/resources/static/properties_bd.jpg)

Este script es ejecutado al iniciar la aplicación de forma automática.

### Conexión a consola de administración de base de datos

Una vez iniciada la aplicación se podra acceder a la consola de base de datos en memoria
con el fin de verificar las inserciones de la aplicación. Se puede acceder a traves de la siguiente URL:

(http://localhost:8080/h2-console/)

![Consola H2](https://github.com/emirandalutz/technical_test/blob/main/src/main/resources/static/h2_console.jpg)

Se especifica la base de datos que es "test", los credenciales de acceso son:

  * User Name: test
  * Password: password