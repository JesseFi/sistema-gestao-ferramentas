package br.edu.infnet.jessefigueroapi.model.service.impl;

import br.edu.infnet.jessefigueroapi.dtos.funcionario.FuncionarioRequestDTO;
import br.edu.infnet.jessefigueroapi.dtos.funcionario.FuncionarioResponseDTO;
import br.edu.infnet.jessefigueroapi.exceptions.FuncionarioNotFoundException;
import br.edu.infnet.jessefigueroapi.mapper.FuncionarioMapper;
import br.edu.infnet.jessefigueroapi.model.domain.Endereco;
import br.edu.infnet.jessefigueroapi.model.domain.Funcionario;
import br.edu.infnet.jessefigueroapi.model.enums.StatusFuncionario;
import br.edu.infnet.jessefigueroapi.model.repository.EnderecoRepository;
import br.edu.infnet.jessefigueroapi.model.repository.FuncionarioRepository;
import br.edu.infnet.jessefigueroapi.model.service.FuncionarioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final FuncionarioMapper funcionarioMapper;

    public FuncionarioServiceImpl(FuncionarioRepository funcionarioRepository, EnderecoRepository enderecoRepository, FuncionarioMapper funcionarioMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.enderecoRepository = enderecoRepository;
        this.funcionarioMapper = funcionarioMapper;
    }

    @Override
    public FuncionarioResponseDTO insert(FuncionarioRequestDTO funcionarioRequestDTO) {

        validarFuncionario(funcionarioRequestDTO);

        Endereco endereco = enderecoRepository.findById(funcionarioRequestDTO.getEnderecoId())
                .orElseThrow(() -> new IllegalArgumentException("Endereço com ID " + funcionarioRequestDTO.getEnderecoId() + " não foi encontrado!"));

        Funcionario funcionario = funcionarioMapper.toEntity(funcionarioRequestDTO);
        funcionario.setEndereco(endereco);
        return funcionarioMapper.toDto(funcionarioRepository.save(funcionario));
    }

    @Override
    public void delete(Integer id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new FuncionarioNotFoundException("Funcionário não encontrado com ID: " + id + " para exclusão!");
        }
        funcionarioRepository.deleteById(id);
    }

    @Override
    public List<FuncionarioResponseDTO> findAll() {
        return funcionarioRepository.findAll().stream().map(funcionarioMapper::toDto).toList();
    }

    @Override
    public FuncionarioResponseDTO findById(Integer id) {
        return funcionarioMapper.toDto(funcionarioRepository.findById(id).orElseThrow(() ->
                new FuncionarioNotFoundException("Funcionário não encontrado com ID: " + id)));
    }

    @Override
    public FuncionarioResponseDTO update(Integer id, FuncionarioRequestDTO funcionarioRequestDTO) {

        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(() -> new FuncionarioNotFoundException("Funcionário não encontrado com ID: " + id + " para atualizar!"));

        validarFuncionario(funcionarioRequestDTO);

        funcionario.setNome(funcionarioRequestDTO.getNome());
        funcionario.setEmail(funcionarioRequestDTO.getEmail());
        funcionario.setCpf(funcionarioRequestDTO.getCpf());
        funcionario.setTelefone(funcionarioRequestDTO.getTelefone());
        funcionario.setMatricula(funcionarioRequestDTO.getMatricula());
        funcionario.setDepartamento(funcionarioRequestDTO.getDepartamento());
        funcionario.setCargo(funcionarioRequestDTO.getCargo());
        funcionario.setDataAdmissao(funcionarioRequestDTO.getDataAdmissao());
        funcionario.setStatus(funcionarioRequestDTO.getStatus());

        Endereco endereco = enderecoRepository.findById(funcionarioRequestDTO.getEnderecoId())
                .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado: " + funcionarioRequestDTO.getEnderecoId()));
        funcionario.setEndereco(endereco);

        return funcionarioMapper.toDto(funcionarioRepository.save(funcionario));
    }

    public FuncionarioResponseDTO alterarStatus(Integer id, StatusFuncionario status) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(() -> new FuncionarioNotFoundException("Funcionário não encontrado com ID: " + id + " para ativar!"));

        if (funcionario.getStatus().equals(status)) {
            throw new IllegalArgumentException("Funcionário " + funcionario.getNome() + " já está ativo(a)!");
        }

        funcionario.setStatus(status);
        Funcionario saved = funcionarioRepository.save(funcionario);
        return funcionarioMapper.toDto(saved);
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

        if (funcionarioRequestDTO.getEnderecoId() == null || funcionarioRequestDTO.getEnderecoId() == 0) {
            throw new IllegalArgumentException("Endereço do funcionário é obrigatório");
        }

        if (funcionarioRequestDTO.getDataAdmissao() == null) {
            throw new IllegalArgumentException("Data de admissão do funcionário é obrigatória");
        }
    }

}
