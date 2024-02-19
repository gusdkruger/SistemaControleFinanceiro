package daos;

import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import conexao.Conexao;
import modelos.MovimentoFinanceiro;

public class MovimentoFinanceiroDAO {

	public static int cria(String data, int banco, String nome, double valor, Boolean recorrente, Boolean variavel) {
		String query = "INSERT INTO MovimentoFinanceiro VALUES ('" + data + "', 0, " + banco + ", '" + nome + "', " + valor + ", " + recorrente + ", " + variavel + ")";
		return Conexao.executaRetornandoID(query);
	}
	
	public static boolean exclui(int id) {
		String query = "DELETE FROM MovimentoFinanceiro WHERE id = " + id;
		return Conexao.executa(query);
	}
	
	public static boolean edita(String data, int id, String nome, Double valor, boolean recorrente, boolean variavel) {
		String query = "UPDATE MovimentoFinanceiro SET data = '" + data + "', nome = '" + nome + "', valor = " + valor + ", recorrente = " + recorrente + ", variavel = " + variavel + " WHERE id = " + id;
		return Conexao.executa(query);
	}
	
	public static ArrayList<MovimentoFinanceiro> getMovimentos(int idBanco, int ano, int mes) {
		ArrayList<MovimentoFinanceiro> movimentos = new ArrayList<>();
		String query = "SELECT * FROM MovimentoFinanceiro WHERE banco = " + idBanco + " AND YEAR(data) = " + ano + " AND MONTH(data) = " + mes;
		ResultSet rs = Conexao.consulta(query);
		if(rs != null) {
			try {
				while(rs.next()) {
					MovimentoFinanceiro mf = new MovimentoFinanceiro(rs.getString("data"), rs.getInt("id"), rs.getString("nome"), rs.getDouble("valor"), rs.getBoolean("recorrente"), rs.getBoolean("variavel"));
					movimentos.add(mf);
				}
				return movimentos;
			}
			catch(Exception e) {
				JOptionPane.showMessageDialog(null, e.toString());
			}
		}
		return null;
	}
	
	public static ArrayList<Integer> getAnos(int idBanco) {
		ArrayList<Integer> anos = new ArrayList<>();
		String query = "SELECT DISTINCT EXTRACT(YEAR FROM data) FROM MovimentoFinanceiro WHERE banco = " + idBanco;
		ResultSet rs = Conexao.consulta(query);
		if(rs != null) {
			try {
				while(rs.next()) {
					anos.add(rs.getInt(1));
				}
			}
			catch(Exception e) {
				JOptionPane.showMessageDialog(null, e.toString());
			}
		}
		return anos;
	}
}
