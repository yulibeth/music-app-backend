# Music App 🎵
Este proyecto es una aplicación de gestión de listas de reproducción de música desarrollada en **Spring Boot**, con base de datos **H2** en memoria. La arquitectura del proyecto sigue un **modelo de capas**, donde la lógica de negocio, la capa de persistencia y la capa de presentación (API) están separadas para facilitar el mantenimiento y la escalabilidad.

## Arquitectura del Proyecto
El proyecto sigue un modelo de capas que consta de:

-Capa de presentación (API): expone los endpoints REST para interactuar con el sistema.
-Capa de servicio: contiene la lógica de negocio, donde se manejan las operaciones de las listas de reproducción.
-Capa de persistencia: interactúa con la base de datos utilizando Spring Data JPA y las entidades correspondientes.

## Estructura del proyecto:
```bash
src/main/java/com/quipux/music-app-backend
    ├── controller       # Controladores REST
    ├── model            # Entidades JPA
    ├── repository       # Repositorios JPA
    ├── service          # Lógica de negocio
    └── security         # Configuración de seguridad (JWT)

src/test/java/com/quipux/music-app-backend
    ├── controller       # Pruebas del controlador
    ├── service          # Pruebas del servicio
    └── repository       # Pruebas del repositorio
```

## Características
- Crear listas de reproducción.
- Consultar todas las listas de reproducción.
- Buscar una lista de reproducción por nombre.
- Eliminar una lista de reproducción específica.
- Autenticación con tokens JWT para acceder a los endpoints protegidos.

## Tecnologías utilizadas
- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven
- JUnit 5 (para pruebas unitarias)
- JWT (JSON Web Tokens) para la autenticación

## Requisitos previos
- JDK 17 o superior
- Maven 3.6 o superior

## Configuración del proyecto
1. Clona el repositorio:
   ```bash
   git clone https://github.com/yulibeth/music-app-backend.git
   ```
2. Accede al directorio del proyecto:
   ```bash
   cd music-app-backend
   ```
3. Ejecuta la aplicación:
   ```bash
   mvn spring-boot:run
   ```

## Acceso a la base de datos H2
La base de datos H2 es accesible desde el navegador:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Usuario: `sa`
- Contraseña: (vacío)

##Servicio de Autenticación
Para interactuar con los endpoints protegidos de la aplicación, es necesario obtener un token JWT. Puedes hacerlo realizando una petición POST al endpoint de login:

```bash
curl --location --request POST 'http://localhost:8080/auth/login?username=usuario1'
```
El servidor devolverá un token JWT que debe incluirse en las cabeceras de las peticiones subsecuentes. Por ejemplo, para crear una lista de reproducción o eliminar una existente, deberás pasar el token generado en la cabecera Authorization:

```bash
curl --location --request POST 'http://localhost:8080/lists' \
--header 'Authorization: Bearer <TOKEN>'
```

## Endpoints disponibles
- **POST /auth/login** - Autenticación de usuario y obtención de token JWT.
- **POST /lists** - Crear una nueva lista de reproducción
- **GET /lists** - Obtener todas las listas de reproducción
- **GET /lists/{listName}** - Obtener una lista de reproducción por nombre
- **DELETE /lists/{listName}** - Eliminar una lista de reproducción por nombre

## Pruebas
Para ejecutar las pruebas unitarias, utiliza el siguiente comando:

```bash
mvn test
```

## Contribuciones
¡Las contribuciones son bienvenidas! Por favor, abre un issue o envía un pull request.

---

**Autor:** Yulibeth Jimenez  
**GitHub:** [https://github.com/yulibeth](https://github.com/yulibeth)
