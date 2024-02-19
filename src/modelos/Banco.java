package modelos;

import java.util.ArrayList;

public class Banco {

	private int id;
	private String nome;
	private ArrayList<Ano> anos;

	public Banco(int id, String nome) {
		this.id = id;
		this.nome = nome;
		anos = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	public Ano getAno(int ano) {
		for(Ano a : anos) {
			if(a.getNumero() == ano) {
				return a;
			}
		}
		return null;
	}
	
	public ArrayList<Ano> getAnos() {
		return anos;
	}

	public void adicionaAno(int ano) {
		anos.add(new Ano(ano));
	}
}
