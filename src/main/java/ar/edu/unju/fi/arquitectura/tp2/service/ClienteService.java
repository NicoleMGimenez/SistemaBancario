package ar.edu.unju.fi.arquitectura.tp2.service;

import java.util.List;

import ar.edu.unju.fi.arquitectura.tp2.model.Cliente;

public interface ClienteService {
	
	Cliente crearCliente(Cliente cliente);
	Cliente obtenerPorCuil(String Cuil);
	List<Cliente> obtenerPorNombre(String Nombre);
}
