package br.edu.infnet.jessefigueroapi.model.repository;

import br.edu.infnet.jessefigueroapi.model.domain.Emprestimo;
import br.edu.infnet.jessefigueroapi.model.domain.Ferramenta;
import br.edu.infnet.jessefigueroapi.model.enums.StatusEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer> {

    List<Emprestimo> findByFerramentaAndStatus(Ferramenta ferramenta, StatusEmprestimo status);

}
