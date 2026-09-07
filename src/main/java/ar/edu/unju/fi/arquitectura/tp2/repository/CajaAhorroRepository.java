package ar.edu.unju.fi.arquitectura.tp2.repository;

import ar.edu.unju.fi.arquitectura.tp2.model.CajaAhorro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CajaAhorroRepository extends JpaRepository<CajaAhorro, Long> {
}
