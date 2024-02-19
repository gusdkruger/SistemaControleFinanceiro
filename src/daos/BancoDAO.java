package daos;

import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import conexao.Conexao;
import modelos.Ano;
import modelos.Banco;

public class BancoDAO {

	public static int cria(String usuario, String nome) {
		String query = "INSERT INTO Banco VALUES (0, '" + usuario + "', '" + nome + "')";
		return Conexao.executaRetornandoID(query);
	}
	
	public static boolean exclui(int id) {
		String query = "DELETE FROM MovimentoFinanceiro WHERE banco = " + id;
		Conexao.executa(query);
		query = "DELETE FROM Banco WHERE id = " + id;
		return Conexao.executa(query);
	}
	
	public static ArrayList<Banco> getBancos(String usuario) {
		ArrayList<Banco> bancos = new ArrayList<>();
		String query = "SELECT * FROM Banco WHERE usuario = '" + usuario + "'";
		ResultSet rs = Conexao.consulta(query);
		if(rs != null) {
			try {
				while(rs.next()) {
					Banco b = new Banco(rs.getInt("id"), rs.getString("nome"));
					preencheBanco(b);
					bancos.add(b);
				}
			}
			catch(Exception e) {
				JOptionPane.showMessageDialog(null, e.toString());
			}
		}
		return bancos;
	}
	
	private static void preencheBanco(Banco banco) {
		ArrayList<Integer> anos = MovimentoFinanceiroDAO.getAnos(banco.getId());
		for(Integer i : anos) {
			banco.adicionaAno(i);
		}
		for(Ano ano : banco.getAnos()) {
			for(int i = 0; i < 12; i++) {
				ano.getMes(i).setMovimentos(MovimentoFinanceiroDAO.getMovimentos(banco.getId(), ano.getNumero(), i+1));
			}
		}
	}
}
