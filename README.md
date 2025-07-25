# 🇵🇪 Clientes API - Sistema de Consulta DNI

Sistema web completo para consulta de información de DNI utilizando la API oficial de RENIEC a través de API Peru Dev.

## 🚀 Características

- ✅ **Consulta de DNI**: Obtén información completa de personas usando su DNI
- ✅ **Gestión de Usuarios**: Sistema de registro y autenticación
- ✅ **Planes de Suscripción**: Gratuito, Básico, Premium e Ilimitado
- ✅ **Dashboard Interactivo**: Interfaz web moderna y responsive
- ✅ **CRUD de Clientes**: Gestiona la información de tus consultas
- ✅ **Exportación a Excel**: Descarga tus datos en formato Excel
- ✅ **Historial Completo**: Mantén un registro de todas tus consultas
- ✅ **API REST**: Endpoints para integración con otros sistemas

## 🛠️ Tecnologías

- **Backend**: Spring Boot 3.x, Spring Security, JPA/Hibernate
- **Frontend**: HTML5, CSS3, JavaScript (Vanilla)
- **Base de Datos**: MySQL
- **Autenticación**: BCrypt para encriptación de contraseñas
- **API Externa**: API Peru Dev para consultas RENIEC

## 📋 Requisitos

- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+
- Token de API Peru Dev

## ⚙️ Instalación

### 1. Clonar el repositorio
```bash
git clone <tu-repositorio>
cd clientes-api
```

### 2. Configurar Base de Datos
```sql
CREATE DATABASE clientes_app;
```

### 3. Configurar application.properties
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/clientes_app
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

# API Peru Dev Configuration
apiperu.token=tu_token_api_peru_dev
```

### 4. Compilar y ejecutar
```bash
mvn clean compile
mvn spring-boot:run
```

## 🌐 Acceso a la Aplicación

- **Página Principal**: http://localhost:8080/
- **Registro**: http://localhost:8080/registro
- **Login**: http://localhost:8080/login
- **Dashboard**: http://localhost:8080/dashboard

## 👤 Cuentas de Prueba

### Usuario Gratuito (5 consultas)
- **Email**: usuario@test.com
- **Contraseña**: 123456

### Usuario Premium (500 consultas) - Para Pruebas
- **Email**: premium@test.com
- **Contraseña**: 123456

## 🔧 Configuración de Cuenta Premium

Para configurar una cuenta con plan Premium para pruebas:

### Opción 1: Usando cURL
```bash
# 1. Registrar usuario
curl -X POST http://localhost:8080/api/auth/registro \
  -H "Content-Type: application/json" \
  -d '{
    "nombres": "Usuario Premium",
    "apellidos": "Prueba",
    "email": "premium@test.com",
    "password": "123456"
  }'

# 2. Actualizar a plan Premium (usar el ID devuelto en el paso anterior)
curl -X PUT http://localhost:8080/api/auth/actualizar-plan/1 \
  -H "Content-Type: application/json" \
  -d '{"plan": "PREMIUM"}'
```

### Opción 2: Desde la Base de Datos
```sql
-- Actualizar plan directamente en la base de datos
UPDATE usuarios 
SET plan = 'PREMIUM', busquedas_restantes = 500 
WHERE email = 'premium@test.com';
```

## 📊 Planes Disponibles

| Plan | Consultas | Precio | Características |
|------|-----------|--------|-----------------|
| **Gratuito** | 5/mes | S/ 0 | Acceso básico, Exportar Excel |
| **Básico** | 100/mes | S/ 29 | Historial completo, Soporte email |
| **Premium** | 500/mes | S/ 99 | API personalizada, Soporte prioritario |
| **Ilimitado** | ∞ | S/ 199 | Sin límites, Soporte 24/7 |

## 🔌 API Endpoints

### Autenticación
- `POST /api/auth/registro` - Registrar usuario
- `POST /api/auth/login` - Iniciar sesión
- `PUT /api/auth/actualizar-plan/{id}` - Actualizar plan de usuario

### Consultas DNI
- `POST /api/clientes/consultar-dni` - Consultar DNI
- `GET /api/clientes/usuario/{id}` - Obtener clientes por usuario
- `PUT /api/clientes/{id}` - Actualizar cliente
- `DELETE /api/clientes/{id}` - Eliminar cliente
- `GET /api/clientes/exportar-excel/{usuarioId}` - Exportar a Excel

## 📱 Uso de la Aplicación

### 1. Registro de Usuario
1. Accede a http://localhost:8080/registro
2. Completa el formulario con tus datos
3. Haz clic en "Registrarse"

### 2. Consulta de DNI
1. Inicia sesión en http://localhost:8080/login
2. Ve al Dashboard
3. En la pestaña "Buscar DNI", ingresa un DNI de 8 dígitos
4. Haz clic en "Buscar"

### 3. Gestión de Clientes
1. En el Dashboard, ve a la pestaña "Mis Clientes"
2. Visualiza todas tus consultas realizadas
3. Edita o elimina clientes según necesites
4. Exporta tus datos a Excel

## 🔒 Seguridad

- Contraseñas encriptadas con BCrypt
- Configuración CORS para desarrollo
- Validación de datos en frontend y backend
- Manejo seguro de tokens de API

## 🐛 Solución de Problemas

### Error de conexión a base de datos
```bash
# Verificar que MySQL esté ejecutándose
mysql -u root -p

# Crear la base de datos si no existe
CREATE DATABASE clientes_app;
```

### Error de compilación
```bash
# Limpiar y recompilar
mvn clean compile
```

### Error 403 en dashboard
- Verificar que el usuario esté autenticado
- Revisar configuración de Spring Security

## 📞 Soporte

Para soporte técnico o consultas:
- Email: soporte@clientesapi.com
- Documentación: [API Peru Dev](https://apiperu.dev)

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

---

**Desarrollado con ❤️ para facilitar las consultas de DNI en Perú**