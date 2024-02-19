package telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import daos.BancoDAO;
import daos.UsuarioDAO;
import modelos.Banco;

@SuppressWarnings("serial")
public class TelaLogado extends JFrame implements ActionListener {

	private String login;
	private ArrayList<Banco> bancos;
	
	private JButton buttonCriarBanco;
	private JButton buttonMudarSenha;
	private JButton buttonExcluirConta;
	private JTabbedPane tabbedPaneBanco;
	
	private final Font fonteGrande = new Font("Arial", Font.PLAIN, 20);
	
	public TelaLogado(String login) {
		super("Controle Financeiro");
		this.login = login;
		bancos = BancoDAO.getBancos(login);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1216, 712);
		setResizable(false);
		setLayout(null);
		
		buttonCriarBanco = new JButton("Criar Banco");
		buttonCriarBanco.setFont(fonteGrande);
		buttonCriarBanco.setBounds(5, 5, 180, 40);
		buttonCriarBanco.addActionListener(this);
		add(buttonCriarBanco);
		
		buttonMudarSenha = new JButton("Mudar Senha");
		buttonMudarSenha.setFont(fonteGrande);
		buttonMudarSenha.setBounds(830, 5, 180, 40);
		buttonMudarSenha.addActionListener(this);
		add(buttonMudarSenha);
		
		buttonExcluirConta = new JButton("Excluir Conta");
		buttonExcluirConta.setFont(fonteGrande);
		buttonExcluirConta.setBounds(1015, 5, 180, 40);
		buttonExcluirConta.addActionListener(this);
		add(buttonExcluirConta);
		
		tabbedPaneBanco = new JTabbedPane();
		tabbedPaneBanco.setBounds(5, 50, 1190, 618);
		atualizaTabs();
		add(tabbedPaneBanco);
		
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == buttonCriarBanco) {
			if(tabbedPaneBanco.getTabCount() < 7) {
				String nome = JOptionPane.showInputDialog("Digite o nome do novo banco");
				if(nome != null && validaString(nome, "Nome deve conter entre 1 e 20 caracteres e nao pode conter somente espacos")) {
						int id = BancoDAO.cria(login, nome);
						if(id != -1) {
							bancos.add(new Banco(id, nome));
							atualizaTabs();
						}
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Maximo de 7 bancos");
			}
		}
		else if(e.getSource() == buttonMudarSenha) {
			if(confirmaSenha("Confirme sua senha para de poder editar ela")) {
				String senha = JOptionPane.showInputDialog(null, "Digite a nova senha");
				if(senha != null && validaString(senha, "Senha deve conter entre 1 e 20 caracteres e nao pode conter somente espacos")) {
					String confirmaSenha = JOptionPane.showInputDialog(null, "Corfime a senha");
					if(confirmaSenha != null) {
						if(senha.equals(confirmaSenha)) {
							if(UsuarioDAO.edita(login, senha)) {
								JOptionPane.showMessageDialog(null, "Senha atualizada com sucesso");
								new TelaLogin();
								dispose();
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "As senhas nao sao iguais");
						}
					}
				}
			}
		}
		else if(e.getSource() == buttonExcluirConta) {
			if(confirmaSenha("Confirme sua senha para deletar sua conta")) {
				UsuarioDAO.exclui(login);
				dispose();
			}
		}
	}
	
	private void atualizaTabs() {
		tabbedPaneBanco.removeAll();
		for(Banco b : bancos) {
			tabbedPaneBanco.addTab(b.getNome(), new PanelBanco(b, tabbedPaneBanco, bancos));
		}
	}
	
	private boolean confirmaSenha(String mensagem) {
		String senha = JOptionPane.showInputDialog(null, mensagem);
		if(senha != null) {
			if(UsuarioDAO.verificaLogin(login, senha)) {
				return true;
			}
			else {
				JOptionPane.showMessageDialog(null, "Senha invalida");
			}
		}
		return false;
	}

	private boolean validaString(String s, String mensagem) {
		if(s.isBlank() || s.length() < 1 || s.length() > 20) {
			JOptionPane.showMessageDialog(null, mensagem);
			return false;
		}
		return true;
	}
}
