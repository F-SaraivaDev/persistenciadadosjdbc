package unidade3;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ClienteApp {
		
		static String url = "jdbc:postgresql://localhost:5432/cursojava";
		static String usuario = "postgres";
		static String senha = "admin";
		static Connection conexao;
		
		public static void conectar() throws SQLException {
			
			conexao = DriverManager.getConnection(url, usuario, senha);
			conexao.setAutoCommit(false);
		}
		
		public static void desconectar() throws SQLException {
			
			conexao.close();
		}
		
		public static void inserir_old(long cpf, String nome, String email) throws SQLException {
		
			PreparedStatement statement = conexao.prepareStatement("insert into Cliente values ("+cpf+",'"+nome+"','"+email+"')");
			statement.executeUpdate();
			conexao.commit();
		}
		
		public static void inserir(long cpf, String nome, String email) throws SQLException{
			
			String sql = "{call sp_inserircliente(?,?,?)}";
			CallableStatement cstmt = conexao.prepareCall(sql);
			cstmt.setLong(1, cpf);
			cstmt.setString(2, nome);
			cstmt.setString(3, email);
			cstmt.execute();
			conexao.commit();
		}
		
		public static void consultar(long cpf) throws SQLException {
			
			Statement statement = conexao.createStatement();
		    ResultSet resultSet	= statement.executeQuery("select * from cliente where cpf="+cpf+"");
			
			while(resultSet.next()) {
				System.out.println("CPF: " + resultSet.getLong(1) + " - Nome: " 
			                               + resultSet.getString(2) + " - Email: "
						                   + resultSet.getString(3));
			}
			
		}
		
		public static void consultarTodos() throws SQLException{
			
			Statement statement = conexao.createStatement();
		    ResultSet resultSet	= statement.executeQuery("select * from cliente");
		    int cont = 0;
		    			
			while(resultSet.next()) {
				System.out.println("CPF: " + resultSet.getLong(1) + " - Nome: " 
			                               + resultSet.getString(2) + " - Email: "
						                   + resultSet.getString(3));
			System.out.println("===============================================================");
			cont++;
			}	
			
			System.out.println("Quantidade de clientes listados: " + cont);
			
		}
		
		public static void alterar(long cpf, String nome, String email) throws SQLException{
			
			Statement statement = conexao.createStatement();
			statement.executeUpdate("update Cliente set nome='"+nome+"', email='"+email+"' where cpf="+cpf);
			conexao.commit();
		}
		
		public static void excluir(long cpf) throws SQLException{
			
			Statement statement = conexao.createStatement();
			statement.executeUpdate("delete from cliente where cpf="+cpf);
			conexao.commit();
		}
		
		
		public static void main(String[] args) {

			try {
				
				int opcao = 0; 	long cpf;
				String nome, email;
				Scanner entrada = new Scanner(System.in);
				
				conectar();
				
				while(opcao != 6){
					
					System.out.println("\nSistema de Gerenciamento de Clientes");
					System.out.println("====================================");
					System.out.println("Digite [1] para Consultar Todos os Clientes");
					System.out.println("Digite [2] para Consultar um Cliente Específico");
					System.out.println("Digite [3] para Cadastrar um Novo Cliente");
					System.out.println("Digite [4] para Alterar um Cliente");
					System.out.println("Digite [5] para Excluir um Cliente");
					System.out.println("Digite [6] para Sair");
					System.out.println("====================================");
					opcao = entrada.nextInt();
					
					switch(opcao)
					{
						case 1: //Consultar Todos
						{
							System.out.println("[1] Consultar Todos");
							consultarTodos();
							break;
						}
						case 2: //Consultar
						{
							System.out.println("[2] Consultar um Cliente Específico");
							System.out.println("Favor informar o CPF: ");
							consultar(entrada.nextLong());
							break;						
						}
						case 3: //Cadastrar 
						{
							System.out.println("[3] Cadastrar um Novo Cliente");
							System.out.println("Favor informar o CPF: ");
							cpf = entrada.nextLong();
							entrada.nextLine(); //esvaziar o buffer do teclado
							System.out.println("Favor informar o Nome: ");
							nome = entrada.nextLine();
							System.out.println("Favor informar o Email: ");
							email = entrada.nextLine();	
							inserir(cpf, nome, email);
							break;					
						}
						case 4: //Alterar
						{
							System.out.println("[4] Alterar um Cliente");
							System.out.println("Favor informar o CPF: ");
							cpf = entrada.nextLong();
							entrada.nextLine(); //esvaziar o buffer do teclado
							System.out.println("Favor informar o Nome: ");
							nome = entrada.nextLine();
							System.out.println("Favor informar o Email: ");
							email = entrada.nextLine();
							alterar(cpf, nome, email);
							break;
						}
						case 5: //Excluir
						{
							System.out.println("[5] Excluir um Cliente");
							System.out.println("Favor informar o CPF: ");
							cpf = entrada.nextLong();
							excluir(cpf);
							break;						
						}
						case 6: //Sair
						{
							System.out.println("Encerrando o Sistema...");
							break;
						}
					}	
				}
				entrada.close();
				desconectar();
				
			}catch(Exception e) {
				
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			
		}
	}		





























