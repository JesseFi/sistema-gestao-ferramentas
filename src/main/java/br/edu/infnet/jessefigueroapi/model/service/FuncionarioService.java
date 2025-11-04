package br.edu.infnet.jessefigueroapi.model.service;

import br.edu.infnet.jessefigueroapi.dtos.funcionario.FuncionarioRequestDTO;
import br.edu.infnet.jessefigueroapi.dtos.funcionario.FuncionarioResponseDTO;
import br.edu.infnet.jessefigueroapi.model.domain.Funcionario;

import java.util.List;

public interface FuncionarioService {

    FuncionarioResponseDTO insert(FuncionarioRequestDTO funcionarioRequestDTO);
    void delete(Integer id);
    List<FuncionarioResponseDTO> findAll();
    FuncionarioResponseDTO findById(Integer id);
    Funcionario findEntityById(Integer id);
    FuncionarioResponseDTO update(Integer id, FuncionarioRequestDTO funcionarioRequestDTO);
    FuncionarioResponseDTO ativar(Integer id);

}
