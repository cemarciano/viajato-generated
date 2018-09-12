package br.com.viajato.repository;

import br.com.viajato.domain.Seguradora;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Seguradora entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeguradoraRepository extends JpaRepository<Seguradora, Long> {

}
