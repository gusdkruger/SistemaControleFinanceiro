package modelos;

import java.util.ArrayList;

public class Mes {

	private ArrayList<MovimentoFinanceiro> movimentos;

	public Mes() {
		movimentos = new ArrayList<>();
	}
	
	public ArrayList<MovimentoFinanceiro> getMovimentos() {
		return movimentos;
	}
	
	public MovimentoFinanceiro getMovimento(int id) {
		for(MovimentoFinanceiro mf : movimentos) {
			if(mf.getId() == id) {
				return mf;
			}
		}
		return null;
	}

	public void setMovimentos(ArrayList<MovimentoFinanceiro> movimentos) {
		this.movimentos = movimentos;
	}
	
	public void addMovimento(MovimentoFinanceiro movimento) {
		movimentos.add(movimento);
	}
}
