package edu.isistan.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOFactory {
	private String driver;
	private String uri;
	private DAOFactura factura;
	private DAOCliente cliente;
	private DAOProducto producto;
	private DAOFacturaProducto facturaProducto;

	public DAOFactory(String driver) {
		if (driver.equals("mysql")) {
			this.driver = "com.mysql.cj.jdbc.Driver";
			uri = "jdbc:mysql://localhost:3306/entregable1bbdd";
		} else {
			this.driver = "org.apache.derby.jdbc.EmbeddedDriver";
			uri = "jdbc:derby:MyDerbyDb;create=true";
		}
		this.factura = new DAOFactura();
		this.cliente = new DAOCliente();
		this.producto = new DAOProducto();
		this.facturaProducto = new DAOFacturaProducto();
	}

	/*
	 * Metodo que comienza la creacion de las tablas de la base de datos y luego les inserta los datos
	 */
	protected void creation() throws FileNotFoundException, IOException {
		try {
			Class.forName(driver).getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		try {
			Connection conn = DriverManager.getConnection(uri, "root", "");
			conn.setAutoCommit(false);
			createTables(conn);
			insertData(conn);
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Crea las tablas de la bbdd, llamado por creation();
	 */
	private void createTables(Connection conn) throws SQLException {
		
		String table1 = "CREATE TABLE cliente(" + "idCliente  INT," + "nombre VARCHAR(500)," + "email VARCHAR(150),"
				+ "PRIMARY KEY(idCliente))";
		conn.prepareStatement(table1).execute();
		conn.commit();

		String table2 = "CREATE TABLE producto(" + "idProducto INT NOT NULL," + "nombre VARCHAR(45) NOT NULL,"
				+ "valor double NOT NULL," + "PRIMARY KEY(idProducto))";
		conn.prepareStatement(table2).execute();
		conn.commit();

		String table3 = "CREATE TABLE factura(" + "idFactura  INT," + "idCliente INT NOT NULL,"
				+ "PRIMARY KEY(idFactura),"
				+ "CONSTRAINT FK_ID_FACTURACLIENTE FOREIGN KEY (idCliente) REFERENCES cliente(idCliente))";
		conn.prepareStatement(table3).execute();
		conn.commit();
		String table4 = "CREATE TABLE facturaProducto(" + "idFactura INT NOT NULL," + "idProducto INT NOT NULL,"
				+ "cantidad INT NOT NULL,"
				+ "CONSTRAINT FK_ID_FACTURAPRODUCTO FOREIGN KEY (idFactura) REFERENCES Factura(idFactura),"
				+ "CONSTRAINT FK_ID_FACTURAPRODUCTOP FOREIGN KEY (idProducto) REFERENCES Producto(idProducto))";
		conn.prepareStatement(table4).execute();
		conn.commit();
	}

	/*
	 * Llama a los metodos que insertan los datos en cada tabla segun su clase
	 */
	private void insertData(Connection conn) throws FileNotFoundException, SQLException, IOException {
		cliente.addCliente(conn);
		factura.addFactura(conn);
		producto.addProducto(conn);
		facturaProducto.addFacturaProducto(conn);
	}

	/*
	 * Llama al metodo que trae la lista de los clientes mas facturados
	 */
	protected ArrayList<Cliente> getBestCustomers() {
		return cliente.getBestCustomers(driver, uri);
	}

	/*
	 * Llama al metodo que trae el producto que mas recauda
	 */
	protected Producto getBestProduct() {
		return producto.getBestProduct(driver, uri);
	}
}
