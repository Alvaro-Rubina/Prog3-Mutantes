<a id="readme-top"></a>
  
# Parcial Programación III: Mutantes

Esta API ha sido desarrollada con el fin de detectar si un humano es mutante basándose en su secuencia de ADN. La secuencia de ADN se envía como un array de Strings, donde cada String representa una fila de una tabla (NxN). Si se encuentran más de una secuencia de cuatro letras iguales, de forma oblicua, horizontal o vertical, se reconoce el ADN como mutante. 

La aplicación ha sido desplegada en Render para su fácil acceso, además de contar con pruebas unitarias para verificar su correcto funcionamiento.

## Tecnologías Utilizadas

* [![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
* [![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=flat&logo=hibernate&logoColor=white)](https://hibernate.org/)
* [![MapStruct](https://img.shields.io/badge/MapStruct-000000?style=flat&logo=mapstruct&logoColor=orange)](https://mapstruct.org/)
* [![JUnit](https://img.shields.io/badge/JUnit-25A162?style=flat&logo=junit5&logoColor=white)](https://junit.org/junit5/)
* [![Mockito](https://img.shields.io/badge/Mockito-00A3E0?style=flat&logo=mockito&logoColor=white)](https://site.mockito.org/)
* [![H2](https://img.shields.io/badge/H2_Database-000000?style=flat&logo=h2&logoColor=white)](https://www.h2database.com/html/main.html)
* [![Lombok](https://img.shields.io/badge/Lombok-3F4B3E?style=flat&logo=lombok&logoColor=white)](https://projectlombok.org/)
* [![Render](https://img.shields.io/badge/Render-4D4D4D?style=flat&logo=render&logoColor=white)](https://render.com/)

## Requerimientos Previos

Para probar el proyecto necesitás tener instalado **Postman**, ya que mediante este programa vas a poder realizar las distintas consultas.


## Modo de Uso

> [!NOTE]
> **Endpoint para verificar ADN mutante**: [https://mutantes.onrender.com/mutant/](https://mutantes.onrender.com/mutant/)
* Para verificar si un ADN corresponde a un mutante, debes enviar una solicitud **POST** con un JSON al endpoint `/mutant/` de la API. El JSON debe seguir este formato:

```json
{
    "name": "John Doe",
    "dna": [
        "ACTACG",
        "GTCTCG",
        "ACAGTA",
        "TTCAGT",
        "CCTCTA",
        "TAGCCA"
    ]
}
```
Donde `name` es el nombre del humano y `dna` es un array de Strings que representa la secuencia de ADN. Para que el ADN sea considerado válido, debe ser una matriz `NxN` de al menos dimension 4x4. Por ejemplo, si hay 5 Strings, cada uno debe tener 5 letras.


> [!NOTE]
> **Endpoint para obtener estadísticas**: [https://mutantes.onrender.com/stats/](https://mutantes.onrender.com/stats/)
  
* Si deseas obtener estadísticas sobre las verificaciones de ADN, podés hacer una solicitud **GET** al endpoint `/stats/`. Esto te devolverá un JSON con la siguiente información:

```json
{
    "countMutantDna": 1,
    "countHumanDna": 0,
    "ratio": 0.0
}
```
`countMutantDna` indica la cantidad ADN mutantes han sido verificados.

`countHumanDna` indica la cantidad de ADN humanos que han sido verificados.

`ratio` representa la proporción de ADN mutante respecto al total de ADN verificados.

## Ejemplos de ADN

### Ejemplo 1: ADN 6x6 con 2 secuencias (MUTANTE)

| G | C | A | A | A | A |
|---|---|---|---|---|---|
| T | A | C | C | G | T |
| T | C | T | A | C | T |
| A | G | T | G | A | G |
| C | A | T | T | C | T |
| G | A | T | G | A | A |


> En este caso, se encuentra una secuencia horizontal de "AAAA" empezando en (0,2) y una secuencia vertical de "TTTT" empezando en (2,2).

### Ejemplo 2: ADN 5x5 con 1 secuencia (NO MUTANTE)

| G | C | T | A | A |
|---|---|---|---|---|
| T | C | G | C | T |
| A | C | A | T | T |
| A | C | C | G | G |
| C | A | T | T | C |


> En este caso, se encuentra una secuencia vertical de "CCCC" empezando en (0,1) pero no es suficiente para verificar el ADN como mutante.

### Ejemplo 3: ADN 4x4 sin ninguna secuencia (NO MUTANTE)

| A | C | A | C |
|---|---|---|---|
| T | G | T | G |
| A | C | A | C |
| T | G | T | G |


> En este caso no hay ninguna secuencia de 4 letras iguales en ninguna dirección.

### Ejemplo 4: ADN NxM (Inválido)

| A | T | G | C |
|---|---|---|---|
| C | A | G | T |
| T | A | T | G |


> En este caso el ADN tiene una dimension de 3x4, por ende no cumple el requisito de ser NxN y no puede ser analizado.

<p align="right">(<a href="readme-top">Volver al inicio</a>)</p>
