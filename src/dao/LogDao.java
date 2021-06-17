package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;

/**
 * Capa de acceso a la base de datos de Log.
 */
public class LogDao {
	
	private static LogDao instancia;
	private String path;
	private String decodedPath;
	private BufferedWriter out;
	private DateFormat dateFormat;
	private Date date;
	
	/*
	 * Singleton
	 */
	private LogDao() {
		getPath();
		try {
			this.out = new BufferedWriter(new FileWriter(decodedPath, true));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		this.date = new Date();
	}

	public static LogDao getSingletonInstance() {
		if(instancia == null) {
			instancia = new LogDao();
		}
		return instancia;
	}
	
	/**
	 * Abrir el log
	 */
	public void logAbrir() {
		log(getDate() + " - Gestor de Libros abierto");
	}
	
	/**
	 * Loggear el login del usuario
	 *
	 * @param user     El nombre del usuario que se logueo
	 * @param correcto si el logueo fue correcto o incorrecto (por ejemplo, sera
	 *                 incorrecto si la contraseña es incorrecta)
	 */
	public void logLoggearUser(String user, boolean correcto) {
		log(getDate() + " - Login " + ((correcto) ? "correcto " : "incorrecto ") + "del usuario " + user);
	}
	
	/**
	 * Loguear el registro de usuario
	 *
	 * Similar a {@link #logLoggearUser(String user, boolean correcto)}
	 */
	public void logRegistrarUser(String user, boolean correcto) {
		log(getDate() + " - Registro " + ((correcto) ? "correcto " : "incorrecto ") + "del usuario " + user);
	}
	
	/**
	 * Loguear el registro de un nuevo libro.
	 *
	 * Similar a {@link #logLoggearUser(String user, boolean correcto)}. En este
	 * caso, sera incorrecto cuando, por ejemplo, se repita un ISBN.
	 */
	public void logRegistrarLibro(String isbn, boolean correcto) {
		log(getDate() + " - Registro " + ((correcto) ? "correcto " : "incorrecto ") + "del libro con ISBN " + isbn);
	}

	/**
	 * Loguear la modificacion de un libro.
	 *
	 * Similar a {@link #logRegistrarLibro(String isbn, boolean correcto)}.
	 */
	public void logModificarLibro(String isbn, boolean correcto) {
		log(getDate() + " - Modificacion " + ((correcto) ? "correcta " : "incorrecta ") + "del libro con ISBN " + isbn);
	}
	
	/**
	 * Loguear la eliminacion de un libro.
	 *
	 * Similar a {@link #logRegistrarLibro(String isbn, boolean correcto)}.
	 */
	public void logEliminarLibro(String isbn, boolean correcto) {
		log(getDate() + " - Eliminacion " + ((correcto) ? "correcta " : "incorrecta ") + "del libro con ISBN " + isbn);
	}
	
	/**
	 * Loggear que los libros se han ordenado
	 */
	public void logOrdenarLibro() {
		log(getDate() + " - Lista de libros ordenada");
	}
	
	/**
	 * Logguar que se ha cerrado el log
	 */
	public void logCerrar() {
		log(getDate() + " - Gestor de Libros cerrado");
		liberarRecursos();
	}
	
	/**
	 * Obtiene la fecha del log formateada.
	 *
	 * @return la fecha del log
	 */
	private String getDate() {
		return dateFormat.format(date);
	}

	/**
	 * Realiza el log en si
	 *
	 * @param texto el contenido del log.
	 */
	private void log(String texto) {
		try {
			out.write(getDate() + " - " + texto);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene el path del log
	 */
	private void getPath() {
		path = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "/Gestor de Libros";
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		createPath(decodedPath);

		path = path + "/Log.txt";
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		createFile(decodedPath);
	}

	private void createPath(String decodedPath) {
		File fi = new File(decodedPath);
		if(!fi.exists()) {
			fi.mkdir();
		}
	}

	private void createFile(String decodedPath) {
		File fi = new File(decodedPath);
		if(!fi.exists()) {
			try {
				fi.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void liberarRecursos() {
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
