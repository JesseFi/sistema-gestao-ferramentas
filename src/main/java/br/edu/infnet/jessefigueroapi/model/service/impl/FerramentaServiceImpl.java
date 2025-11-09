package br.edu.infnet.jessefigueroapi.model.service.impl;

import br.edu.infnet.jessefigueroapi.dtos.ferramenta.FerramentaRequestDTO;
import br.edu.infnet.jessefigueroapi.dtos.ferramenta.FerramentaResponseDTO;
import br.edu.infnet.jessefigueroapi.exceptions.FerramentaNotFoundException;
import br.edu.infnet.jessefigueroapi.mapper.FerramentaMapper;
import br.edu.infnet.jessefigueroapi.model.domain.Ferramenta;
import br.edu.infnet.jessefigueroapi.model.repository.FerramentaRepository;
import br.edu.infnet.jessefigueroapi.model.service.FerramentaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FerramentaServiceImpl implements FerramentaService {

    private final FerramentaRepository ferramentaRepository;
    private final FerramentaMapper ferramentaMapper;

    public FerramentaServiceImpl(FerramentaRepository ferramentaRepository, FerramentaMapper ferramentaMapper) {
        this.ferramentaRepository = ferramentaRepository;
        this.ferramentaMapper = ferramentaMapper;
    }

    @Transactional
    @Override
    public FerramentaResponseDTO insert(FerramentaRequestDTO ferramentaRequestDTO) {

        validarFerramenta(ferramentaRequestDTO);

        Ferramenta ferramenta = ferramentaMapper.toEntity(ferramentaRequestDTO);
        return ferramentaMapper.toDto(ferramentaRepository.save(ferramenta));
    }

    @Override
    public void delete(Integer id) {
        FerramentaResponseDTO ferramenta = findById(id);
        ferramentaRepository.deleteById(ferramenta.getId());
    }

    @Override
    public List<FerramentaResponseDTO> findAll() {
        return ferramentaRepository.findAll().stream().map(ferramentaMapper::toDto).toList();
    }

    @Override
    public FerramentaResponseDTO findById(Integer id) {
        return ferramentaMapper.toDto(ferramentaRepository.findById(id)
                .orElseThrow(() -> new FerramentaNotFoundException("Ferramenta não encontrada com ID: " + id)));
    }

    @Override
    public FerramentaResponseDTO update(Integer id, FerramentaRequestDTO ferramentaRequestDTO) {

        Ferramenta ferramenta = ferramentaRepository.findById(id).orElseThrow(() -> new FerramentaNotFoundException("Funcionário não encontrado com ID: " + id + " para atualizar!"));;

        validarFerramenta(ferramentaRequestDTO);

        ferramenta.setNome(ferramentaRequestDTO.getNome());
        ferramenta.setTipo(ferramentaRequestDTO.getTipo());
        ferramenta.setFabricante(ferramentaRequestDTO.getFabricante());
        ferramenta.setPreco(ferramentaRequestDTO.getPreco());
        ferramenta.setNumeroSerie(ferramentaRequestDTO.getNumeroSerie());
        ferramenta.setDisponivel(ferramentaRequestDTO.getDisponivel());
        ferramenta.setDescricao(ferramentaRequestDTO.getDescricao());

        return ferramentaMapper.toDto(ferramentaRepository.save(ferramenta));
    }

    private void validarFerramenta(FerramentaRequestDTO ferramentaRequestDTO) {
        if (ferramentaRequestDTO == null) {
            throw new IllegalArgumentException("Ferramenta não pode ser nula");
        }

        if (ferramentaRequestDTO.getNome() == null || ferramentaRequestDTO.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da ferramentaRequestDTO é obrigatório");
        }

        if (ferramentaRequestDTO.getPreco() == null || ferramentaRequestDTO.getPreco() <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }

        if (ferramentaRequestDTO.getFabricante() == null || ferramentaRequestDTO.getFabricante().trim().isEmpty()) {
            throw new IllegalArgumentException("Fabricante é obrigatório");
        }

        if (ferramentaRequestDTO.getNumeroSerie() == null || ferramentaRequestDTO.getNumeroSerie().trim().isEmpty()) {
            throw new IllegalArgumentException("Número de série é obrigatório");
        }

        if (ferramentaRequestDTO.getTipo() == null || ferramentaRequestDTO.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo da ferramenta é obrigatório");
        }
    }
}