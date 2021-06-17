package view;

import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Usuario;
import service.LogService;
import service.UsuarioService;

class RegistroInterfaz {

	private JDialog frame;
	private JTextField userField;
	private JPasswordField passwordField; 
	private UsuarioService usuarioService;
	private LogService logService;
	private Frame framePadre;
	/**
	 * Create the application.
	 *
	 */
	RegistroInterfaz(Frame frame) {
		framePadre = frame;
		usuarioService = UsuarioService.getSingletonInstance();
		logService = LogService.getSingletonInstance();
		initialize();
	}

	/**
	 * 
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		/* ventana */
		frame = new JDialog(framePadre,"Registro de usuario",true);
		frame.setResizable(false);
		frame.setBounds(100, 100, 201, 196);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		/* usuario */
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(12, 11, 82, 20);
		frame.getContentPane().add(lblUsuario);
		
		/* contrasena */
		JLabel lblPass = new JLabel("Contraseña:");
		lblPass.setBounds(12, 58, 92, 20);
		frame.getContentPane().add(lblPass);
		
		userField = new JTextField();
		userField.setBounds(12, 28, 161, 20);
		frame.getContentPane().add(userField);
		userField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(12, 77, 161, 20);
		frame.getContentPane().add(passwordField);
		
		/* boton confirmar */
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(e -> registrar()); 
		btnConfirmar.setBounds(38, 118, 103, 23);
		frame.getContentPane().add(btnConfirmar);
		
		frame.setVisible(true);
	}

	/*
	 * Realiza el registro del usuario
	 */
	private void registrar() {
		/* obtener los datos */
		String user = userField.getText();
		String pass = new String(passwordField.getPassword());
		
		/* valida */
		if(user.isEmpty() || pass.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Complete los campos por favor", "", JOptionPane.ERROR_MESSAGE);
		} else {
			Usuario usuario = new Usuario(userField.getText(), new String(passwordField.getPassword()));
			
			/* si no se pudo registrar */
			if(!usuarioService.registrarUsuario(usuario)) {
				logService.logRegistrarUser(user, false);
				JOptionPane.showMessageDialog(null, "El usuario ya existe", "", JOptionPane.ERROR_MESSAGE);
			} else {
				/* se pudo registrar */
				logService.logRegistrarUser(user, true);
				JOptionPane.showMessageDialog(null, "Usuario registrado correctamente", "", JOptionPane.INFORMATION_MESSAGE);
				frame.dispose();
			}
		}
		
	}
}
