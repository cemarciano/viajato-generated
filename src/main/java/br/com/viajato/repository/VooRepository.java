package br.com.viajato.repository;

import br.com.viajato.domain.Voo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Voo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VooRepository extends JpaRepository<Voo, Long> {

}
