package br.edu.infnet.jessefigueroapi.controllers;

import br.edu.infnet.jessefigueroapi.dtos.ferramenta.FerramentaRequestDTO;
import br.edu.infnet.jessefigueroapi.dtos.ferramenta.FerramentaResponseDTO;
import br.edu.infnet.jessefigueroapi.model.service.impl.FerramentaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ferramentas")
public class FerramentaController {

    private final FerramentaServiceImpl ferramentaService;

    public FerramentaController(FerramentaServiceImpl ferramentaService) {
        this.ferramentaService = ferramentaService;
    }

    @PostMapping
    public ResponseEntity<FerramentaResponseDTO> insert(@Valid @RequestBody FerramentaRequestDTO ferramentaRequestDTO) {
        FerramentaResponseDTO fDTO = ferramentaService.insert(ferramentaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(fDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FerramentaResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody FerramentaRequestDTO ferramentaRequestDTO) {
        FerramentaResponseDTO fDTO = ferramentaService.update(id, ferramentaRequestDTO);
        return ResponseEntity.ok(fDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FerramentaResponseDTO> delete(@PathVariable Integer id) {
        ferramentaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FerramentaResponseDTO>> findAll() {
        List<FerramentaResponseDTO> fsDTO = ferramentaService.findAll();
        return ResponseEntity.ok(fsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FerramentaResponseDTO> findById(@PathVariable Integer id) {
        FerramentaResponseDTO fsDTO = ferramentaService.findById(id);
        return ResponseEntity.ok(fsDTO);
    }

}
