# Satapp
Hecho con Spring Boot 3

## Cómo Desplegar o Arrancar en Localhost

Para ejecutar la aplicación en tu entorno local, sigue estos pasos:

1. **Clona el Repositorio:**
   ```bash
   git clone https://github.com/cpcx04/Satapp
   cd SataApp
   ```
   
2. **Haz docker compose:**
   Inicia Docker para poder arrancar la base de datos en un contenedor y seguido esto ejecutamos este comando
   ```bash
   docker-compose up -d
   ```
   
4. **Inicia la Aplicación:**
   ```bash
   cd SataApp
   ```
   Y dentro de nuestro IDE ejecutamos `mvn:spring-boot-run` y nuestra API REST estará funcionando en `http://localhost:8080`
   
5. **Para acceder a la base de datos**
   - Nos dirigiremos en nuestro navegador a `http://localhost:5050` e iniciaremos sesión con estas credenciales
     - Username: admin@admin.com
     - Password: admin
   - Podemos crear un servidor para producción y otro para desarrollo, para desarrollo tenemos estas credenciales:
     - host: pg_dev
     - username: postgres
     - password: 12345678
  - Para producción solo cambiará el host
     - host: pg_dev
     - username: postgres
     - password: 12345678

   - Por último podemos gestionar en el archivo application.properties, qué perfil de base de datos se usará: 
   `spring.profiles.active=dev`.
    De esta manera el perfil de base de datos utilizado es el de desarrollo y podemos cambiarlo a producción, sustituyendo `dev` por `prod`

6. **Credenciales**: Las credenciales que se pueden usar para probar la API REST:
- En el perfil de desarrollo se pueden hacer pruebas con estas cuentas o añadiendo más:
     - Admin:
       - username: admin
       - password: admin1
     - User (3 cuentas):
       - username: user1, user2, y user3
       - password: user1234

- En el perfil de producción se pueden hacer pruebas con la cuenta de admin, que es la única añadida hasta el momento, así como tampoco hay datos de prueba; tampoco hay cuentas de usuario activas:
   - Admin:
     - username: admin
     - password: admin1
  
6. **Disfruta:**
   - Desde los contribuidores de este repositorio esperamos que puedas disfrutar de este proyecto :)
  ###Informacion extra detallada
## Colección de Postman

Adjunto de la coleccion de postman para poder exportarla a su framework

**[SataApp](SataApp-CristianPulidoCabello.postman_collection.json)**

# SataApp - Guía de Uso

Esta guía proporciona pasos detallados para ejecutar la aplicación SataApp de manera óptima. Asegúrate de tener las dependencias necesarias instaladas y el servidor en ejecución antes de seguir estos pasos.

## Iniciar Sesión

### Para Administradores

1. **Login como Admin:**
   - Utiliza la solicitud "loginAsAdmin" en Postman para iniciar sesión como administrador.
   - Endpoint: `http://localhost:8080/auth/login`
   - Método: `POST`
   - Credenciales de Prueba:
     - Usuario: `admin1`
     - Contraseña: `admin1`
   - Guarda el token de acceso en las variables de colección.

## Validación de Usuarios

1. **Validar Usuarios:**
   - Usa la solicitud "ValidationUsers" para validar un usuario específico.
   - Endpoint: `http://localhost:8080/users/{userId}/validate`
   - Método: `PUT`
   - Reemplaza `{userId}` con el ID del usuario a validar.
  
2. **Lista de Usuarios No Validados:**
   - Utiliza "noValidatedUserList" para obtener la lista de usuarios no validados.
   - Endpoint: `http://localhost:8080/users/no-validated`
   - Método: `GET`

## Administración de Inventario

1. **Añadir Elemento al Inventario:**
   - Usa "AddInventariable" para agregar un elemento al inventario.
   - Endpoint: `http://localhost:8080/inventariable`
   - Método: `POST`
   - Proporciona los detalles del inventario en el cuerpo de la solicitud.

2. **Editar Elemento de Inventario:**
   - Utiliza "editInvetariable" para editar un elemento específico del inventario.
   - Endpoint: `http://localhost:8080/inventariable/{inventoryId}`
   - Método: `PUT`
   - Reemplaza `{inventoryId}` con el ID del elemento a editar.
   - Proporciona los detalles actualizados en el cuerpo de la solicitud.

3. **Eliminar Elemento del Inventario:**
   - Usa "deleteItem" para eliminar un elemento del inventario.
   - Endpoint: `http://localhost:8080/inventariable/{inventoryId}`
   - Método: `DELETE`
   - Reemplaza `{inventoryId}` con el ID del elemento a eliminar.

## Gestión de Tickets

1. **Obtener Todos los Tickets:**
   - Utiliza "getTickets" para obtener todos los tickets.
   - Endpoint: `http://localhost:8080/ticket`
   - Método: `GET`

2. **Obtener Tickets de un Elemento del Inventario:**
   - Usa "getTicketsFromInventariable" para obtener tickets asociados a un elemento específico del inventario.
   - Endpoint: `http://localhost:8080/ticket/inventariable/{inventoryId}`
   - Método: `GET`
   - Reemplaza `{inventoryId}` con el ID del elemento del inventario.

3. **Asignar un Ticket a un Usuario:**
   - Utiliza "assignTicket" para asignar un ticket a un usuario específico.
   - Endpoint: `http://localhost:8080/ticket/{ticketId}/asignar`
   - Método: `PUT`
   - Reemplaza `{ticketId}` con el ID del ticket a asignar.
   - Proporciona los detalles de la asignación en el cuerpo de la solicitud.

4. **Editar Estado de un Ticket:**
   - Usa "editStatus" para editar el estado de un ticket específico.
   - Endpoint: `http://localhost:8080/ticket/{ticketId}/estado`
   - Método: `PUT`
   - Reemplaza `{ticketId}` con el ID del ticket a editar.
   - Proporciona los detalles del nuevo estado en el cuerpo de la solicitud.

5. **Eliminar un Ticket:**
   - Utiliza "deleteTicket" para eliminar un ticket específico.
   - Endpoint: `http://localhost:8080/ticket/{ticketId}`
   - Método: `DELETE`
   - Reemplaza `{ticketId}` con el ID del ticket a eliminar.

6. **Obtener Tickets Asignados al Usuario Logueado:**
   - Usa "getAssignedTickets" para obtener todos los tickets asignados al usuario autenticado.
   - Endpoint: `http://localhost:8080/ticket/asignados/me`
   - Método: `GET`

---
# Controlador de Usuario

Este controlador maneja las operaciones relacionadas con los usuarios en la API REST de la aplicación. Proporciona endpoints para la creación, autenticación, validación y recuperación de usuarios.

## Descripción de los Endpoints

### Registro de Usuario

- Método: `POST`
- URL: `/auth/register`
- Descripción: Crea un nuevo usuario en la plataforma.
- Respuesta exitosa (código 201):
  - Devuelve un token JWT y los detalles del usuario recién creado.
  - Ejemplo de respuesta:
    ```json
    {
        "id": "ff45a024-2acc-4fcf-b99b-ad8e39d8d2f7",
        "username": "cristian2",
        "email": "user@gmail.com",
        "nombre": "Cristian Pulido",
        "role": "ROLE_USER",
        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9...."
    }
    ```
- Respuesta de error (código 400): Si ocurre un error al registrar al usuario.

### Inicio de Sesión de Usuario

- Método: `POST`
- URL: `/auth/login`
- Descripción: Autentica al usuario y genera un token JWT para su sesión.
- Respuesta exitosa (código 201):
  - Devuelve un token JWT y los detalles del usuario autenticado.
  - Ejemplo de respuesta:
    ```json
    {
        "id": "4066d638-45c1-43dc-8276-06f20c562f5a",
        "username": "cristian3",
        "email": "user@gmail.com",
        "nombre": "Cristian Pulido",
        "role": "ROLE_USER",
        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9...."
    }
    ```
- Respuesta de error (código 400): Si las credenciales del usuario son inválidas.

### Validación de Usuario por Administrador

- Método: `PUT`
- URL: `/users/{uuid}/validate`
- Descripción: Valida a un usuario por parte de un administrador.
- Respuesta exitosa (código 200):
  - Devuelve los detalles del usuario validado.
  - Ejemplo de respuesta:
    ```json
    {
        "id": "3f0190ac-ebef-4fc2-99c9-5d44016da63a",
        "username": "cristian1",
        "email": "cristian@example.com",
        "nombre": "Cristian Garcia",
        "role": "ROLE_USER",
        "createdAt": null
    }
    ```
- Respuesta de error (código 403): Si se deniega el acceso.
- Respuesta de error (código 400): Si no se encuentra al usuario.

### Obtener Todos los Usuarios No Validados

- Método: `GET`
- URL: `/users/no-validated`
- Descripción: Obtiene una lista de todos los usuarios no validados.
- Respuesta exitosa (código 201):
  - Devuelve una lista de usuarios no validados.
  - Ejemplo de respuesta:
    ```json
    [
        {
            "id": "8b6932a7-aeed-4b4c-bc95-98b9a6817851",
            "username": "paco2",
            "email": "paco@example.com",
            "nombre": "Paco Martinez",
            "role": "ROLE_USER",
            "createdAt": null
        },
        // Otros usuarios no validados...
    ]
    ```
- Respuesta de error (código 400): Si no se encuentran usuarios no validados.

## Uso del Controlador

Este controlador proporciona endpoints para el registro, inicio de sesión y validación de usuarios en la plataforma. Además, permite a los administradores validar a los usuarios y obtener una lista de usuarios no validados en la base de datos.
# Controlador de Inventario

Este controlador maneja las operaciones relacionadas con el inventario en la API REST de la aplicación. Proporciona endpoints para obtener, editar, eliminar y crear elementos de inventario.

## Descripción de los Endpoints

### Obtener Todos los Elementos de Inventario

- Método: `GET`
- URL: `/inventariable`
- Descripción: Obtiene todos los elementos de inventario almacenados en la base de datos.
- Respuesta exitosa (código 201):
  - Devuelve una lista de elementos de inventario.
  - Ejemplo de respuesta:
    ```json
    [
        {
            "id": "3f0190ac-ebef-4fc2-99c9-5d44016da63a",
            "type": "LAPTOP",
            "description": "...",
            "status": "...",
            "location": "...",
            "additionalDetails": "...",
            "relatedTickets": [...]
        },
        // Otros elementos de inventario...
    ]
    ```
- Respuesta de error (código 400): Si no se encuentran elementos de inventario.

### Obtener un Elemento de Inventario por ID

- Método: `GET`
- URL: `/inventariable/{uuid}`
- Descripción: Obtiene un elemento de inventario específico por su ID.
- Respuesta exitosa (código 200):
  - Devuelve los detalles del elemento de inventario encontrado.
  - Ejemplo de respuesta:
    ```json
    {
        "id": "3f0190ac-ebef-4fc2-99c9-5d44016da63a",
        "type": "LAPTOP",
        "description": "...",
        "status": "...",
        "location": "...",
        "additionalDetails": "...",
        "relatedTickets": [...]
    }
    ```
- Respuesta de error (código 404): Si no se encuentra ningún elemento de inventario con ese ID.

### Crear un Nuevo Elemento de Inventario

- Método: `POST`
- URL: `/inventariable`
- Descripción: Crea un nuevo elemento de inventario.
- Respuesta exitosa (código 201):
  - Devuelve los detalles del nuevo elemento de inventario creado.
  - Ejemplo de respuesta:
    ```json
    {
        "id": "...",
        "type": "...",
        "description": "...",
        "status": "...",
        "location": "...",
        "additionalDetails": "...",
        "relatedTickets": []
    }
    ```
- Respuesta de error (código 400): Si no se crea el elemento de inventario.

### Editar un Elemento de Inventario por ID

- Método: `PUT`
- URL: `/inventariable/{uuid}`
- Descripción: Edita un elemento de inventario existente por su ID.
- Respuesta exitosa (código 200):
  - Devuelve los detalles del elemento de inventario editado.
  - Ejemplo de respuesta:
    ```json
    {
        "id": "...",
        "type": "...",
        "description": "...",
        "status": "...",
        "location": "...",
        "additionalDetails": "...",
        "relatedTickets": [...]
    }
    ```
- Respuesta de error (código 404): Si no se encuentra ningún elemento de inventario con ese ID.

### Eliminar un Elemento de Inventario por ID

- Método: `DELETE`
- URL: `/inventariable/{uuid}`
- Descripción: Elimina un elemento de inventario por su ID.
- Respuesta exitosa (código 204): Indica que el elemento de inventario se ha eliminado correctamente.
- Respuesta de error: Puede lanzar una excepción si existen tickets relacionados con el elemento de inventario.

## Uso del Controlador

El controlador `InventoryController` ofrece endpoints para obtener, editar, eliminar y crear elementos de inventario. Cada endpoint tiene su descripción, método HTTP correspondiente, URL y ejemplos de respuestas exitosas y de error.

---
# Controlador de Tickets

Este controlador maneja las operaciones relacionadas con los tickets en la API REST de la aplicación. Proporciona endpoints para crear, editar, eliminar y obtener información sobre los tickets, así como para asignar tickets a usuarios y cambiar su estado.

## Descripción de los Endpoints

### Crear un Nuevo Ticket

- Método: `POST`
- URL: `/ticket`
- Descripción: Crea un nuevo ticket.
- Respuesta exitosa (código 201):
  - Devuelve los detalles del ticket creado.
  - Ejemplo de respuesta:
    ```json
    {
        "uuid": "...",
        "description": "...",
        "status": "...",
        "createdByUsername": "...",
        "assignedTo": "...",
        "relatedInventoryItem": "..."
    }
    ```
- Respuesta de error (código 400): Si la creación del ticket no tiene éxito.

### Editar un Ticket

- Método: `PUT`
- URL: `/ticket/{uuid}`
- Descripción: Edita un ticket existente.
- Respuesta exitosa (código 200):
  - Devuelve los detalles del ticket editado.
  - Ejemplo de respuesta:
    ```json
    {
        "ticketId": "...",
        "description": "...",
        "status": "...",
        "createdByUsername": "...",
        "assignedTo": "...",
        "relatedInventoryItem": "..."
    }
    ```
- Respuesta de error (código 404): Si no se encuentra el ticket.

### Eliminar un Ticket

- Método: `DELETE`
- URL: `/ticket/{uuid}`
- Descripción: Elimina un ticket existente.
- Respuesta exitosa (código 204): No devuelve ningún contenido.
- Respuesta de error (código 404): Si no se encuentra el ticket.

### Obtener Todos los Tickets

- Método: `GET`
- URL: `/ticket`
- Descripción: Obtiene todos los tickets de la base de datos.
- Respuesta exitosa (código 200):
  - Devuelve una lista de todos los tickets.
  - Ejemplo de respuesta:
    ```json
    [
        {
            "ticketId": "...",
            "description": "...",
            "status": "...",
            "createdByUsername": "...",
            "assignedTo": "...",
            "relatedInventoryItem": "..."
        },
        // Otros tickets...
    ]
    ```
- Respuesta de error (código 400): Si no se encuentran tickets.

### Asignar Tickets a Usuarios

- Método: `PUT`
- URL: `/ticket/{uuid}/asignar`
- Descripción: Asigna un ticket a un usuario.
- Respuesta exitosa (código 200):
  - Devuelve los detalles del ticket asignado.
  - Ejemplo de respuesta:
    ```json
    {
        "ticketId": "...",
        "description": "...",
        "status": "...",
        "createdByUsername": "...",
        "assignedTo": "...",
        "relatedInventoryItem": "..."
    }
    ```
- Respuesta de error (código 404): Si no se encuentra el ticket.

### Cambiar Estado de un Ticket

- Método: `PUT`
- URL: `/ticket/{uuid}/estado`
- Descripción: Cambia el estado de un ticket.
- Respuesta exitosa (código 200):
  - Devuelve los detalles del ticket con el estado actualizado.
  - Ejemplo de respuesta:
    ```json
    {
        "ticketId": "...",
        "description": "...",
        "status": "...",
        "createdByUsername": "...",
        "assignedTo": "...",
        "relatedInventoryItem": "..."
    }
    ```
- Respuesta de error (código 404): Si no se encuentra el ticket.

### Obtener Tickets Asignados al Usuario Actual

- Método: `GET`
- URL: `/ticket/asignados/me`
- Descripción: Obtiene todos los tickets asignados al usuario actual.
- Respuesta exitosa (código 200):
  - Devuelve una lista de tickets asignados al usuario actual.
  - Ejemplo de respuesta:
    ```json
    [
        {
            "ticketId": "...",
            "description": "...",
            "status": "...",
            "createdByUsername": "...",
            "assignedTo": "...",
            "relatedInventoryItem": "..."
        },
        // Otros tickets asignados...
    ]
    ```
- Respuesta de error (código 404): Si no se encuentran tickets asignados al usuario actual.

## Uso del Controlador

El controlador `TicketController` proporciona endpoints para realizar operaciones CRUD en los tickets. Cada endpoint tiene su descripción, método HTTP correspondiente, URL y ejemplos de respuestas exitosas y de error.

---



Cada endpoint tiene su descripción, método HTTP correspondiente, URL y ejemplos de respuestas exitosas.

---
