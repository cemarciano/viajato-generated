package br.com.viajato.repository;

import br.com.viajato.domain.Veiculo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Veiculo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

}
