package telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import daos.MovimentoFinanceiroDAO;
import filtros.LimitaLength;
import filtros.LimitaSomenteNumeros;
import modelos.Banco;
import modelos.MovimentoFinanceiro;

@SuppressWarnings("serial")
public class PanelEditarMovimento extends JPanel implements ActionListener {

	int ano;
	int mes;
	int id;
	PanelBanco panelBanco;
	Banco banco;
	
	JLabel labelData;
	JComboBox<String> comboBoxDia;
	JTextField textFieldNome;
	JTextField textFieldValor;
	JCheckBox checkBoxRecorrente;
	JCheckBox checkBoxVariavel;
	JButton buttonConfirmar;
	
	Font fonteGrande = new Font("Arial", Font.PLAIN, 25);
	Font fonteMedia = new Font("Arial", Font.PLAIN, 20);
	Font fontePequena = new Font("Arial", Font.PLAIN, 15);
	
	public PanelEditarMovimento(PanelBanco panelBanco, Banco banco) {
		this.ano = 2024;
		this.mes = 1;
		this.id = 0;
		this.panelBanco = panelBanco;
		this.banco = banco;
		setSize(366, 370);
		setLayout(null);
		
		labelData = new JLabel(dataToString());
		labelData.setFont(fonteGrande);
		labelData.setBounds(98, 19, 100, 50);
		add(labelData);
		
		comboBoxDia = new JComboBox<>();
		comboBoxDia.setFont(fonteGrande);
		comboBoxDia.setBounds(199, 27, 53, 35);
		preencheComboBox();
		add(comboBoxDia);
		
		JLabel labelNome = new JLabel("Nome");
		labelNome.setFont(fontePequena);
		labelNome.setBounds(50, 54, 100, 50);
		add(labelNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setFont(fonteGrande);
		textFieldNome.setBounds(50, 89, 250, 50);
		textFieldNome.setDocument(new LimitaLength(20));
		add(textFieldNome);
		
		JLabel labelValor = new JLabel("Valor");
		labelValor.setFont(fontePequena);
		labelValor.setBounds(50, 141, 100, 50);
		add(labelValor);
		
		textFieldValor = new JTextField();
		textFieldValor.setFont(fonteGrande);
		textFieldValor.setBounds(50, 176, 250, 50);
		textFieldValor.setDocument(new LimitaSomenteNumeros());
		add(textFieldValor);
				
		checkBoxRecorrente = new JCheckBox("Recorrente");
		checkBoxRecorrente.setFont(fonteMedia);
		checkBoxRecorrente.setBounds(50, 234, 125, 50);
		checkBoxRecorrente.addActionListener(this);
		add(checkBoxRecorrente);
				
		checkBoxVariavel = new JCheckBox("Variavel");
		checkBoxVariavel.setFont(fonteMedia);
		checkBoxVariavel.setBounds(205, 234, 95, 50);
		checkBoxVariavel.addActionListener(this);
		add(checkBoxVariavel);
		
		buttonConfirmar = new JButton("Salvar");
		buttonConfirmar.setFont(fonteGrande);
		buttonConfirmar.setBounds(50, 295, 250, 50);
		buttonConfirmar.addActionListener(this);
		add(buttonConfirmar);
	}

	public void atualizaInfo(int ano, int mes, int dia, int id, String nome, String valor, boolean recorrente, boolean variavel) {
		this.ano = ano;
		this.mes = mes;
		this.id = id;
		labelData.setText(dataToString());
		preencheComboBox();
		comboBoxDia.setSelectedIndex(dia - 1);
		textFieldNome.setText(nome);
		textFieldValor.setText(valor + "");
		checkBoxRecorrente.setSelected(recorrente);
		checkBoxVariavel.setSelected(variavel);
	}
	
	public void resetaDados() {
		comboBoxDia.setSelectedIndex(0);
		textFieldNome.setText("");
		textFieldValor.setText("");
		checkBoxRecorrente.setSelected(false);
		checkBoxVariavel.setSelected(false);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == buttonConfirmar) {
			String nome = textFieldNome.getText();
			String valorString = textFieldValor.getText().replaceAll(",", ".");
			if(!nome.isBlank() && validaValor(valorString)) {
				String data = dataToString() + comboBoxDia.getSelectedItem();
				Double valor = Double.parseDouble(valorString);
				MovimentoFinanceiroDAO.edita(data.replaceAll("/", "-"), id, nome, valor, checkBoxRecorrente.isSelected(), checkBoxVariavel.isSelected());
				MovimentoFinanceiro mf = banco.getAno(ano).getMes(mes - 1).getMovimento(id);
				mf.setData(data.replaceAll("/", "-"));
				mf.setNome(nome);
				mf.setValor(valor);
				mf.setRecorrente(checkBoxRecorrente.isSelected());
				mf.setVariavel(checkBoxVariavel.isSelected());
				panelBanco.preencheTabela();
				panelBanco.mostraAdicionar();
			}
		}
		else if(e.getSource() == checkBoxRecorrente && !checkBoxRecorrente.isSelected()) {
			checkBoxVariavel.setSelected(false);
		}
		else if(e.getSource() == checkBoxVariavel && !checkBoxRecorrente.isSelected()) {
			checkBoxVariavel.setSelected(false);
		}
	}
	
	private String dataToString() {
		String data = ano + "/";
		if(mes < 10) {
			data += "0";
		}
		return data += mes + "/";
	}
	
	private void preencheComboBox() {
		comboBoxDia.removeAllItems();
		int qtdDias;
		if(mes == 2) {
			if((ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0)) {
				qtdDias = 29;
			}
			else {
				qtdDias = 28;
			}
		}
		else if(mes == 4 || mes == 6 || mes == 9 || mes == 11) {
			qtdDias = 30;
		}
		else {
			qtdDias = 31;
		}
		for(int i = 1; i <= qtdDias; i++) {
			if(i < 10) {
				comboBoxDia.addItem("0" + i);
			}
			else {
				comboBoxDia.addItem("" + i);
			}
		}
	}
	
	private boolean validaValor(String valor) {
		try {
			Double d = Double.parseDouble(valor);
			if(d >= 10000000) {
				JOptionPane.showMessageDialog(null, "Valor maximo: 9999999.99");
				return false;
			}
			else if(BigDecimal.valueOf(d).scale() > 2) {
				JOptionPane.showMessageDialog(null, "Maximo de 2 numeros depois da virgula ou ponto");
				return false;
			}
			return true;
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Valor so pode conter um , ou .");
			return false;
		}
	}
}
