package br.edu.infnet.jessefigueroapi.controllers;

import br.edu.infnet.jessefigueroapi.dtos.funcionario.FuncionarioRequestDTO;
import br.edu.infnet.jessefigueroapi.dtos.funcionario.FuncionarioResponseDTO;
import br.edu.infnet.jessefigueroapi.model.enums.StatusFuncionario;
import br.edu.infnet.jessefigueroapi.model.service.impl.FuncionarioServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/funcionarios")
public class FuncionarioController {

    private final FuncionarioServiceImpl funcionarioService;

    public FuncionarioController(FuncionarioServiceImpl funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public ResponseEntity<FuncionarioResponseDTO> insert(@Valid @RequestBody FuncionarioRequestDTO funcionarioRequestDTO) {
        FuncionarioResponseDTO func = funcionarioService.insert(funcionarioRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(func);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> update(@Valid @PathVariable Integer id, @RequestBody FuncionarioRequestDTO funcionario) {
        FuncionarioResponseDTO func = funcionarioService.update(id, funcionario);
        return ResponseEntity.ok(func);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> delete(@PathVariable Integer id) {
        funcionarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDTO>> findAll() {
        List<FuncionarioResponseDTO> funcsDTO = funcionarioService.findAll();
        return ResponseEntity.ok(funcsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> findById(@PathVariable Integer id) {
        FuncionarioResponseDTO funcDTO = funcionarioService.findById(id);
        return ResponseEntity.ok(funcDTO);
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<FuncionarioResponseDTO> ativar(@PathVariable Integer id, @RequestParam StatusFuncionario status) {
        FuncionarioResponseDTO funcDTO = funcionarioService.alterarStatus(id, status);
        return ResponseEntity.ok(funcDTO);
    }
}
