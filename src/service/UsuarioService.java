package service;

import dao.UsuarioDao;
import model.Usuario;

public class UsuarioService {
	
	private UsuarioDao usuarioDao; 
	private static UsuarioService instancia;
	
	private UsuarioService() {
		usuarioDao = UsuarioDao.getSingletonInstance();
	} 

	public static UsuarioService getSingletonInstance() {
		if(instancia == null) {
			instancia = new UsuarioService();
		}
		return instancia;
	}
	
	public boolean usuarioValido(Usuario user) {
		return usuarioDao.usuarioValido(user);
	}
	
	public boolean registrarUsuario(Usuario user) {
		return usuarioDao.generarUsuario(user);
	}
	
	public boolean loginValido(Usuario user) {
		return usuarioDao.loginValido(user);
	}
}
