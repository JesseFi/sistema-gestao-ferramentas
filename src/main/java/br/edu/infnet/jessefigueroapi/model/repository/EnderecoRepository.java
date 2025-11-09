package br.edu.infnet.jessefigueroapi.model.repository;

import br.edu.infnet.jessefigueroapi.model.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,Integer> {
}
