package modelos;

public class Ano {

	private int ano;
	private Mes[] meses;
	
	public Ano(int ano) {
		this.ano = ano;
		meses = new Mes[12];
		for(int i = 0; i < 12; i++) {
			meses[i] = new Mes();
		}
	}
	
	public int getNumero() {
		return ano;
	}

	public Mes getMes(int i) {
		return meses[i];
	}
}
