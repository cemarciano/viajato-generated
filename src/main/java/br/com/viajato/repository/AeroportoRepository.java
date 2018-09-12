package br.com.viajato.repository;

import br.com.viajato.domain.Aeroporto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Aeroporto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AeroportoRepository extends JpaRepository<Aeroporto, Long> {

}
