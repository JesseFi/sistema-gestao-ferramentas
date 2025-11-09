package br.edu.infnet.jessefigueroapi.model.service.impl;

import br.edu.infnet.jessefigueroapi.dtos.emprestimo.EmprestimoRequestDTO;
import br.edu.infnet.jessefigueroapi.dtos.emprestimo.EmprestimoResponseDTO;
import br.edu.infnet.jessefigueroapi.exceptions.*;
import br.edu.infnet.jessefigueroapi.mapper.EmprestimoMapper;
import br.edu.infnet.jessefigueroapi.model.domain.Emprestimo;
import br.edu.infnet.jessefigueroapi.model.domain.Ferramenta;
import br.edu.infnet.jessefigueroapi.model.domain.Funcionario;
import br.edu.infnet.jessefigueroapi.model.enums.StatusEmprestimo;
import br.edu.infnet.jessefigueroapi.model.enums.StatusFuncionario;
import br.edu.infnet.jessefigueroapi.model.repository.EmprestimoRepository;
import br.edu.infnet.jessefigueroapi.model.repository.FerramentaRepository;
import br.edu.infnet.jessefigueroapi.model.repository.FuncionarioRepository;
import br.edu.infnet.jessefigueroapi.model.service.EmprestimoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmprestimoServiceImpl implements EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final FerramentaRepository ferramentaRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final EmprestimoMapper emprestimoMapper;

    public EmprestimoServiceImpl(EmprestimoRepository emprestimoRepository, FuncionarioRepository funcionarioRepository, FerramentaRepository ferramentaRepository, EmprestimoMapper emprestimoMapper) {
        this.emprestimoRepository = emprestimoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.ferramentaRepository = ferramentaRepository;
        this.emprestimoMapper = emprestimoMapper;
    }

//    @Transactional
//    @Override
//    public EmprestimoResponseDTO insert(EmprestimoRequestDTO emprestimoRequestDTO) {
//
//        validarFerramenta(emprestimoRequestDTO.getFerramentaId());
//        validarFuncionario(emprestimoRequestDTO.getFuncionarioId());
//
//        emprestimoRequestDTO.setDataEmprestimo(LocalDateTime.now());
//        emprestimoRequestDTO.setStatus(StatusEmprestimo.ATIVO);
//
//        if (emprestimoRequestDTO.getDataDevolucaoPrevista() == null) {
//            emprestimoRequestDTO.setDataDevolucaoPrevista(LocalDateTime.now().plusDays(7));
//        }
//
//        Ferramenta ferramenta = ferramentaRepository.findById(emprestimoRequestDTO.getFerramentaId())
//                .orElseThrow(() -> new FerramentaNotFoundException("Ferramenta com ID: " + emprestimoRequestDTO.getFerramentaId() + "não encontrada!"));
//
//        ferramenta.setDisponivel(false);
//
//        Funcionario funcionario = funcionarioRepository.findById(emprestimoRequestDTO.getFuncionarioId())
//                .orElseThrow(() -> new FuncionarioNotFoundException("Funcionário com ID: " + emprestimoRequestDTO.getFuncionarioId() + "não encontrado!"));
//
//        Emprestimo emprestimo = emprestimoMapper.toEntity(emprestimoRequestDTO);
//        emprestimo.setFerramenta(ferramenta);
//        emprestimo.setFuncionario(funcionario);
//
//        return emprestimoMapper.toDto(emprestimoRepository.save(emprestimo));
//    }

    @Override
    @Transactional
    public EmprestimoResponseDTO insert(EmprestimoRequestDTO emprestimoRequestDTO) {

        Ferramenta ferramenta = ferramentaRepository.findById(emprestimoRequestDTO.getFerramentaId())
                .orElseThrow(() -> new FerramentaNotFoundException("Ferramenta com ID: '" + emprestimoRequestDTO.getFerramentaId() + "' não encontrada!"));

        if (!ferramenta.getDisponivel()) {
            throw new IllegalStateException("Ferramenta '" + ferramenta.getNome() + "' não está disponível para empréstimo!");
        }

        Funcionario funcionario = funcionarioRepository.findById(emprestimoRequestDTO.getFuncionarioId())
                .orElseThrow(() -> new FuncionarioNotFoundException("Funcionário com ID: '" + emprestimoRequestDTO.getFuncionarioId() + "' não encontrado!"));

        if (funcionario.getStatus() != StatusFuncionario.ATIVO) {
            throw new IllegalStateException("Funcionário '" + funcionario.getNome() + "' não está ativo!");
        }

        emprestimoRequestDTO.setDataEmprestimo(LocalDateTime.now());
        emprestimoRequestDTO.setStatus(StatusEmprestimo.ATIVO);

        if (emprestimoRequestDTO.getDataDevolucaoPrevista() == null) {
            emprestimoRequestDTO.setDataDevolucaoPrevista(LocalDateTime.now().plusDays(7));
        }

        ferramenta.setDisponivel(false);
        ferramentaRepository.save(ferramenta);

        Emprestimo emprestimo = emprestimoMapper.toEntity(emprestimoRequestDTO);
        emprestimo.setFerramenta(ferramenta);
        emprestimo.setFuncionario(funcionario);

        return emprestimoMapper.toDto(emprestimoRepository.save(emprestimo));
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id).orElseThrow(() -> new EmprestimoNotFoundException("Empréstimo não encontrado com ID: '" + id + "'!"));

        if (emprestimo.isAtivo()) {
            throw new IllegalStateException("Não é possível excluir um empréstimo ativo. Realize a devolução primeiro.");
        }
        emprestimoRepository.deleteById(id);
    }

    @Override
    public List<EmprestimoResponseDTO> findAll() {
        return emprestimoRepository.findAll().stream().map(emprestimoMapper::toDto).toList();
    }

    @Override
    public EmprestimoResponseDTO findById(Integer id) {
        return emprestimoMapper.toDto(emprestimoRepository.findById(id)
                .orElseThrow(() -> new EmprestimoNotFoundException("Emprestimo com ID: '" + id + "' não encontrado!")));
    }

    @Transactional
    @Override
    public EmprestimoResponseDTO update(Integer id, EmprestimoRequestDTO emprestimoRequestDTO) {

        Emprestimo emprestimoDB = emprestimoRepository.findById(id).orElseThrow(() -> new EmprestimoNotFoundException("Emprestimo não encontrado com ID: '" + id +"'!"));

        if (emprestimoDB.isConcluido()) {
            throw new EmprestimoConcluidoException("Não é possível alterar um empréstimo já concluído!");
        }

        if (emprestimoDB.getStatus() == StatusEmprestimo.CANCELADO) {
            throw new EmprestimoCanceladoException("Não é possível alterar empréstimo cancelado!");
        }

        validarAtualizacao(emprestimoDB, emprestimoRequestDTO);

        emprestimoDB.setDataDevolucaoPrevista(emprestimoRequestDTO.getDataDevolucaoPrevista());
        emprestimoDB.setQuantidade(emprestimoRequestDTO.getQuantidade());
        emprestimoDB.setObservacoes(emprestimoRequestDTO.getObservacoes());

        return emprestimoMapper.toDto(emprestimoRepository.save(emprestimoDB));
    }

    @Transactional
    public EmprestimoResponseDTO devolverFerramenta(Integer emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId).orElseThrow(() -> new EmprestimoNotFoundException("Empréstimo não encontrado com ID: '" + emprestimoId + "'!"));

        if (!emprestimo.isAtivo()) {
            throw new EmprestimoAtivoException("Apenas empréstimos ativos podem ser devolvidos!");
        }

        emprestimo.setDataDevolucaoReal(LocalDateTime.now());
        emprestimo.setStatus(StatusEmprestimo.CONCLUIDO);

        Ferramenta ferramenta = emprestimo.getFerramenta();
        ferramenta.setDisponivel(true);
        ferramentaRepository.save(ferramenta);

        return emprestimoMapper.toDto(emprestimoRepository.save(emprestimo));
    }

    private void validarAtualizacao(Emprestimo emprestimo, EmprestimoRequestDTO emprestimoRequestDTO) {
        if (emprestimoRequestDTO.getFerramentaId() != null && !emprestimoRequestDTO.getFerramentaId().equals(emprestimo.getFerramenta().getId())) {
            throw new AlteracaoNaoPermitidaException("Não é possível alterar a ferramenta de um empréstimo!");
        }

        if (emprestimoRequestDTO.getFuncionarioId() != null && !emprestimoRequestDTO.getFuncionarioId().equals(emprestimo.getFuncionario().getId())) {
            throw new AlteracaoNaoPermitidaException("Não é possível alterar o funcionário de um empréstimo!");
        }

        if (emprestimoRequestDTO.getDataDevolucaoPrevista() != null) {
            if (emprestimoRequestDTO.getDataDevolucaoPrevista().isBefore(emprestimo.getDataEmprestimo())) {
                throw new DataInvalidaException("Data de devolução prevista não pode ser anterior à data de empréstimo");
            }
        }
    }
}
