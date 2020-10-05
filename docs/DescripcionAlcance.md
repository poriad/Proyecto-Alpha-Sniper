# SnowyDune

## ¿Qué es SnowyDune?

SnowyDune es una aplicación que permite a los usuarios acceder mediante, primero registro y posterior acceso,
a poder personalizar viajes que inicialmente serán especializados en deportes de invierno en zonas de Europa.
Estos viajes se podrán hacer lo más a la medida posible de lo que el usuario desea, resultando una forma sencilla
y centralizada en la que tener todo lo necesario ya sea alquiler de vehículos, alquiler de apartamentos, etc.

Estos usuarios podrán ser tanto personas que desean realizar un viaje, como empresas que deseen publicar sus servicios
en nuestra aplicación. Los servicios se le presentarán al usuario en función de la proximidad de estos a la zona en la que desee
realizar el viaje.

## Alcance del proyecto.

Este proyecto busca facilitar a los usuarios la forma de crear un viaje personalizado sin necesidad de visualizar de forma separada
cada servicio existente en la zona donde los usuarios quieren disfrutar de algún deporte de invierno, de forma que puedan formalizar
estos servicios desde nuestra aplicación.
A su vez se busca que, aunque inicialmente los servicios se deberán introducir por nosotros, posteriormente sean las propias empresas
las cuales se registren y oferten ellos mismos sus servicios a través de la aplicación.

Para todo esto se debe contar con:

- Un sistema de registro a través del cual poder acceder posteriormente al sistema. Este registro debe diferenciarte entre si es 
  un usuario estandar o un usuario empresa en cuanto a los campos iniciales a rellenar. Las empresas, inicialmente, deberán ser autorizadas por un usuario administrador.
- Un login que permita acceder al sistema, este login debe ser lo suficiente seguro como para evitar intrusiones indeseadas.
- Una pantalla inicial a través de la cual gestionar el Inicio de sesión y el registro en el sistema.
- Una pantalla específica para usuario, en la cual se pueda acceder a la personalización del viaje y pasar por el proceso de creación del mismo.
También va a poder acceder a modificar sus datos personales y a una pantalla en la que se visualizan los viajes creados y su historial de viajes realizados.
- Una pantalla específica para empresas, las cuales puedan publicar su negocio, con descripciones y fotos que el usuario desee.
- Una forma de que el usuario pueda realizar el pago del viaje que tenga en su carrito, después de haber creado el viaje.
- Una o varias pantallas en las que el usuario personalice su viaje, accediendo a lo que sea necesario, ya sea alquileres de vehículos, apartamentos, equipo, etc...
- Una forma de salir del sistema, mediante un Logout en todas las pantallas internas de la aplicación.
- El usuario puede realizar comentarios de los servicios que haya disfrutado, una vez las fechas del viaje han sido vencidas.
- El usuario empresa puede responder a dichos usuarios.
- Inicialmente, al ser el tráfico de usuarios escaso, los comentarios deberán ser revisados por un usuario administrador, pero posteriormente se prevee introducir un
sistema de reporte a través del cual regular el posible mal uso de los comentarios.
- Una pantalla específica para los usuarios administradores, a través de las cuales, poder gestionar las autorizaciones de registro y visualizar los comentarios realizados.
