# Collector Wheels

Collector Wheels es una aplicacion movil nativa para Android orientada a una tienda virtual de vehiculos Hot Wheels. El proyecto desarrolla principalmente la capa frontend de la aplicacion, con pantallas, navegacion, componentes visuales y datos de demostracion para simular el comportamiento de una tienda funcional.

La aplicacion esta construida en Android Studio usando Java, XML, `Activities`, `Fragments`, menus de navegacion, adaptadores, modelos y recursos reutilizables.

---

## Objetivo Del Proyecto

El objetivo principal del proyecto es implementar una interfaz movil organizada por roles de usuario. La aplicacion contempla tres perfiles:

- Comprador
- Vendedor
- Administrador

Cada rol tiene una experiencia diferente, con pantallas y opciones de navegacion adaptadas a sus necesidades dentro de la tienda.

---

## Estructura General Del Proyecto

```text
proyecto/
└── app/
    └── src/
        └── main/
            ├── java/com/collectorwheels/app/
            └── res/
```

La carpeta `java/com/collectorwheels/app/` contiene el codigo principal de la aplicacion. La carpeta `res/` contiene layouts XML, menus, colores, textos, iconos y otros recursos visuales.

---

## Paquetes Principales

### `auth/`

Contiene las pantallas relacionadas con autenticacion.

Archivos principales:

- `LoginFragment.java`
- `RegisterFragment.java`
- `ForgotPasswordFragment.java`

Funcionamiento:

Este modulo permite iniciar sesion, registrar una cuenta y simular la recuperacion de contrasena. En esta version el login trabaja en modo demostracion: valida que los campos no esten vacios y permite seleccionar un rol para entrar a la aplicacion.

---

### `buyer/`

Contiene las pantallas del rol comprador.

Archivos principales:

- `BuyerHomeFragment.java`
- `BuyerCatalogFragment.java`
- `BuyerProductDetailFragment.java`
- `BuyerCartFragment.java`
- `BuyerPaymentFragment.java`
- `BuyerProfileFragment.java`

Funcionamiento:

Este modulo representa la experiencia de compra. Permite mostrar una pagina de inicio, explorar productos, consultar el detalle de un articulo, agregar productos al carrito, revisar el resumen de compra y acceder a una pantalla de pago demostrativa.

Nota: en versiones posteriores se solucionara y mejorara la parte del comprador, especialmente el flujo completo de compra, la persistencia del carrito y la integracion con datos reales.

---

### `seller/`

Contiene las pantallas del rol vendedor.

Archivos principales:

- `SellerDashboardFragment.java`
- `SellerProductsFragment.java`
- `SellerProductEditorFragment.java`
- `SellerOrdersFragment.java`
- `SellerProfileFragment.java`

Funcionamiento:

Este modulo permite simular la administracion comercial de un vendedor. El usuario puede revisar un panel principal, visualizar productos, abrir el formulario para crear o editar productos, consultar pedidos recibidos y revisar su perfil.

---

### `admin/`

Contiene las pantallas del rol administrador.

Archivos principales:

- `AdminDashboardFragment.java`
- `AdminUsersFragment.java`
- `AdminUserEditorFragment.java`
- `AdminProductsFragment.java`
- `AdminSalesReportFragment.java`

Funcionamiento:

Este modulo centraliza las funciones administrativas. Permite visualizar indicadores generales, gestionar usuarios, revisar productos y consultar reportes de ventas simulados.

---

### `data/`

Contiene datos locales de demostracion.

Archivo principal:

- `DemoData.java`

Funcionamiento:

`DemoData.java` funciona como una fuente temporal de informacion. Define listas de productos, usuarios y pedidos para que las pantallas puedan mostrar contenido sin depender todavia de una base de datos externa o de un servicio web.

---

### `model/`

Contiene las clases que representan los datos principales de la aplicacion.

Archivos principales:

- `Product.java`
- `UserAccount.java`
- `OrderSummary.java`
- `CartLine.java`

Funcionamiento:

Estas clases modelan la informacion usada por la aplicacion. Por ejemplo, `Product` representa un producto de la tienda, `UserAccount` representa un usuario, `OrderSummary` representa un resumen de pedido y `CartLine` representa una linea del carrito.

---

### `ui/common/`

Contiene adaptadores reutilizables para listas y componentes visuales.

Archivos principales:

- `ProductGridAdapter.java`
- `ProductManageAdapter.java`
- `OrderRowAdapter.java`
- `UserRowAdapter.java`
- `CartAdapter.java`
- `BannerAdapter.java`
- `DetailImageAdapter.java`

Funcionamiento:

Los adaptadores conectan los datos con componentes visuales como listas y grillas. Su funcion es tomar objetos del modelo, como productos o usuarios, y mostrarlos en elementos XML reutilizables.

---

### `util/`

Contiene clases auxiliares.

Archivos principales:

- `MoneyFormat.java`
- `BiometricHelper.java`

Funcionamiento:

`MoneyFormat.java` ayuda a presentar valores monetarios con formato adecuado. `BiometricHelper.java` centraliza validaciones relacionadas con autenticacion biometrica, cuando el dispositivo la soporta.

---

## Archivos Principales De La Aplicacion

### `AuthActivity.java`

Es la actividad encargada del flujo de autenticacion. Al abrirse, carga inicialmente `LoginFragment` dentro del contenedor definido para autenticacion.

Funcionamiento:

Si el usuario aun no ha iniciado sesion, esta actividad muestra las pantallas de login, registro o recuperacion de contrasena.

---

### `MainActivity.java`

Es la actividad principal despues del inicio de sesion.

Funcionamiento:

Lee el rol guardado en la sesion y, segun ese rol, configura la navegacion de la aplicacion. Carga menus diferentes para comprador, vendedor o administrador, y reemplaza fragments dentro del contenedor principal.

Tambien controla:

- Menu lateral
- Menu inferior
- Barra superior
- Boton flotante del vendedor
- Navegacion hacia pantallas internas
- Cierre de sesion

---

### `NavigationHost.java`

Es una interfaz que define metodos de navegacion entre pantallas.

Funcionamiento:

Permite que los fragments soliciten acciones de navegacion sin depender directamente de la implementacion interna de `MainActivity`. Por ejemplo, abrir el detalle de un producto, ir al carrito, abrir la pasarela de pago o mostrar el editor de usuarios.

---

### `SessionManager.java`

Gestiona la sesion del usuario.

Funcionamiento:

Guarda informacion basica como rol, nombre, correo y estado de autenticacion. Esto permite que la aplicacion recuerde que tipo de usuario inicio sesion y muestre la interfaz correspondiente.

---

### `Role.java`

Define los roles disponibles en la aplicacion.

Funcionamiento:

Permite identificar si el usuario actual es comprador, vendedor o administrador. Esta informacion se usa para decidir que menus, pantallas y opciones se deben mostrar.

---

## Recursos XML

### `res/layout/`

Contiene los archivos XML que definen la estructura visual de cada pantalla.

Ejemplos:

- `activity_auth.xml`
- `activity_main.xml`
- `fragment_login.xml`
- `fragment_buyer_catalog.xml`
- `fragment_seller_products.xml`
- `fragment_admin_dashboard.xml`
- `item_product_card.xml`

Funcionamiento:

Cada layout define la ubicacion de botones, textos, listas, formularios y contenedores. Los fragments y activities usan estos archivos para construir la interfaz que ve el usuario.

---

### `res/menu/`

Contiene los menus de navegacion de la aplicacion.

Archivos principales:

- `menu_bottom_buyer.xml`
- `menu_bottom_seller.xml`
- `menu_bottom_admin.xml`
- `menu_drawer_buyer.xml`
- `menu_drawer_seller.xml`
- `menu_drawer_admin.xml`
- `menu_toolbar_main.xml`

Funcionamiento:

Los menus inferiores permiten acceder rapidamente a las secciones principales. Los menus laterales agrupan opciones adicionales segun el rol del usuario.

---

### `res/values/strings.xml`

Centraliza los textos reutilizables de la aplicacion.

Funcionamiento:

Aqui se almacenan titulos, botones, mensajes, nombres de menus y textos visibles en la interfaz. Esto facilita el mantenimiento y evita duplicar cadenas de texto en el codigo.

---

### `res/values/colors.xml`

Define la paleta de colores de la aplicacion.

Funcionamiento:

La identidad visual usa principalmente tonos oscuros y acentos naranjas, relacionados con el estilo automotriz y coleccionable de Collector Wheels.

---

### `res/values/themes.xml`

Define la configuracion visual general del tema.

Funcionamiento:

Controla estilos globales como colores base, apariencia de la aplicacion y compatibilidad con componentes visuales de Android.

---

### `res/drawable/`

Contiene iconos, fondos y recursos graficos XML.

Funcionamiento:

Estos recursos se usan en botones, menus, tarjetas, fondos y elementos visuales de la interfaz.

---

## Funcionamiento General

1. La aplicacion inicia mostrando una pantalla de autenticacion.
2. El usuario ingresa correo, contrasena y selecciona un rol de demostracion.
3. `SessionManager` guarda los datos de sesion.
4. Se abre `MainActivity`.
5. `MainActivity` identifica el rol del usuario.
6. Se cargan los menus y pantallas correspondientes.
7. La navegacion se realiza mediante fragments.
8. Los datos visibles provienen principalmente de `DemoData.java`.

---

## Flujo Por Rol

### Comprador

El comprador puede navegar por inicio, catalogo, detalle de producto, carrito, pago y perfil. Este flujo simula una experiencia de marketplace.

### Vendedor

El vendedor puede revisar su panel, administrar productos, crear o editar productos, consultar pedidos y revisar su perfil.

### Administrador

El administrador puede acceder a un panel general, gestionar usuarios, revisar productos y consultar reportes de ventas.

---

## Datos De Demostracion

La aplicacion no depende todavia de una base de datos remota. Para mostrar contenido se usa `DemoData.java`, donde se crean datos simulados como:

- Productos
- Usuarios
- Pedidos

Esto permite probar la interfaz y los flujos principales antes de integrar servicios reales.

---

## Estado Actual Del Proyecto

El proyecto implementa la base frontend de Collector Wheels. Actualmente incluye:

- Login con seleccion de rol
- Navegacion por comprador, vendedor y administrador
- Pantallas principales por rol
- Listas y tarjetas de productos
- Formularios de gestion
- Datos locales de demostracion
- Estilos, colores, menus e iconos personalizados

---

## Nota Sobre Futuras Versiones

En versiones posteriores se solucionara y completara la parte del comprador. Se espera mejorar el comportamiento del carrito, el flujo de compra, la persistencia de datos, la integracion con una base de datos real y la conexion con servicios externos para autenticacion, pagos y pedidos.

---

## Rutas Importantes

| Ruta | Descripcion |
| --- | --- |
| `proyecto/app/src/main/java/com/collectorwheels/app/AuthActivity.java` | Contenedor del flujo de autenticacion |
| `proyecto/app/src/main/java/com/collectorwheels/app/MainActivity.java` | Actividad principal y control de navegacion |
| `proyecto/app/src/main/java/com/collectorwheels/app/NavigationHost.java` | Interfaz para navegar entre pantallas internas |
| `proyecto/app/src/main/java/com/collectorwheels/app/SessionManager.java` | Administracion de sesion |
| `proyecto/app/src/main/java/com/collectorwheels/app/Role.java` | Definicion de roles |
| `proyecto/app/src/main/java/com/collectorwheels/app/data/DemoData.java` | Datos simulados |
| `proyecto/app/src/main/java/com/collectorwheels/app/auth/` | Autenticacion |
| `proyecto/app/src/main/java/com/collectorwheels/app/buyer/` | Pantallas del comprador |
| `proyecto/app/src/main/java/com/collectorwheels/app/seller/` | Pantallas del vendedor |
| `proyecto/app/src/main/java/com/collectorwheels/app/admin/` | Pantallas del administrador |
| `proyecto/app/src/main/java/com/collectorwheels/app/model/` | Modelos de datos |
| `proyecto/app/src/main/java/com/collectorwheels/app/ui/common/` | Adaptadores reutilizables |
| `proyecto/app/src/main/res/layout/` | Layouts XML |
| `proyecto/app/src/main/res/menu/` | Menus de navegacion |
| `proyecto/app/src/main/res/values/strings.xml` | Textos reutilizables |
| `proyecto/app/src/main/res/values/colors.xml` | Paleta de colores |
| `proyecto/app/src/main/res/drawable/` | Iconos y fondos |

---
## Vídeo explicativo de la tienda virtual - Collector Wheels
[Tienda Virtual - Collector Wheels](https://youtu.be/VkVBiFWpMCs)
