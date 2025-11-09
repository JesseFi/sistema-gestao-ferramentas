package br.edu.infnet.jessefigueroapi.model.service;

import br.edu.infnet.jessefigueroapi.dtos.ferramenta.FerramentaRequestDTO;
import br.edu.infnet.jessefigueroapi.dtos.ferramenta.FerramentaResponseDTO;

import java.util.List;

public interface FerramentaService {

    FerramentaResponseDTO insert(FerramentaRequestDTO ferramenta);
    void delete(Integer id);
    List<FerramentaResponseDTO> findAll();
    FerramentaResponseDTO findById(Integer id);
    FerramentaResponseDTO update(Integer id, FerramentaRequestDTO ferramenta);
}