package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import model.Usuario;
import service.LogService;
import service.UsuarioService;

public class LoginInterfaz {

	private JFrame frame; 
	private JTextField userField;
	private JPasswordField passwordField;
	private UsuarioService usuarioService;
	private LogService logService;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				LoginInterfaz window = new LoginInterfaz();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the application.
	 */
	private LoginInterfaz() {
		usuarioService = UsuarioService.getSingletonInstance();
		logService = LogService.getSingletonInstance();
		logService.logAbrir();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame("Login de usuario");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				logService.logCerrar();
			}
		});
		frame.setResizable(false);
		frame.setBounds(100, 100, 322, 210);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel user = new JLabel("Usuario:");
		user.setHorizontalAlignment(SwingConstants.RIGHT);
		user.setBounds(10, 17, 64, 13);
		frame.getContentPane().add(user);
		
		JLabel pass = new JLabel("Contraseña:");
		pass.setHorizontalAlignment(SwingConstants.RIGHT);
		pass.setBounds(28, 57, 64, 13);
		frame.getContentPane().add(pass);
		
		userField = new JTextField();
		userField.setBounds(28, 30, 126, 20);
		frame.getContentPane().add(userField);
		userField.setColumns(10);
		
		JButton btnAceptar = new JButton("INGRESAR");
		btnAceptar.addActionListener(e -> logearse());
		btnAceptar.setBounds(164, 29, 108, 22);
		frame.getContentPane().add(btnAceptar);
		
		JButton btnRegistro = new JButton("Registrarse");
		btnRegistro.setBorder(new LineBorder(Color.GREEN));
		btnRegistro.addActionListener(e -> new RegistroInterfaz(frame));
		btnRegistro.setBounds(152, 127, 120, 22);
		frame.getContentPane().add(btnRegistro);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(28, 70, 126, 20);
		frame.getContentPane().add(passwordField);
		
		JLabel lblNewLabel = new JLabel("¿No tiene usuario?");
		lblNewLabel.setBounds(28, 128, 126, 21);
		frame.getContentPane().add(lblNewLabel);
	}
	
	private void logearse(){
		String user = userField.getText();
		String pass = new String(passwordField.getPassword());

		if(user.isEmpty() || pass.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Complete los campos por favor", "", JOptionPane.ERROR_MESSAGE);
		} else {
			Usuario usuario = new Usuario(user, pass);

			if(usuarioService.loginValido(usuario)) {
				logService.logLoggearUser(user, true);
				new ABMInterfaz().setVisible(true);
				frame.dispose();
			} else {
				logService.logLoggearUser(user, false);
				JOptionPane.showMessageDialog(null, "Usuario no valido", "", JOptionPane.ERROR_MESSAGE);
			}
		}

	}
}
