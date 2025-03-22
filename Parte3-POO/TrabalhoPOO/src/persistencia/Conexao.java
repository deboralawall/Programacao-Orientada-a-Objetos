package persistencia;

import java.sql.*;

public class Conexao {
	
    private static Connection conexao = null;
    private static String senha;
    
    public static void setSenha(String s) {
    	senha = s;
    }
    
    public static Connection getConexao() throws ClassNotFoundException, SQLException{
    	if(conexao == null) {
    		String driver = "org.postgresql.Driver";
    	    String url = "jdbc:postgresql://localhost:5432/TRAB_POO";
    	    String usuario = "postgres";
    	    conexao = DriverManager.getConnection(url, usuario, senha);
    	}
    	return conexao;
    }
    
}
