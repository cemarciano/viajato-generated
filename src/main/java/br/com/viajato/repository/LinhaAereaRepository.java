package br.com.viajato.repository;

import br.com.viajato.domain.LinhaAerea;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LinhaAerea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LinhaAereaRepository extends JpaRepository<LinhaAerea, Long> {

}
