package br.cefet.p4.collection.bdr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {

	public Connection criar() throws SQLException, ClassNotFoundException {
		Class.forName( "com.mysql.jdbc.Driver" );
		String url = "jdbc:mysql://127.0.0.1/acme";
		return DriverManager.getConnection( url, "root", "" );
	}
	
}
