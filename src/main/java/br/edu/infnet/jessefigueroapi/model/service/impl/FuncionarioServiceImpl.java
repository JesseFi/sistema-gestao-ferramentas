package br.edu.infnet.jessefigueroapi.model.service.impl;

import br.edu.infnet.jessefigueroapi.dtos.funcionario.FuncionarioRequestDTO;
import br.edu.infnet.jessefigueroapi.dtos.funcionario.FuncionarioResponseDTO;
import br.edu.infnet.jessefigueroapi.exceptions.FuncionarioNotFoundException;
import br.edu.infnet.jessefigueroapi.model.domain.Funcionario;
import br.edu.infnet.jessefigueroapi.model.repository.FuncionarioRepository;
import br.edu.infnet.jessefigueroapi.model.service.FuncionarioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioServiceImpl(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    public FuncionarioResponseDTO insert(FuncionarioRequestDTO funcionarioRequestDTO) {
        validarFuncionario(funcionarioRequestDTO);
        Funcionario saved = funcionarioRepository.save(funcionarioRequestDTO.toEntity());
        return new FuncionarioResponseDTO(saved);
    }

    @Override
    public void delete(Integer id) {
        Funcionario funcionario = findEntityById(id);
        funcionarioRepository.delete(funcionario);
    }

    @Override
    public List<FuncionarioResponseDTO> findAll() {
        List<Funcionario> funcs = funcionarioRepository.findAll();
        return funcs.stream().map(FuncionarioResponseDTO::new).toList();
    }

    @Override
    public FuncionarioResponseDTO findById(Integer id) {
        Funcionario funcionario = findEntityById(id);
        return new FuncionarioResponseDTO(funcionario);
    }

    @Override
    public Funcionario findEntityById(Integer id) {
        return funcionarioRepository.findById(id).orElseThrow(() -> new FuncionarioNotFoundException("Funcionário não encontrado: " + id));
    }

    @Override
    public FuncionarioResponseDTO update(Integer id, FuncionarioRequestDTO funcionario) {

        Funcionario func = findEntityById(id);
        validarFuncionario(funcionario);

        func.setNome(funcionario.getNome());
        func.setEmail(funcionario.getEmail());
        func.setCpf(funcionario.getCpf());
        func.setTelefone(funcionario.getTelefone());
        func.setMatricula(funcionario.getMatricula());
        func.setDepartamento(funcionario.getDepartamento());
        func.setCargo(funcionario.getCargo());
        func.setDataAdmissao(funcionario.getDataAdmissao());
        func.setAtivo(funcionario.getAtivo());
        func.setEndereco(funcionario.getEndereco());

        Funcionario saved = funcionarioRepository.save(func);

        return new FuncionarioResponseDTO(saved);
    }

    public FuncionarioResponseDTO ativar(Integer id) {
        Funcionario func = findEntityById(id);

        if (!func.getAtivo()) {
            System.err.println("O funcionário " + func.getNome() + " está desativado!");
            return new FuncionarioResponseDTO(func);
        }
        return new FuncionarioResponseDTO(func);
    }

    private void validarFuncionario(FuncionarioRequestDTO funcionarioRequestDTO) {
        if (funcionarioRequestDTO == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo");
        }

        if (funcionarioRequestDTO.getNome() == null || funcionarioRequestDTO.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do funcionário é obrigatório");
        }

        if (funcionarioRequestDTO.getEmail() == null || funcionarioRequestDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email do funcionário é obrigatório");
        }

        if (funcionarioRequestDTO.getMatricula() == null || funcionarioRequestDTO.getMatricula().trim().isEmpty()) {
            throw new IllegalArgumentException("Matrícula do funcionário é obrigatória");
        }

        if (funcionarioRequestDTO.getDepartamento() == null || funcionarioRequestDTO.getDepartamento().trim().isEmpty()) {
            throw new IllegalArgumentException("Departamento do funcionário é obrigatório");
        }

        if (funcionarioRequestDTO.getCargo() == null || funcionarioRequestDTO.getCargo().trim().isEmpty()) {
            throw new IllegalArgumentException("Cargo do funcionário é obrigatório");
        }

        if (funcionarioRequestDTO.getEndereco() == null) {
            throw new IllegalArgumentException("Endereço do funcionário é obrigatório");
        }

        if (funcionarioRequestDTO.getDataAdmissao() == null) {
            throw new IllegalArgumentException("Data de admissão do funcionário é obrigatória");
        }
    }

}
