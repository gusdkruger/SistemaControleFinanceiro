package telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import daos.UsuarioDAO;
import filtros.LimitaLength;

@SuppressWarnings("serial")
public class TelaLogin extends JFrame implements ActionListener {
	
	private JTextField textFieldLogin;
	private JPasswordField passwordField;
	private JButton buttonLogin;
	private JButton buttonCadastrar;
	
	private final Font fonteGrande = new Font("Arial", Font.PLAIN, 25);
	private final Font fontePequena = new Font("Arial", Font.PLAIN, 15);
	
	public TelaLogin() {
		super("Controle Financeiro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(426, 399);
		setResizable(false);
		setLayout(null);
		
		JLabel labelUsuario = new JLabel("Login");
		labelUsuario.setFont(fontePequena);
		labelUsuario.setBounds(80, 23, 250, 15);
		add(labelUsuario);
		
		textFieldLogin = new JTextField();
		textFieldLogin.setFont(fonteGrande);
		textFieldLogin.setBounds(80, 40, 250, 50);
		textFieldLogin.setDocument(new LimitaLength(20));
		add(textFieldLogin);
		
		JLabel labelSenha = new JLabel("Senha");
		labelSenha.setFont(fontePequena);
		labelSenha.setBounds(80, 113, 250, 15);
		add(labelSenha);
		
		passwordField = new JPasswordField();
		passwordField.setFont(fonteGrande);
		passwordField.setBounds(80, 130, 250, 50);
		textFieldLogin.setDocument(new LimitaLength(20));
		add(passwordField);
		
		buttonLogin = new JButton("Logar");
		buttonLogin.setFont(fonteGrande);
		buttonLogin.setBounds(80, 200, 250, 50);
		buttonLogin.addActionListener(this);
		add(buttonLogin);
		
		buttonCadastrar = new JButton("Cadastrar");
		buttonCadastrar.setFont(fonteGrande);
		buttonCadastrar.setBounds(80, 270, 250, 50);
		buttonCadastrar.addActionListener(this);
		add(buttonCadastrar);
		
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		String login = textFieldLogin.getText();
		@SuppressWarnings("deprecation")
		String senha = passwordField.getText();
		if(e.getSource() == buttonLogin) {
			if(!login.isBlank() && !senha.isBlank()) {
				if(UsuarioDAO.verificaLogin(login, senha)) {
					new TelaLogado(login);
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "Usuario ou senha invalidos");
					passwordField.setText("");
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Preencha os campos");
			}
		}
		else if(e.getSource() == buttonCadastrar) {
			if(!login.isBlank() && !senha.isBlank()) {
				String confirmaSenha = JOptionPane.showInputDialog("Corfime a senha");
				if(confirmaSenha != null) {
					if(senha.equals(confirmaSenha)) {
						if(UsuarioDAO.cadastra(login, senha)) {
							JOptionPane.showMessageDialog(null, "Usuario cadastrado com sucesso");
							new TelaLogado(login);
							dispose();
						}
						else {
							textFieldLogin.setText("");
							passwordField.setText("");
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "As senhas nao sao iguais");
						passwordField.setText("");
					}
				}
				else {
					passwordField.setText("");
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Preencha os campos");
			}
		}	
	}
}
