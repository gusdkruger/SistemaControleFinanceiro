package telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import daos.BancoDAO;
import daos.MovimentoFinanceiroDAO;
import modelos.Banco;
import modelos.MovimentoFinanceiro;

@SuppressWarnings("serial")
public class PanelBanco extends JPanel implements ActionListener {

	private int ano;
	private int mes;
	private Banco banco;
	private final String[] nomeMeses = {"Janeiro", "Fevereiro", "Marco", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
	private final String[] titulos = {"Data", "Nome", "Valor", "Recorrente", "Variavel"};
	private int[] idMovimentos;
	private JTabbedPane tabbedPaneBanco;
	private ArrayList<Banco> bancos;
	private JButton buttonEnabled;

	private JButton buttonEsquerda;
	private JLabel labelAno;
	private JButton buttonDireita;
	private JButton[] buttonMeses;
	private DefaultTableModel conteudoTabela;
	private JTable tabela;
	private JButton buttonRecorrencias;
	private JButton buttonAdicionarMovimento;
	private JButton buttonEditarMovimento;
	private JButton buttonExcluirMovimento;
	private JLabel labelEntrada;
	private JLabel labelSaida;
	private PanelAdicionarMovimento panelAdicionarMovimento;
	private PanelEditarMovimento panelEditarMovimento;
	private JButton buttonExcluirBanco;
	
	private final Font fonteGrande = new Font("Arial", Font.PLAIN, 20);
	private final Font fonteGrande2 = new Font("Arial", Font.PLAIN, 25);
	
	public PanelBanco(Banco banco, JTabbedPane tabbedPaneBanco, ArrayList<Banco> bancos) {
		this.ano = 2024;
		this.mes = 0;
		this.banco = banco;
		this.tabbedPaneBanco = tabbedPaneBanco;
		this.bancos = bancos;
		setSize(1185, 590);
		setLayout(null);
		
		buttonEsquerda = new JButton("<");
		buttonEsquerda.setFont(fonteGrande);
		buttonEsquerda.setBounds(5, 5, 50, 30);
		buttonEsquerda.addActionListener(this);
		buttonEsquerda.setEnabled(false);
		add(buttonEsquerda);
		
		labelAno = new JLabel(ano + "");
		labelAno.setFont(fonteGrande);
		labelAno.setBounds(60, 5, 50, 30);
		add(labelAno);
		
		buttonDireita = new JButton(">");
		buttonDireita.setFont(fonteGrande);
		buttonDireita.setBounds(110, 5, 50, 30);
		buttonDireita.addActionListener(this);
		add(buttonDireita);
		
		buttonMeses = new JButton[12];
		int y = 50;
		for(int i = 0; i < 12; i++) {
			buttonMeses[i] = new JButton(nomeMeses[i]);
			buttonMeses[i].setFont(fonteGrande);
			buttonMeses[i].setBounds(5, y, 155, 35);
			buttonMeses[i].addActionListener(this);
			add(buttonMeses[i]);
			y += 40;
		}
		buttonEnabled = buttonMeses[0];
		buttonEnabled.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(170, 5, 520, 520);
		conteudoTabela = new DefaultTableModel(null, titulos);
		tabela = new JTable(conteudoTabela) {
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		tabela.setDefaultRenderer(Object.class, centerRenderer);
		tabela.getTableHeader().setReorderingAllowed(false);
		tabela.getTableHeader().setResizingAllowed(false);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tabela);
		add(scrollPane);
		
		buttonRecorrencias = new JButton("Recorrencias");
		buttonRecorrencias.setFont(fonteGrande);
		buttonRecorrencias.setBounds(5, 540, 155, 45);
		buttonRecorrencias.addActionListener(this);
		add(buttonRecorrencias);
		
		buttonAdicionarMovimento = new JButton("Adicionar");
		buttonAdicionarMovimento.setFont(fonteGrande);
		buttonAdicionarMovimento.setBounds(170, 540, 170, 45);
		buttonAdicionarMovimento.addActionListener(this);
		add(buttonAdicionarMovimento);
		
		buttonEditarMovimento = new JButton("Editar");
		buttonEditarMovimento.setFont(fonteGrande);
		buttonEditarMovimento.setBounds(345, 540, 170, 45);
		buttonEditarMovimento.addActionListener(this);
		add(buttonEditarMovimento);
		
		buttonExcluirMovimento = new JButton("Excluir");
		buttonExcluirMovimento.setFont(fonteGrande);
		buttonExcluirMovimento.setBounds(520, 540, 170, 45);
		buttonExcluirMovimento.addActionListener(this);
		add(buttonExcluirMovimento);
		
		labelEntrada = new JLabel("Entrada: 0");
		labelEntrada.setFont(fonteGrande2);
		labelEntrada.setBounds(710, 15, 400, 25);
		add(labelEntrada);
		
		labelSaida = new JLabel("Saida: 0");
		labelSaida.setFont(fonteGrande2);
		labelSaida.setBounds(733, 50, 400, 25);
		add(labelSaida);
		
		panelAdicionarMovimento = new PanelAdicionarMovimento(this, banco);
		panelAdicionarMovimento.setBounds(763, 100, 366, 370);
		panelAdicionarMovimento.setVisible(true);
		add(panelAdicionarMovimento);
		
		panelEditarMovimento = new PanelEditarMovimento(this, banco);
		panelEditarMovimento.setBounds(763, 100, 366, 370);
		panelEditarMovimento.setVisible(false);
		add(panelEditarMovimento);
		
		buttonExcluirBanco = new JButton("Excluir Banco");
		buttonExcluirBanco.setFont(fonteGrande);
		buttonExcluirBanco.setBounds(1020, 540, 160, 45);
		buttonExcluirBanco.addActionListener(this);
		add(buttonExcluirBanco);
		
		preencheTabela();
	}
	
	public void mostraAdicionar() {
		panelEditarMovimento.setVisible(false);
		panelAdicionarMovimento.resetaDados();
		panelAdicionarMovimento.atualizaData(ano, mes + 1);
		panelAdicionarMovimento.setVisible(true);
		panelEditarMovimento.setVisible(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == buttonEsquerda) {
			labelAno.setText(--ano + "");
			enableSetas();
			mes = 0;
			buttonEnabled.setEnabled(true);
			buttonEnabled = buttonMeses[0];
			buttonEnabled.setEnabled(false);
			preencheTabela();
			mostraAdicionar();
		}
		else if(e.getSource() == buttonDireita) {
			labelAno.setText(++ano + "");
			enableSetas();
			mes = 0;
			buttonEnabled.setEnabled(true);
			buttonEnabled = buttonMeses[0];
			buttonEnabled.setEnabled(false);
			preencheTabela();
			mostraAdicionar();
		}
		for(int i = 0; i < 12; i++) {
			if(e.getSource() == buttonMeses[i]) {
				mes = i;
				buttonEnabled.setEnabled(true);
				buttonEnabled = buttonMeses[i];
				buttonEnabled.setEnabled(false);
				preencheTabela();
				mostraAdicionar();
			}
		}
		if(e.getSource() == buttonRecorrencias) {
			boolean recorrenciaAplicada = false;
			if(0 == JOptionPane.showConfirmDialog(null, "Tem certeza que deseja aplicar os valores recorrente do mes passado?")) {
				int mesRecorrencia;
				int anoRecorrencia;
				if(mes == 0) {
					mesRecorrencia = 11;
					anoRecorrencia = ano - 1;
				}
				else {
					mesRecorrencia = mes - 1;
					anoRecorrencia = ano;
				}
				try {
					for(MovimentoFinanceiro mfRecorrente : banco.getAno(anoRecorrencia).getMes(mesRecorrencia).getMovimentos()) {
						if(mfRecorrente.isRecorrente()) {
							String nome = mfRecorrente.getNome();
							String data = ano + "-";
							if(mes + 1 < 10) {
								data += "0";
							}
							data += (mes + 1) + "-";
							if(mfRecorrente.getDia() < 10) {
								data += "0";
							}
							data += mfRecorrente.getDia();
							if(mfRecorrente.isVariavel()) {
								int id = MovimentoFinanceiroDAO.cria(data, banco.getId(), nome, 0, true, true);
								MovimentoFinanceiro mf = new MovimentoFinanceiro(data, id, nome, 0, true, true);
								banco.getAno(ano).getMes(mes).addMovimento(mf);
							}
							else {
								Double valor = mfRecorrente.getValor();
								int id = MovimentoFinanceiroDAO.cria(data, banco.getId(), nome, valor, true, false);
								MovimentoFinanceiro mf = new MovimentoFinanceiro(data, id, nome, valor, true, false);
								banco.getAno(ano).getMes(mes).addMovimento(mf);
							}
							recorrenciaAplicada = true;
						}
					}
					if(recorrenciaAplicada) {
						JOptionPane.showMessageDialog(null, "Valores aplicado com sucesso\nValores variaveis inseridos como 0");
						preencheTabela();
					}
				}
				catch(Exception ee) {
				}
			}
			if(!recorrenciaAplicada) {
				JOptionPane.showMessageDialog(null, "Nenhum valor recorrente detectado");
			}
		}
		else if(e.getSource() == buttonAdicionarMovimento) {
			panelAdicionarMovimento.resetaDados();
			panelEditarMovimento.setVisible(false);
			panelAdicionarMovimento.atualizaData(ano, mes + 1);
			panelAdicionarMovimento.setVisible(true);
		}
		else if(e.getSource() == buttonEditarMovimento) {
			int index = tabela.getSelectedRow();
			if(index != -1) {
				panelEditarMovimento.resetaDados();
				panelAdicionarMovimento.setVisible(false);
				String diaString = tabela.getValueAt(index, 0) + "";
				int dia = Integer.parseInt(diaString.substring(8));
				String nome = tabela.getValueAt(index, 1) + "";
				String valor = tabela.getValueAt(index, 2) + "";
				boolean recorrente;
				if(tabela.getValueAt(index, 3).equals("true")) {
					recorrente = true;
				}
				else {
					recorrente = false;
				}
				boolean variavel;
				if(tabela.getValueAt(index, 4).equals("true")) {
					variavel = true;
				}
				else {
					variavel = false;
				}
				panelEditarMovimento.atualizaInfo(ano, (mes + 1), dia, idMovimentos[index], nome, valor, recorrente, variavel);
				panelEditarMovimento.setVisible(true);
			}
			else {
				JOptionPane.showMessageDialog(null, "Selecione a linha que deseja editar");
			}
		}
		else if(e.getSource() == buttonExcluirMovimento) {
			int index = tabela.getSelectedRow();
			if(index != -1) {
				mostraAdicionar();
				MovimentoFinanceiro mf = banco.getAno(ano).getMes(mes).getMovimentos().get(index);
				String linha = "(" + mf.getData() + ", " + mf.getNome() + ", " + mf.getValor() + ", " + mf.isRecorrente() + ", " + mf.isVariavel() + ")";
				int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir a linha selecionada?\n" + linha);
				if(confirma == 0) {
					MovimentoFinanceiroDAO.exclui(idMovimentos[index]);
					banco.getAno(ano).getMes(mes).getMovimentos().remove(index);
					preencheTabela();
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Selecione a linha que deseja excluir");
			}
		}
		else if(e.getSource() == buttonExcluirBanco) {
			String nome = JOptionPane.showInputDialog("Confirme o nome do banco selecionado");
			if(nome != null) {
				if(nome.equals(banco.getNome())) {
					BancoDAO.exclui(banco.getId());
					int inddex = tabbedPaneBanco.indexOfComponent(this);
	                tabbedPaneBanco.removeTabAt(inddex);
	                bancos.remove(inddex);
				}
				else {
					JOptionPane.showMessageDialog(null, "Nome invalido");
				}
			}
		}
	}
	
	public void preencheTabela() {
		Double entrada = 0.0;
		Double saida = 0.0;
		try {
			ArrayList<MovimentoFinanceiro> movimentos = banco.getAno(ano).getMes(mes).getMovimentos();
			int size = movimentos.size();
			String[][] novoConteudo = new String[size][5];
			idMovimentos = new int[size];
			for(int i = 0; i < size; i++) {
				idMovimentos[i] = movimentos.get(i).getId();
				novoConteudo[i][0] = movimentos.get(i).getData();
				novoConteudo[i][1] = movimentos.get(i).getNome();
				Double valor = movimentos.get(i).getValor();
				novoConteudo[i][2] = valor + "";
				novoConteudo[i][3] = movimentos.get(i).isRecorrente() + "";
				novoConteudo[i][4] = movimentos.get(i).isVariavel() + "";
				if(valor > 0) {
					entrada += valor;
				}
				else {
					saida += Math.abs(valor);
				}
			}
			conteudoTabela.setDataVector(novoConteudo, titulos);
		}
		catch(Exception e) {
			conteudoTabela.setDataVector(null, titulos);
		}
		labelEntrada.setText("Entrada: " + entrada);
		labelSaida.setText("Saida: " + saida);
		if(conteudoTabela.getDataVector().size() == 0) {
			buttonEditarMovimento.setEnabled(false);
			buttonExcluirMovimento.setEnabled(false);
		}
		else {
			buttonEditarMovimento.setEnabled(true);
			buttonExcluirMovimento.setEnabled(true);
		}
		if(tabela.getRowCount() < 32) {
			tabela.getColumnModel().getColumn(0).setMaxWidth(78);
			tabela.getColumnModel().getColumn(1).setMaxWidth(153);
			tabela.getColumnModel().getColumn(2).setMaxWidth(145);
			tabela.getColumnModel().getColumn(3).setMaxWidth(78);
			tabela.getColumnModel().getColumn(4).setMaxWidth(63);
		}
		else {
			tabela.getColumnModel().getColumn(0).setMaxWidth(75);
			tabela.getColumnModel().getColumn(1).setMaxWidth(150);
			tabela.getColumnModel().getColumn(2).setMaxWidth(142);
			tabela.getColumnModel().getColumn(3).setMaxWidth(75);
			tabela.getColumnModel().getColumn(4).setMaxWidth(60);
		}
	}
	
	private void enableSetas() {
		if(ano > 2024) {
			buttonEsquerda.setEnabled(true);
		}
		else {
			buttonEsquerda.setEnabled(false);
		}
		if(ano < 2030) {
			buttonDireita.setEnabled(true);
		}
		else {
			buttonDireita.setEnabled(false);
		}
	}
}
