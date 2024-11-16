# Proyecto: Aplicación Android en Kotlin

## Descripción

El proyecto es una **aplicación Android** desarrollada en **Kotlin**, diseñada siguiendo el patrón arquitectónico **MVVM** para garantizar un código **modular**, **limpio** y **fácil de mantener**. Para la gestión de dependencias, se utiliza **Dagger Hilt** como solución de inyección de dependencias, lo que facilita la integración y la gestión de los componentes a lo largo de la aplicación.

## Características principales

- **Patrón MVVM**: Utiliza el patrón arquitectónico **Model-View-ViewModel** para separar las preocupaciones y mejorar la escalabilidad y mantenimiento del código.
  
- **Inyección de dependencias**: Se utiliza **Dagger Hilt** para la inyección de dependencias en toda la aplicación, asegurando una gestión eficiente y desacoplada de los objetos y componentes.

- **Interfaz de usuario (UI)**: La interfaz está completamente implementada con los componentes **Jetpack** de Android, lo que permite la creación de **componentes modernos**, **dinámicos** y **altamente personalizables**.
  
- **Navegación**: La navegación entre pantallas se gestiona mediante **NavController**, proporcionando **transiciones fluidas** y una **estructura clara** para la navegación dentro de la app.

- **Manejo de imágenes**: Se ha implementado un sistema para manejar la carga de imágenes de manera eficiente, utilizando herramientas que optimizan el rendimiento y la experiencia del usuario.

- **Comunicación con la API**: La aplicación utiliza **Retrofit** junto con **Gson** para convertir y parsear los datos en formato **JSON** provenientes de la API, asegurando una comunicación eficiente con el backend.

- **Caché y base de datos**: Para mejorar el rendimiento y la experiencia del usuario, se ha implementado un sistema de **caché** para las respuestas de las solicitudes de red. Además, la app utiliza una **base de datos local** con **Room** (Google SQL), lo que permite almacenar los datos localmente y acceder a ellos incluso sin conexión a internet.

- **Gestión de favoritos**: La aplicación permite a los usuarios **agregar** o **eliminar juegos como favoritos**. Esta funcionalidad se implementa mediante una operación de **query de actualización** y **eliminación** en la base de datos local, proporcionando una forma eficiente de editar o eliminar juegos de la lista de favoritos.

## Tecnologías y herramientas utilizadas

- **Kotlin**: Lenguaje principal de desarrollo.
- **MVVM**: Arquitectura que separa la lógica de negocio de la UI.
- **Jetpack Compose**: Componente para la creación de interfaces de usuario modernas.
- **NavController**: Para la gestión de navegación dentro de la aplicación.
- **Retrofit**: Para la comunicación con la API.
- **Gson**: Para la conversión de datos JSON.
- **Room**: Base de datos local para el almacenamiento de datos.
- **Caché**: Implementación de almacenamiento en caché para optimizar la experiencia de usuario.
- **Dagger Hilt**: Para la inyección de dependencias, facilitando la integración y gestión de objetos en la aplicación.

## Android Studio
Android Studio Koala | 2024.1.1 Canary 8  
Kotlin 1.9.0

## Conclusión

Este proyecto ha sido diseñado para proporcionar una experiencia de usuario fluida y rápida, utilizando prácticas modernas de desarrollo Android. La implementación de **MVVM**, las herramientas de **Jetpack**, la integración de **Dagger Hilt** y la gestión de juegos favoritos aseguran que el código sea fácil de mantener y expandir a medida que la aplicación crece. La integración con una **base de datos local** y un sistema de **caché** mejora la accesibilidad a los datos, incluso sin conexión a internet.

## Autor
- [@Xget7](https://www.github.com/Xget7)
