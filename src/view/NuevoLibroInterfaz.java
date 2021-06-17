package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.Libro;
import service.LibroService;
import service.LogService;

/*
 * Interfaz de nuevo libro
 *
 */
class NuevoLibroInterfaz {

	private JDialog frame;
	private JLabel lblMensajeDeError;
	private JTextField txfISBN;
	private JTextField txfTitulo;
	private JTextField txfAutor;
	private JTextField txfEditorial;
	private JTextField txfEdicion;
	private JTextField txfAnioPublicacion;
	private LibroService libroService;
	private LogService logService;
	private ABMInterfaz abmInterfaz;

	/**
	 * Crea la interfaz de nuevo libro
	 *
	 * @param abmInterfaz la interfaz anterior
	 */
	NuevoLibroInterfaz(ABMInterfaz abmInterfaz) {
		libroService = LibroService.obtenerSingletonInstance();
		logService = LogService.getSingletonInstance();
		this.abmInterfaz = abmInterfaz;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		int anchoCampo = 150;
		/* ventana */
		frame = new JDialog(abmInterfaz,"Registrar nuevo libro",true);
		frame.setResizable(false);
		frame.setBounds(100, 100, 254, 439);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		/* titulo */
		JLabel lblCompleteLosCampos = new JLabel("Complete los campos:");
		lblCompleteLosCampos.setFont(lblCompleteLosCampos.getFont().deriveFont(lblCompleteLosCampos.getFont().getStyle() | Font.BOLD));
		lblCompleteLosCampos.setBounds(48, 11, 209, 14);
		frame.getContentPane().add(lblCompleteLosCampos);
		
		txfISBN = new JTextField();
		txfISBN.setToolTipText("");
		txfISBN.setBounds(48, 52, anchoCampo, 20);
		frame.getContentPane().add(txfISBN);
		txfISBN.setColumns(10);
		
		/* isbn */
		JLabel lblIsbn = new JLabel("ISBN:");
		lblIsbn.setBounds(48, 38, anchoCampo, 14);
		frame.getContentPane().add(lblIsbn);
		
		/* titulo */
		JLabel lblTtulo = new JLabel("Título:");
		lblTtulo.setBounds(48, 83, anchoCampo, 14);
		frame.getContentPane().add(lblTtulo);
		
		txfTitulo = new JTextField();
		txfTitulo.setBounds(48, 97, anchoCampo, 20);
		frame.getContentPane().add(txfTitulo);
		txfTitulo.setColumns(10);
		
		/* autor */
		JLabel lblAutor = new JLabel("Autor:");
		lblAutor.setBounds(48, 128, anchoCampo, 14);
		frame.getContentPane().add(lblAutor);
		
		txfAutor = new JTextField();
		txfAutor.setBounds(48, 142, anchoCampo, 20);
		frame.getContentPane().add(txfAutor);
		txfAutor.setColumns(10);
		
		/* editorial */
		JLabel lblEditorial = new JLabel("Editorial:");
		lblEditorial.setBounds(48, 173, anchoCampo, 14);
		frame.getContentPane().add(lblEditorial);
		
		txfEditorial = new JTextField();
		txfEditorial.setBounds(48, 187, anchoCampo, 20);
		frame.getContentPane().add(txfEditorial);
		txfEditorial.setColumns(10);
		
		/* edicion */
		JLabel lblEdicion = new JLabel("Edición:");
		lblEdicion.setBounds(48, 223, anchoCampo, 14);
		frame.getContentPane().add(lblEdicion);
		
		txfEdicion = new JTextField();
		txfEdicion.setBounds(48, 237, anchoCampo, 20);
		frame.getContentPane().add(txfEdicion);
		txfEdicion.setColumns(10);
		
		/* año */
	 	JLabel lblAnioPublicacion = new JLabel("Año publicación:");
		lblAnioPublicacion.setBounds(48, 268, anchoCampo, 14);
		frame.getContentPane().add(lblAnioPublicacion);
		
		txfAnioPublicacion = new JTextField();
		txfAnioPublicacion.setBounds(48, 282, anchoCampo, 20);
		frame.getContentPane().add(txfAnioPublicacion);
		txfAnioPublicacion.setColumns(10);
		
		/* boton aceptar */
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(e -> agregar());
		btnAceptar.setBounds(17, 346, 92, 20);
		frame.getContentPane().add(btnAceptar);
		
		/* boton cancelar */
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e -> frame.dispose());
		btnCancelar.setBounds(119, 346, 92, 20);
		frame.getContentPane().add(btnCancelar);
		
		/*
		 * Mensaje de error
		 * Aparece cuando falla la validacion
		 */
		lblMensajeDeError = new JLabel("Mensaje de error");
		lblMensajeDeError.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensajeDeError.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMensajeDeError.setForeground(Color.RED);
		lblMensajeDeError.setBounds(-58, 322, 357, 14);
		lblMensajeDeError.setVisible(false);
		frame.getContentPane().add(lblMensajeDeError);
		
		frame.setVisible(true);
	}
	
	/*
	 * Agregar nuevo libro
	 *
	 */
	private void agregar() {

		/* si hay un campo vacio */
		if(campoVacio()) {
			lblMensajeDeError.setText("Complete los campos por favor");
			lblMensajeDeError.setVisible(true);
		} else {
			/* todos los campos llenos */
			String isbn = txfISBN.getText();
			String titulo = txfTitulo.getText(); 
			String autor = txfAutor.getText();
			String editorial = txfEditorial.getText();
			String edicion = txfEdicion.getText();
			String anioPublicacion = txfAnioPublicacion.getText();
			
			Libro nuevo;
			try {
				/* creo un nuevo libro */
				nuevo = new Libro(isbn, titulo, autor, editorial, edicion, anioPublicacion);
				
				/* guardo */
				if(libroService.guardar(nuevo)) {
					/* si pudo guardar */
					abmInterfaz.buscar();
					logService.logRegistrarLibro(isbn, true);
					JOptionPane.showMessageDialog(null, "Libro guardado correctamente", "", JOptionPane.INFORMATION_MESSAGE);
					frame.dispose();
				} else {
					/* ISBN repetido */
					logService.logRegistrarLibro(isbn, false);
					lblMensajeDeError.setText("ISBN Repetido. Elija otro.");
					lblMensajeDeError.setVisible(true);
				}
			} catch (Exception error) {
				/*
				 * si al crear un libro hay errores, los muestro con el
				 * mensaje inferior
				 */
				lblMensajeDeError.setText(error.getMessage());
				lblMensajeDeError.setVisible(true);
			}

			
		}
	}
	
	private boolean campoVacio() {
		return  txfISBN.getText().isEmpty() 			||
				txfAutor.getText().isEmpty() 			||
				txfTitulo.getText().isEmpty() 			||
				txfAnioPublicacion.getText().isEmpty()	||
				txfEditorial.getText().isEmpty() 		||
				txfEdicion.getText().isEmpty();
	}
}
