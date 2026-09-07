package ar.edu.unju.fi.arquitectura.tp2.repository;

import ar.edu.unju.fi.arquitectura.tp2.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Búsqueda exacta por cuil
    Optional<Cliente> findByCuil(String cuil);

    // Búsqueda flexible por nombre
    List<Cliente> findByNombreRazonSocialContainingIgnoreCase(String nombre);
}