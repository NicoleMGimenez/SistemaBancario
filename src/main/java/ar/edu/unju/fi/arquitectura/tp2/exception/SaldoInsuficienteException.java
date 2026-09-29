package ar.edu.unju.fi.arquitectura.tp2.exception;

public class SaldoInsuficienteException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }

}
