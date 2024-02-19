package daos;

import java.sql.ResultSet;
import javax.swing.JOptionPane;
import conexao.Conexao;

public class UsuarioDAO {

	public static boolean cadastra(String login, String senha) {
		String query = "INSERT INTO Usuario VALUES ('" + login + "', '" + senha + "')";
		return Conexao.executa(query);
	}
	
	public static boolean edita(String login, String senha) {
		String query = "UPDATE Usuario SET senha = '" + senha + "' WHERE login = '" + login + "'";
		return Conexao.executa(query);
	}
	
	public static boolean exclui(String login) {
		String query = "SELECT id FROM Banco WHERE usuario = '" + login + "'";
		ResultSet rs = Conexao.consulta(query);
		if(rs != null) {
			try {
				while(rs.next()) {
					BancoDAO.exclui(rs.getInt(1));
				}
			}
			catch(Exception e) {
				JOptionPane.showMessageDialog(null, e.toString());
			}
		}
		query = "DELETE FROM Usuario WHERE login = '" + login + "'";
		return Conexao.executa(query);
	}
	
	public static boolean verificaLogin(String login, String senha) {
		String query = "SELECT login FROM Usuario WHERE login = '" + login + "' AND senha = '" + senha + "'";
		ResultSet rs = Conexao.consulta(query);
		if(rs != null) {
			try {
				if(rs.next()) {
					return true;
				}
			}
			catch(Exception e) {
				JOptionPane.showMessageDialog(null, e.toString());
			}
		}
		return false;	
	}
}
