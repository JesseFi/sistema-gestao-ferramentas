package br.edu.infnet.jessefigueroapi.model.service.impl;

import br.edu.infnet.jessefigueroapi.dtos.ferramenta.FerramentaRequestDTO;
import br.edu.infnet.jessefigueroapi.dtos.ferramenta.FerramentaResponseDTO;
import br.edu.infnet.jessefigueroapi.exceptions.FuncionarioNotFoundException;
import br.edu.infnet.jessefigueroapi.model.domain.Ferramenta;
import br.edu.infnet.jessefigueroapi.model.repository.FerramentaRepository;
import br.edu.infnet.jessefigueroapi.model.service.FerramentaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FerramentaServiceImpl implements FerramentaService {


    private final FerramentaRepository ferramentaRepository;

    public FerramentaServiceImpl(FerramentaRepository ferramentaRepository) {
        this.ferramentaRepository = ferramentaRepository;
    }

    @Override
    public FerramentaResponseDTO insert(FerramentaRequestDTO ferramentaRequestDTO) {
        validarFerramenta(ferramentaRequestDTO);
        Ferramenta savedFerramenta = ferramentaRepository.save(ferramentaRequestDTO.toEntity());
        return new FerramentaResponseDTO(savedFerramenta);
    }

    @Override
    public void delete(Integer id) {
        FerramentaResponseDTO ferramenta = findById(id);
        ferramentaRepository.deleteById(ferramenta.getId());
    }

    @Override
    public List<FerramentaResponseDTO> findAll() {
        List<Ferramenta> funcs = ferramentaRepository.findAll();
        return funcs.stream().map(FerramentaResponseDTO::new).toList();
    }

    @Override
    public FerramentaResponseDTO findById(Integer id) {
        Ferramenta ferramenta = findEntityById(id);
        return new FerramentaResponseDTO(ferramenta);
    }

    @Override
    public Ferramenta findEntityById(Integer id) {
        return ferramentaRepository.findById(id).orElseThrow(() -> new FuncionarioNotFoundException("Funcionário não encontrado: " + id));
    }

    @Override
    public FerramentaResponseDTO update(Integer id, FerramentaRequestDTO ferramenta) {

        Ferramenta f = findEntityById(id);
        validarFerramenta(new FerramentaRequestDTO(f));

        f.setNome(ferramenta.getNome());
        f.setPreco(ferramenta.getPreco());
        f.setFabricante(ferramenta.getFabricante());
        f.setDescricao(ferramenta.getDescricao());
        f.setDisponivel(ferramenta.getDisponivel());
        f.setTipo(ferramenta.getTipo());
        f.setNumeroSerie(ferramenta.getNumeroSerie());

        Ferramenta savedFerramenta = ferramentaRepository.save(f);
        return new FerramentaResponseDTO(savedFerramenta);
    }

    private void validarFerramenta(FerramentaRequestDTO ferramenta) {
        if (ferramenta == null) {
            throw new IllegalArgumentException("Ferramenta não pode ser nula");
        }

        if (ferramenta.getNome() == null || ferramenta.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da ferramenta é obrigatório");
        }

        if (ferramenta.getPreco() == null || ferramenta.getPreco() <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }

        if (ferramenta.getFabricante() == null || ferramenta.getFabricante().trim().isEmpty()) {
            throw new IllegalArgumentException("Fabricante é obrigatório");
        }

        if (ferramenta.getNumeroSerie() == null || ferramenta.getNumeroSerie().trim().isEmpty()) {
            throw new IllegalArgumentException("Número de série é obrigatório");
        }

        if (ferramenta.getTipo() == null || ferramenta.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo da ferramenta é obrigatório");
        }
    }
}