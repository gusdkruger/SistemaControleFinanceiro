package modelos;

public class MovimentoFinanceiro {

	private String data;
	private int id;
	private String nome;
	private double valor;
	private boolean recorrente;
	private boolean variavel;

	public MovimentoFinanceiro(String data, int id, String nome, double valor, boolean recorrente, boolean variavel) {
		this.data = data;
		this.id = id;
		this.nome = nome;
		this.valor = valor;
		this.recorrente = recorrente;
		this.variavel = variavel;
	}

	public String getData() {
		return data;
	}
	
	public int getAno() {
		return Integer.parseInt(data.substring(0, 4));
	}
	
	public int getMes() {
		return Integer.parseInt(data.substring(5, 7));
	}
	
	public int getDia() {
		return Integer.parseInt(data.substring(8));
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public double getValor() {
		return valor;
	}

	public boolean isRecorrente() {
		return recorrente;
	}

	public boolean isVariavel() {
		return variavel;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public void setRecorrente(boolean recorrente) {
		this.recorrente = recorrente;
	}

	public void setVariavel(boolean variavel) {
		this.variavel = variavel;
	}
	
	
}
