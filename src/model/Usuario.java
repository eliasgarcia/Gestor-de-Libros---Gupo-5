package model;

/**
 * BO de Usuario
 */
public class Usuario {

	/* atributos */
	private String user;
	private String pass;
	
	public Usuario(String user, String pass) {
		this.user = user.toLowerCase();
		this.pass = pass; 
	}

	/*
	 * ==========================================================================
	 * SETTERS
	 * ==========================================================================
	 */
	
	public void especificarUser(String user) {
		this.user = user;
	}
	
	public void especificarPass(String pass) {
		this.pass = pass;
	}

	/*
	 * ==========================================================================
	 * GETTERS
	 * ==========================================================================
	 */
	
	public String obtenerUser() {
		return user;
	}
	
	public String obtenerPass() {
		return pass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		if (obj == null) return false;
		
		if (getClass() != obj.getClass()) return false;
		
		Usuario other = (Usuario) obj;
		
		if (user == null) {
			if (other.user != null) return false;
		} else if (!user.equals(other.user)) return false;
		
		return true;
	}

	
}
