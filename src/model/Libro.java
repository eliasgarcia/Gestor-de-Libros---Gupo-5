package model;

import java.util.Comparator;
import java.util.regex.Pattern;

public class Libro implements Comparable<Libro>, Comparator<Libro> {

	private String isbn;
	private String titulo;
	private String autor;
	private String editorial;
	private Integer edicion;
	private Integer anioPublicacion;

	public Libro() {
		
	}

	public Libro(String isbn, String titulo, String autor, String editorial, String edicion, String anioPublicacion) throws Exception{
		
		if( isbn.length() != 10 && isbn.length() != 13 )
			throw new Exception("Longitud incorrecta del ISBN");
		
		if( !esNumerico(isbn) )
			throw new Exception("ISBN es un campo numérico");
		
		if( titulo.length() == 0 )
			throw new Exception("Campo Título vacío");
			
		if( !esAlfanumerico(titulo) )
			throw new Exception("Título es un campo alfanumérico");
		
		if( autor.length() == 0 )
			throw new Exception("Campo Autor vacío");
		
		if( !esAlfabetico(autor) )
			throw new Exception("Autor es un campo alfabético");
		
		if( editorial.length() == 0 )
			throw new Exception("Campo Editorial vacío");
		
		if( !esAlfabetico(editorial) )
			throw new Exception("Editorial es un campo alfabético");
		
		if( !esNumerico(edicion) )
			throw new Exception("Edición es un campo numérico");
		
		if( !verificarRango(edicion, 1, 99))
			throw new Exception("Edicion fuera del rango 1-99");
		
		if( edicion.length() > 2 )
			throw new Exception("Longitud incorrecta del numero de Edicion");
		
		if( !esNumerico(anioPublicacion) )
			throw new Exception("Año de publicación es un campo numérico");
		
		if( !verificarRango(anioPublicacion, 1900, 2021) )
			throw new Exception("Año de publicacion fuera del rango 1900-2021");
		
		this.isbn = isbn;
		this.titulo = titulo;
		this.autor = autor;
		this.editorial = editorial;
		this.edicion = Integer.parseInt(edicion);
		this.anioPublicacion = Integer.parseInt(anioPublicacion);
	}

	public void especificarISBN(String isbn) {
		this.isbn = isbn;
	}
	
	public void especificarTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public void especificarAutor(String autor) {
		this.autor = autor;
	}
	
	public void especificarEditorial(String editorial) {
		this.editorial = editorial;
	}
	
	public void especificarEdicion(Integer edicion) {
		this.edicion = edicion;
	}
	
	public void especificarAnioPublicacion(Integer anioPublicacion) {
		this.anioPublicacion = anioPublicacion;
	}
	
	public String obtenerISBN() {
		return isbn;
	}
	
	public String obtenerTitulo() {
		return titulo;
	}

	public String obtenerAutor() {
		return autor;
	}
	
	public String obtenerEditorial() {
		return editorial;
	}
	
	public Integer obtenerEdicion() {
		return edicion;
	}
	
	public Integer obtenerAnioPublicacion() {
		return anioPublicacion;
	}

	@Override
	public int compareTo(Libro otro) {
		return Long.valueOf(this.isbn).intValue() - Long.valueOf(otro.isbn).intValue();
	}
	
	@Override
	public int compare(Libro uno, Libro dos) {
		return uno.compareTo(dos);
	}

	public String[] enFormatoFila() {
		return new String[]{obtenerISBN(),obtenerTitulo(),obtenerAutor(),obtenerEditorial(),String.valueOf(obtenerEdicion()),String.valueOf(obtenerAnioPublicacion())};
	}
	
	public String enFormatoRegistro(String separado) {
		return new String(isbn + separado + titulo + separado + autor + separado + editorial + separado + edicion + separado + anioPublicacion + "\n");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn)) 
			return false;
		return true;
	}

	private boolean esNumerico(String texto) {
		return Pattern.matches("^[0-9]+$", texto);
	}
	
	private boolean esAlfabetico(String texto) {
		return Pattern.matches("^[a-zA-ZáéíóúÁÉÍÓÚ][a-zA-Z -.,áéíóúÁÉÍÓÚ]*$", texto);
	}
	
	private boolean esAlfanumerico(String texto) {
		return Pattern.matches("[a-zA-Z0-9áéíóúÁÉÍÓÚ][a-zA-Z0-9 ,áéíóúÁÉÍÓÚ]*$", texto);
	}
	
	
	private boolean verificarRango(String texto, Integer min, Integer max) {
		Integer numero = Integer.parseInt(texto);
		
		return min <= numero && numero <= max;
	}
}