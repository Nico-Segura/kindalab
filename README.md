# 🚀 Elevator System

Este proyecto implementa un **sistema de gestión de ascensores** con soporte para ascensores públicos y de carga, incluyendo validación de acceso con keycard y control de peso.

## 📌 Características

✅ Soporte para **ascensores públicos y de carga**  
✅ Validación de **keycards** para acceso a pisos restringidos en caso de ascensores publicos  
✅ Control de **peso máximo permitido**  
✅ **Alarmas** cuando se excede el peso  
✅ API RESTful con **Spring Boot**  
✅ Manejo centralizado de **excepciones con `@RestControllerAdvice`**  
✅ Cobertura de **tests con JaCoCo**  

---

## 📂 Estructura del Proyecto

```
📦 elevator-system
├── 📂 src
│   ├── 📂 main
│   │   ├── 📂 java/com/kindalab/elevatorsystem
│   │   │   ├── 📂 controller   # Controladores REST
│   │   │   ├── 📂 model        # Clases de negocio
│   │   │   ├── 📂 service      # Lógica de negocio
│   │   │   ├── 📂 exception    # Manejo de excepciones
│   │   ├──📂 resources
│   │      ├── 📂 application.properties   # Properties (Manejo de pesos maximos y pisos restrigidos para ascensores publicos)
│   ├── 📂 test  # Pruebas unitarias con JUnit y Mockito
├── 📄 pom.xml   # Configuración de Maven y JaCoCo
├── 📄 README.md # Documentación del proyecto
```

---

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot**
- **Maven**
- **JUnit 5 y Mockito** (para testing)
- **JaCoCo** (para cobertura de código)
- **Swagger** (para documentar la API)

---

## 🏗️ Instalación y Ejecución

### 1️⃣ **Clonar el repositorio**
```bash
git clone https://github.com/tu-usuario/elevator-system.git
cd elevator-system
```

### 2️⃣ **Compilar y ejecutar**
```bash
mvn clean install
mvn spring-boot:run
```

### 3️⃣ **Acceder a la API**
El servidor se ejecutará en **`http://localhost:8080`**.

Para ver los endpoints documentados en **Swagger**, visita:
```
http://localhost:8080/swagger-ui/
```

---

## 🛠️ API Endpoints

### **📌 Ascensor Público (`/elevator/public`)**
| Método | Endpoint | Descripción                                                                           |
|--------|----------|---------------------------------------------------------------------------------------|
| `POST` | `/move`  | Mueve el ascensor al piso seleccionado (se debe enviar `floor` como parámetro y `keycard` en el cuerpo de la solicitud si es necesario para pisos restringidos). |
| `POST` | `/add-weight?weight=500` | Agrega peso al ascensor.                                                              |
| `POST` | `/remove-weight?weight=200` | Remueve peso del ascensor.                                                            |
| `POST` | `/reset-alarm` | Resetea la alarma del ascensor.                                                       |
| `GET`  | `/current-floor` | Obtiene el piso actual del ascensor.                                                  |
| `GET`  | `/current-weight` | Obtiene el peso actual del ascensor.                                                  |

### **📌 Ascensor de Carga (`/elevator/freight`)**
| Método | Endpoint | Descripción                                 |
|--------|----------|---------------------------------------------|
| `POST` | `/move?floor=3` | Mueve el ascensor al piso seleccionado (3). |
| `POST` | `/add-weight?weight=1000` | Agrega peso al ascensor.                    |
| `POST` | `/remove-weight?weight=500` | Remueve peso del ascensor.                  |
| `POST` | `/reset-alarm` | Resetea la alarma del ascensor.             |
| `GET`  | `/current-floor` | Obtiene el piso actual del ascensor.        |
| `GET`  | `/current-weight` | Obtiene el peso actual del ascensor.        |

---

## ✅ Pruebas

### **Ejecutar los tests**
```bash
mvn test
```

### **📊 Generar Reporte de Cobertura de Código**
```bash
mvn jacoco:report
```
Luego abre el archivo:
```
target/site/jacoco/index.html
```

---


## 🎯 Conclusión
El foco principal de este proyecto estuvo en la arquitectura y el diseño orientado a objetos (OOP), asegurando que el código fuera modular, escalable y fácil de mantener. La elección de Spring Boot fue secundaria, utilizada por comodidad y experiencia previa, pero sin que su uso definiera el enfoque del sistema.

Desde el inicio, se priorizó una estructura clara y bien organizada, evitando duplicación de código y asegurandose que cada componente tuviera una única responsabilidad. Se implementaron servicios separados para cada tipo de ascensor, junto con un sistema centralizado de excepciones para mejorar las respuestas que recibe el usuario.

Además, se integraron pruebas unitarias e integración con Mockito y JUnit, junto con JaCoCo para garantizar una cobertura del código. 

En definitiva, más allá de qué framework se utilizó, el objetivo fue construir un sistema de ascensores bien diseñado y funcional, donde cada pieza del código estuviera bien definida y pudiera escalarse fácilmente en el futuro. 

---

👨‍💻 **Nicolas Segura**  
📧 [Seguraa.Nicolas@gmail.com](mailto:seguraa.nicolas@gmail.comm)

---