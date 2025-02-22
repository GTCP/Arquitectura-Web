package edu.isistan.db;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class DAOFacturaProducto {

	public DAOFacturaProducto() {
		super();
	}

	/*
	 * Agrega los datos de las diferentes relaciones entre facturas y productos a la tabla facturaProducto en la bbdd, 
	 * trae los datos desde un archivo .csv cuya ruta DEBE SER SETEADA ANTES DE COMENZAR
	 */
	protected static void addFacturaProducto(Connection conn) throws SQLException, FileNotFoundException, IOException {
		String insert = "INSERT INTO facturaProducto (cantidad, idProducto, idFactura) VALUES(?,?,?)";
		PreparedStatement ps = conn.prepareStatement(insert);
		CSVParser parser;
		parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader("//facturas-productos.csv"));
		for (CSVRecord row : parser) {
			ps.setInt(1, Integer.parseInt(row.get("cantidad")));
			ps.setInt(2, Integer.parseInt(row.get("idProducto")));
			ps.setInt(3, Integer.parseInt(row.get("idFactura")));
			ps.executeUpdate();
		}
		ps.close();
		conn.commit();
	}

}
