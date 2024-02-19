package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Conexao {
	
	private static String url = "jdbc:mysql://localhost:3306/BancoControleFinanceiro";
	private static String user = "root";
	private static String senha = "";
			
	public static boolean executa(String query) {
		try {
			Connection con = DriverManager.getConnection(url, user, senha);
			Statement st = con.createStatement();
			st.execute(query);
			con.close();
			return true;
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.toString());
			return false;
		}
	}
	
	public static int executaRetornandoID(String query) {
	    try {
	        Connection con = DriverManager.getConnection(url, user, senha);
	        PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        pst.executeUpdate();
	        ResultSet keys = pst.getGeneratedKeys();
	        if(keys.next()) {
	            int id = keys.getInt(1);
	            con.close();
	            return id;
	        }
	        else {
	            con.close();
	            return -1;
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, e.toString());
	        return -1;
	    }
	}
	
	public static ResultSet consulta(String query) {
		try {
			Connection con = DriverManager.getConnection(url, user, senha);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			return rs;
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.toString());
			return null;
		}
	}
}
