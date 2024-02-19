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
import modelos.Ano;
import modelos.Banco;
import modelos.MovimentoFinanceiro;

@SuppressWarnings("serial")
public class PanelAdicionarMovimento extends JPanel implements ActionListener {

	private int ano;
	private int mes;
	private PanelBanco panelBanco;
	private Banco banco;
	
	private JLabel labelData;
	private JComboBox<String> comboBoxDia;
	private JTextField textFieldNome;
	private JTextField textFieldValor;
	private JCheckBox checkBoxRecorrente;
	private JCheckBox checkBoxVariavel;
	private JButton buttonConfirmar;
	
	private final Font fonteGrande = new Font("Arial", Font.PLAIN, 25);
	private final Font fonteMedia = new Font("Arial", Font.PLAIN, 20);
	private final Font fontePequena = new Font("Arial", Font.PLAIN, 15);
	
	public PanelAdicionarMovimento(PanelBanco panelBanco, Banco banco) {
		this.ano = 2024;
		this.mes = 1;
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
		
		buttonConfirmar = new JButton("Adicionar");
		buttonConfirmar.setFont(fonteGrande);
		buttonConfirmar.setBounds(50, 295, 250, 50);
		buttonConfirmar.addActionListener(this);
		add(buttonConfirmar);
	}
	
	public void atualizaData(int ano, int mes) {
		this.ano = ano;
		this.mes = mes;
		labelData.setText(dataToString());
		preencheComboBox();
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
			if(!nome.isBlank() && !valorString.isBlank() && validaValor(valorString)) {
				String data = dataToString() + comboBoxDia.getSelectedItem();
				Double valor = Double.parseDouble(valorString);
				int id = MovimentoFinanceiroDAO.cria(data, banco.getId(), nome, valor, checkBoxRecorrente.isSelected(), checkBoxVariavel.isSelected());
				MovimentoFinanceiro mf = new MovimentoFinanceiro(data.replaceAll("/", "-"), id, nome, valor, checkBoxRecorrente.isSelected(), checkBoxVariavel.isSelected());
				if(banco.getAno(ano) == null) {
					banco.getAnos().add(new Ano(ano));
				}
				banco.getAno(ano).getMes(mes - 1).addMovimento(mf);
				panelBanco.preencheTabela();
				resetaDados();
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
