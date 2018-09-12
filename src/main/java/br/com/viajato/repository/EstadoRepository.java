package br.com.viajato.repository;

import br.com.viajato.domain.Estado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Estado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
