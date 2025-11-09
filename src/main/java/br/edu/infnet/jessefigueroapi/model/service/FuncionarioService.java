package br.edu.infnet.jessefigueroapi.model.service;

import br.edu.infnet.jessefigueroapi.dtos.funcionario.FuncionarioRequestDTO;
import br.edu.infnet.jessefigueroapi.dtos.funcionario.FuncionarioResponseDTO;
import br.edu.infnet.jessefigueroapi.model.enums.StatusFuncionario;

import java.util.List;

public interface FuncionarioService {

    FuncionarioResponseDTO insert(FuncionarioRequestDTO funcionarioRequestDTO);
    void delete(Integer id);
    List<FuncionarioResponseDTO> findAll();
    FuncionarioResponseDTO findById(Integer id);
    FuncionarioResponseDTO update(Integer id, FuncionarioRequestDTO funcionarioRequestDTO);
    FuncionarioResponseDTO alterarStatus(Integer id, StatusFuncionario status);

}
