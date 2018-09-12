package br.com.viajato.repository;

import br.com.viajato.domain.Passagem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Passagem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PassagemRepository extends JpaRepository<Passagem, Long> {

}
