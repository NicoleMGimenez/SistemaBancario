package ar.edu.unju.fi.arquitectura.tp2.exception;

public class RecursoNoEncontradoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
	
}
