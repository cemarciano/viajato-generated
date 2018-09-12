package br.com.viajato.repository;

import br.com.viajato.domain.Quarto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Quarto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {

}
