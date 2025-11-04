package br.edu.infnet.jessefigueroapi.model.repository;

import br.edu.infnet.jessefigueroapi.model.domain.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
}
