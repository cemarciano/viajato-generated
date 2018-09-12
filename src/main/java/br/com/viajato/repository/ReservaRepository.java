package br.com.viajato.repository;

import br.com.viajato.domain.Reserva;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Reserva entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

}
