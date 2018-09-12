package br.com.viajato.repository;

import br.com.viajato.domain.Locadora;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Locadora entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocadoraRepository extends JpaRepository<Locadora, Long> {

}
