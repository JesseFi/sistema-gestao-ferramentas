package br.edu.infnet.jessefigueroapi.model.service;

import br.edu.infnet.jessefigueroapi.dtos.emprestimo.EmprestimoRequestDTO;
import br.edu.infnet.jessefigueroapi.dtos.emprestimo.EmprestimoResponseDTO;
import br.edu.infnet.jessefigueroapi.model.domain.Emprestimo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmprestimoService {

     EmprestimoResponseDTO insert(EmprestimoRequestDTO emprestimo);
     void delete(Integer id);
     List<EmprestimoResponseDTO> findAll();
     EmprestimoResponseDTO findById(Integer id);
     EmprestimoResponseDTO update (Integer id, EmprestimoRequestDTO emprestimo);
     Emprestimo findEntityById(Integer id);
}
