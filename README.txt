- Antes de comenzar y darle run al main se deben hacer ciertas cosas:

1)  Setear la ruta de donde estan los archivos csv, estos son los que tienen los datos que van a ser cargados en la bbdd. 
Para esto se debe entrar a DAOCliente, DAOFactura, DAOProducto y DAOFacturaProducto y cambiar la ruta de los archivos a la ruta que este en su pc.

2) Crear una bbdd vacia llamada "entregable1bbdd" (para este proyecto se utilizo en phpmyadmin, en caso de no utilizar phpmyadmin 
probablemente debera de cambiar en cada coneccion a la bbdd (1 por DAO) el usuario "root" por el suyo y la contraseña "" por la suya).