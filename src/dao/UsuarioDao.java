package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

import org.mindrot.jbcrypt.*;
import model.Usuario;

/**
 * Capa de acceso a la base de datos de usuarios.
 */
public class UsuarioDao {

	/* instancia del singleton */
	private static UsuarioDao instancia;

	/* separador de usuarios */
	private final static String SEPARADOR = "&&";

	/* paths de la base de datos */
	private String path;
	private String pathBackup;
	private String decodedPath;
	private String decodedPathBackup;

	/**
	 * Singleton
	 */
	private UsuarioDao() {
		getPath();
	}

	public static UsuarioDao getSingletonInstance() {
		if(instancia == null) {
			instancia = new UsuarioDao(); 
		}
		return instancia;
	}
	
	/**
	 * Verifica que un usuario sea valido. Para esto, se fija que el usuario
	 * ingresado no exista en la base de datso de usuarios.
	 *
	 * Se utiliza a la hora de registrar un nuevo usuario.
	 *
	 * @param user el usuario ingresado
	 * @return devuelve true si es valido. False en otro caso
	 */
	public boolean usuarioValido(Usuario user) {
		
		Usuario userIngresado = new Usuario(user.obtenerUser(), BCrypt.hashpw(user.obtenerPass(), BCrypt.gensalt(4)));
		List<Usuario> usuarios = new ArrayList<>();

		cargarUsuarios(usuarios);

		/* me fijo que no se encuentre entre los usuarios */
		for(Usuario userSeguro : usuarios) {
			if(userSeguro.equals(userIngresado)) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Verifica que un login sea valido. Para esto, verifica que exista en la
	 * base de datos y que el hash de la contraseña coincida con el almacenado
	 *
	 * @param user el usuario que hace login.
	 * @return true si es un login valido. false en otro caso
	 */
	public boolean loginValido(Usuario user) {
		List<Usuario> usuarios = new ArrayList<>();

		cargarUsuarios(usuarios);

		/* me fijo que exista en la base de datos y coincida su hash */
		for(Usuario userSeguro : usuarios) {
			if(userSeguro.obtenerUser().equals(user.obtenerUser())) {
				if(BCrypt.checkpw(user.obtenerPass(), userSeguro.obtenerPass())) {
					return true;
				}
			}
		}
		
		return false;
	}

	/**
	 * Cargo los usuarios en memoria
	 *
	 * @param usuarios Parametro de salida donde se cargaran los usuarios
	 */
	private void cargarUsuarios(List<Usuario> usuarios) {
		Scanner sc;
		try {
			sc = new Scanner(new File(decodedPath));
			while (sc.hasNextLine()) {
				/* parseo de atributos */
				String[] atributos = sc.nextLine().split(SEPARADOR);

				usuarios.add(new Usuario(atributos[0], atributos[1]));
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Genero un nuevo usuario
	 *
	 * @param user el usuario a generar
	 * @return true si se pudo generar. false en otro caso
	 */
	public boolean generarUsuario(Usuario user) {
		
		Usuario userIngresado = new Usuario(user.obtenerUser(), BCrypt.hashpw(user.obtenerPass(), BCrypt.gensalt(4)));
		BufferedWriter out; 
		BufferedWriter outBackup; 
		
		if(usuarioValido(userIngresado)) {
			try {
				out = new BufferedWriter(new FileWriter(decodedPath, true));
				out.write(userIngresado.obtenerUser() + SEPARADOR + userIngresado.obtenerPass() + '\n');
				out.close();
				
				outBackup = new BufferedWriter(new FileWriter(decodedPathBackup, true));
				outBackup.write(userIngresado.obtenerUser() + SEPARADOR + userIngresado.obtenerPass() + '\n');
				outBackup.close();
				
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	private void getPath() {
		path = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "/Gestor de Libros";
		pathBackup = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "/Gestor de Libros backup";
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
			decodedPathBackup = URLDecoder.decode(pathBackup, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		createPath(decodedPath);
		createPath(decodedPathBackup);
		
		path = path + "/Usuarios.txt";
		pathBackup = pathBackup + "/Usuarios.txt";
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
			decodedPathBackup = URLDecoder.decode(pathBackup, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		createFile(decodedPath, decodedPathBackup);
	}
	
	private void createPath(String decodedPath) {
		File fi = new File(decodedPath);
		if(!fi.exists()) {
			fi.mkdir();
		}
	}
	
	private void createFile(String decodedPath, String decodedPathBackup) {
		File fi = new File(decodedPath);
		File fiBackup = new File(decodedPathBackup);
		
		if(!fi.exists()) {
			if(fiBackup.exists()) {
				BufferedReader br;
				PrintWriter pw;
				String cad;
				
				try {
					br = new BufferedReader(new FileReader(fiBackup));
					pw = new PrintWriter(new FileWriter(fi));
					
					while((cad = br.readLine()) != null) {
						pw.print(cad);
					}
					
					br.close();
					pw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					fi.createNewFile();
					fiBackup.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
