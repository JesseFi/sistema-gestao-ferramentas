package br.edu.infnet.jessefigueroapi.model.repository;

import br.edu.infnet.jessefigueroapi.model.domain.Ferramenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FerramentaRepository extends JpaRepository<Ferramenta, Integer> {

}
