package unidade3;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class AcessoBD {

	static String url = "jdbc:postgresql://localhost:5432/cursojava";
	static String usuario = "postgres";
	static String senha = "admin";
	static Connection conexao;
	
	public static void conectar() throws SQLException {
		
		conexao = DriverManager.getConnection(url, usuario, senha);
		conexao.setAutoCommit(false);
	}
	
	public static void consultarCliente() throws SQLException {
		
		String consulta = "SELECT * FROM Cliente";
		Statement statement = conexao.createStatement();
		ResultSet rs = statement.executeQuery(consulta);
		
		while(rs.next()) {
			JOptionPane.showMessageDialog(null, "CPF: " + rs.getLong(1) + " - Nome: " + rs.getString(2) + " - Email: " + rs.getString(3));
		}
		
	}
	
	public static void mostraMetaInfoBD() throws SQLException {
		
		DatabaseMetaData meta = conexao.getMetaData();
		String fabricanteBD = meta.getDatabaseProductName();
		String versaoBD = meta.getDatabaseProductVersion();
		JOptionPane.showMessageDialog(null, fabricanteBD + "<==>" + versaoBD);
	}
	
	public static void main(String[] args) {
		
		try {
			conectar();
			mostraMetaInfoBD();
			consultarCliente();
			conexao.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}





















