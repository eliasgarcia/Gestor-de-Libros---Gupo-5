package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Libro;
import service.LibroService;
import service.LogService;
import java.awt.Color;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;

class ABMInterfaz extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int ISBN = 0;
	private static final int TITULO = 1;
	private static final int AUTOR = 2;
	private static final int EDITORIAL = 3;
	private static final int EDICION = 4;
	private static final int ANIO_PUBLICACION = 5; 
	private static final int COLUMNAS = 6;
	private static final String[] ROTULOS = { "ISBN", "Titulo", "Autor", "Editorial", "Edicion", "Año publicacion" };

	private JButton btnBuscar;
	private JButton btnAgregar;
	private JButton btnLimpiar;
	private JButton btnOrdenar;
	private JButton btnEliminar;
	private JButton btnModificar;
	private JButton btnListar;

	private JTextField txfIsbn;
	private JTextField txfAutor;
	private JTextField txfTitulo;
	private JTextField txfEdicion;
	private JTextField txfEditorial;
	private JTextField txfAnioPublicion;

	private JTable tabla;
	private TableModel tablaModel;
	private LibroService libroService;
	private LogService logService;

	ABMInterfaz() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				logService.logCerrar();
			}
		});
		libroService = LibroService.obtenerSingletonInstance();
		logService = LogService.getSingletonInstance();
		especificarComponents();
		especificarListeners();
	}

	private void especificarComponents() {
		setTitle("Gestor de Libros");
		setBounds(100, 100, 1017, 452);
		setResizable(Boolean.FALSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 108, 874, 299);
		getContentPane().add(scrollPane);

		tablaModel = new DefaultTableModel(null, ROTULOS);
		tabla = new JTable(tablaModel);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setDefaultEditor(Object.class, null);
		scrollPane.setViewportView(tabla);

		JLabel lblIsbn = new JLabel("ISBN");
		lblIsbn.setBounds(12, 54, 105, 15);
		getContentPane().add(lblIsbn);

		JLabel lblTitulo = new JLabel("Titulo");
		lblTitulo.setBounds(124, 54, 105, 15);
		getContentPane().add(lblTitulo);

		JLabel lblAutor = new JLabel("Autor");
		lblAutor.setBounds(238, 54, 105, 15);
		getContentPane().add(lblAutor);

		JLabel lblEditorial = new JLabel("Editorial");
		lblEditorial.setBounds(345, 54, 70, 15);
		getContentPane().add(lblEditorial);

		JLabel lblEdicion = new JLabel("Edicion");
		lblEdicion.setBounds(457, 54, 70, 15);
		getContentPane().add(lblEdicion);

		JLabel lblAoPublicacion = new JLabel("Año");
		lblAoPublicacion.setBounds(569, 54, 94, 15);
		getContentPane().add(lblAoPublicacion);

		txfIsbn = new JTextField();
		txfIsbn.setBounds(12, 71, 105, 25);
		getContentPane().add(txfIsbn);
		txfIsbn.setColumns(10);

		txfTitulo = new JTextField();
		txfTitulo.setColumns(10);
		txfTitulo.setBounds(124, 71, 105, 25);
		getContentPane().add(txfTitulo);

		txfAutor = new JTextField();
		txfAutor.setColumns(10);
		txfAutor.setBounds(231, 71, 105, 25);
		getContentPane().add(txfAutor);

		txfEditorial = new JTextField();
		txfEditorial.setColumns(10);
		txfEditorial.setBounds(348, 71, 105, 25);
		getContentPane().add(txfEditorial);

		txfEdicion = new JTextField();
		txfEdicion.setColumns(10);
		txfEdicion.setBounds(457, 71, 105, 25);
		getContentPane().add(txfEdicion);

		txfAnioPublicion = new JTextField();
		txfAnioPublicion.setColumns(10);
		txfAnioPublicion.setBounds(569, 71, 105, 25);
		getContentPane().add(txfAnioPublicion);

		btnAgregar = new JButton("Agregar");
		btnAgregar.setBorder(new LineBorder(Color.GREEN));
		btnAgregar.setBounds(686, 34, 200, 25);
		getContentPane().add(btnAgregar);

		btnListar = new JButton("Listar");
		btnListar.setBounds(893, 110, 100, 25);
		getContentPane().add(btnListar);

		btnOrdenar = new JButton("Ordenar");
		btnOrdenar.setBounds(896, 146, 97, 25);
		getContentPane().add(btnOrdenar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBorder(new LineBorder(Color.RED));
		btnEliminar.setBounds(893, 382, 100, 25);
		getContentPane().add(btnEliminar);

		btnModificar = new JButton("Modificar");
		btnModificar.setBorder(new LineBorder(Color.BLUE));
		btnModificar.setBounds(893, 346, 100, 25);
		getContentPane().add(btnModificar);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(686, 71, 94, 25);
		getContentPane().add(btnBuscar);

		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(792, 71, 94, 25);
		getContentPane().add(btnLimpiar);
	}

	private void especificarListeners() {
		btnBuscar.addActionListener(e -> buscar());
		btnListar.addActionListener(e -> listar());
		btnLimpiar.addActionListener(e -> limpiar());
		btnOrdenar.addActionListener(e -> ordenar());
		btnAgregar.addActionListener(e -> agregar());
		btnEliminar.addActionListener(e -> eliminar());
		btnModificar.addActionListener(e -> modificar());
	}

	void buscar() {
		String isbn = txfIsbn.getText();
		String titulo = txfTitulo.getText();
		String autor = txfAutor.getText();
		String editorial = txfEditorial.getText();
		String edicion = txfEdicion.getText();
		String anioPublicacion = txfAnioPublicion.getText();

		List<Libro> libros = libroService.obtenerLibroConFiltro(isbn, titulo, autor, editorial, edicion,
				anioPublicacion);

		tablaModel = new DefaultTableModel(obtenerLibrosToModel(libros), ROTULOS);
		tabla.setModel(tablaModel);
	}

	private void ordenar() {
		tablaModel = new DefaultTableModel(obtenerLibrosToModel(libroService.ordenar()), ROTULOS);
		tabla.setModel(tablaModel);
		logService.logOrdenarLibro();
	}

	private void agregar() {
		new NuevoLibroInterfaz(this);
	}

	private void eliminar() {
		int libroSeleccionado = tabla.getSelectedRow();
		String isbn = "";
		if (libroSeleccionado == -1)
			JOptionPane.showMessageDialog(null, "Debe seleccionar un libro.");
		else
			try {
				isbn = tabla.getModel().getValueAt(libroSeleccionado, 0).toString();
				tablaModel = new DefaultTableModel(obtenerLibrosToModel(libroService.eliminar(isbn)), ROTULOS);
				tabla.setModel(tablaModel);
				logService.logEliminarLibro(isbn, true);
			} catch (Exception e) {
				logService.logEliminarLibro(isbn, false);
				JOptionPane.showMessageDialog(null, "No se pudo eliminar correctamente.");
			}
	}

	private void listar() {

		tablaModel = new DefaultTableModel(obtenerLibrosToModel(libroService.obtenerLibros()), ROTULOS);
		tabla.setModel(tablaModel);
	}

	private Object[][] obtenerLibrosToModel(List<Libro> libros) {
		String[][] filas = new String[libros.size()][COLUMNAS];
		int i = 0;
		for (Libro libro : libros)
			filas[i++] = libro.enFormatoFila();

		return filas;
	}

	private void limpiar() {
		txfIsbn.setText("");
		txfTitulo.setText("");
		txfAutor.setText("");
		txfEditorial.setText("");
		txfEdicion.setText("");
		txfAnioPublicion.setText("");
	}

	private void modificar() {
		int libroSeleccionado = tabla.getSelectedRow();

		if (libroSeleccionado == -1)
			JOptionPane.showMessageDialog(null, "Debe seleccionar un libro.");
		else
			try {
				String isbn = tabla.getValueAt(libroSeleccionado, ISBN).toString();
				String titulo = tabla.getValueAt(libroSeleccionado, TITULO).toString();
				String autor = tabla.getValueAt(libroSeleccionado, AUTOR).toString();
				String editorial = tabla.getValueAt(libroSeleccionado, EDITORIAL).toString();
				String edicion = tabla.getValueAt(libroSeleccionado, EDICION).toString();
				String anioPublicacion = tabla.getValueAt(libroSeleccionado, ANIO_PUBLICACION).toString();

				Libro aModificar = new Libro(isbn, titulo, autor, editorial, edicion, anioPublicacion);

				new ModificarLibroInterfaz(aModificar, this);
				buscar();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "No se pudo eliminar correctamente.");
			}
	}
}
