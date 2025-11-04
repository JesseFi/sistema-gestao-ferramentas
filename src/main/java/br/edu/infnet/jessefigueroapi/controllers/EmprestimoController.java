package br.edu.infnet.jessefigueroapi.controllers;

import br.edu.infnet.jessefigueroapi.dtos.emprestimo.EmprestimoRequestDTO;
import br.edu.infnet.jessefigueroapi.dtos.emprestimo.EmprestimoResponseDTO;
import br.edu.infnet.jessefigueroapi.model.service.impl.EmprestimoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/emprestimos")
public class EmprestimoController {

    private final EmprestimoServiceImpl emprestimoService;

    public EmprestimoController(EmprestimoServiceImpl emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public ResponseEntity<EmprestimoResponseDTO> insert(@Valid @RequestBody EmprestimoRequestDTO emprestimo) {
        EmprestimoResponseDTO empDTO = emprestimoService.insert(emprestimo);
        return ResponseEntity.status(HttpStatus.CREATED).body(empDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmprestimoResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody EmprestimoRequestDTO emprestimo) {
        EmprestimoResponseDTO empDTO = emprestimoService.update(id, emprestimo);
        return ResponseEntity.ok(empDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmprestimoResponseDTO> delete(@PathVariable Integer id) {
        emprestimoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoResponseDTO>> findAll() {
        List<EmprestimoResponseDTO> empsDTO = emprestimoService.findAll();
        return ResponseEntity.ok(empsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoResponseDTO> findById(@Valid @PathVariable Integer id) {
        EmprestimoResponseDTO empDTO = emprestimoService.findById(id);
        return ResponseEntity.ok(empDTO);
    }

}
