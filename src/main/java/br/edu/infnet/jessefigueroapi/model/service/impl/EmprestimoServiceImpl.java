package br.edu.infnet.jessefigueroapi.model.service.impl;

import br.edu.infnet.jessefigueroapi.dtos.emprestimo.EmprestimoRequestDTO;
import br.edu.infnet.jessefigueroapi.dtos.emprestimo.EmprestimoResponseDTO;
import br.edu.infnet.jessefigueroapi.dtos.ferramenta.FerramentaResponseDTO;
import br.edu.infnet.jessefigueroapi.dtos.funcionario.FuncionarioResponseDTO;
import br.edu.infnet.jessefigueroapi.exceptions.*;
import br.edu.infnet.jessefigueroapi.model.domain.Emprestimo;
import br.edu.infnet.jessefigueroapi.model.domain.Ferramenta;
import br.edu.infnet.jessefigueroapi.model.domain.Funcionario;
import br.edu.infnet.jessefigueroapi.model.enums.StatusEmprestimo;
import br.edu.infnet.jessefigueroapi.model.repository.EmprestimoRepository;
import br.edu.infnet.jessefigueroapi.model.service.EmprestimoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmprestimoServiceImpl implements EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final FerramentaServiceImpl ferramentaService;
    private final FuncionarioServiceImpl funcionarioService;


    public EmprestimoServiceImpl(EmprestimoRepository emprestimoRepository, FuncionarioServiceImpl funcionarioService, FerramentaServiceImpl ferramentaService) {
        this.emprestimoRepository = emprestimoRepository;
        this.funcionarioService = funcionarioService;
        this.ferramentaService = ferramentaService;
    }

    @Override
    public EmprestimoResponseDTO insert(EmprestimoRequestDTO emprestimo) {

        validarFerramenta(emprestimo.getFerramentaId());
        validarFuncionario(emprestimo.getFuncionarioId());

        emprestimo.setDataEmprestimo(LocalDateTime.now());
        emprestimo.setStatus(StatusEmprestimo.ATIVO);

        if (emprestimo.getDataDevolucaoPrevista() == null) {
            emprestimo.setDataDevolucaoPrevista(LocalDateTime.now().plusDays(7));
        }

        Ferramenta ferramenta = ferramentaService.findEntityById(emprestimo.getFerramentaId());
        Funcionario funcionario = funcionarioService.findEntityById(emprestimo.getFuncionarioId());

        Emprestimo emprestimoEntity = emprestimo.toEntity();
        emprestimoEntity.setFerramenta(ferramenta);
        emprestimoEntity.setFuncionario(funcionario);

        Emprestimo saved = emprestimoRepository.save(emprestimoEntity);
        return new EmprestimoResponseDTO(saved);
    }

    @Override
    public void delete(Integer id) {

        Emprestimo emprestimo = emprestimoRepository.findById(id).orElseThrow(() -> new EmprestimoNotFoundException("Empréstimo não encontrado com ID: " + id));

        if (emprestimo.isAtivo()) {
            throw new IllegalStateException("Não é possível excluir um empréstimo ativo. Realize a devolução primeiro.");
        }
        emprestimoRepository.deleteById(id);
    }

    @Override
    public List<EmprestimoResponseDTO> findAll() {
        return emprestimoRepository.findAll().stream().map(EmprestimoResponseDTO::new).toList();
    }

    @Override
    public EmprestimoResponseDTO findById(Integer id) {
        Emprestimo emprestimo = findEntityById(id);
        return new EmprestimoResponseDTO(emprestimo);
    }

    @Override
    public Emprestimo findEntityById(Integer id) {
        return emprestimoRepository.findById(id).orElseThrow(() -> new EmprestimoNotFoundException("Emprestimo não encontrado: " + id));
    }

    @Override
    public EmprestimoResponseDTO update(Integer id, EmprestimoRequestDTO emprestimo) {

        Emprestimo emprestimoDB = emprestimoRepository.findById(id)
                .orElseThrow(() -> new EmprestimoNotFoundException("Emprestimo não encontrado com ID: " + id));

        if (emprestimoDB.isConcluido()) {
            throw new EmprestimoConcluidoException("Não é possível alterar um empréstimo já concluído!");
        }

        if (StatusEmprestimo.CANCELADO.equals(emprestimo.getStatus())) {
            throw new EmprestimoCanceladoException("Não é possível alterar emprestimo cancelado!");
        }

        validarAtualizacao(emprestimoDB, emprestimo);

        emprestimoDB.setDataDevolucaoPrevista(emprestimo.getDataDevolucaoPrevista());
        emprestimoDB.setQuantidade(emprestimo.getQuantidade());
        emprestimoDB.setObservacoes(emprestimo.getObservacoes());

        Emprestimo saved = emprestimoRepository.save(emprestimoDB);
        return new EmprestimoResponseDTO(saved);
    }

    public void validarFerramenta(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID é obrigatória!");
        }

        FerramentaResponseDTO ferramentaDB = ferramentaService.findById(id);
        if (ferramentaDB == null) {
            throw new FerramentaNotFoundException("Ferramenta não encontrada com ID: " + ferramentaDB.getId());
        }

        if (!ferramentaDB.getDisponivel()) {
            throw new FerramentaIndisponivelException("Ferramenta '" + ferramentaDB.getNome() + "' não está disponível");
        }
    }

    public void validarFuncionario(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Funcionário é obrigatório!");
        }

        FuncionarioResponseDTO funcionarioDB = funcionarioService.findById(id);
        if (funcionarioDB == null) {
            throw new FuncionarioNotFoundException("Funcionário não encontrado com ID: " + id);
        }

        if (!funcionarioDB.getAtivo()) {
            throw new FuncionarioInativoException("Funcionário '" + funcionarioDB.getNome() + "' não está ativo");
        }
    }

    private void validarAtualizacao(Emprestimo existente, EmprestimoRequestDTO atualizacao) {
        if (atualizacao.getFerramentaId() != null && !atualizacao.getFerramentaId().equals(existente.getFerramenta().getId())) {
            throw new AlteracaoNaoPermitidaException("Não é possível alterar a ferramenta de um empréstimo");
        }

        if (atualizacao.getFuncionarioId() != null && !atualizacao.getFuncionarioId().equals(existente.getFuncionario().getId())) {
            throw new AlteracaoNaoPermitidaException("Não é possível alterar o funcionário de um empréstimo");
        }

        if (atualizacao.getDataDevolucaoPrevista() != null) {
            if (atualizacao.getDataDevolucaoPrevista().isBefore(existente.getDataEmprestimo())) {
                throw new DataInvalidaException("Data de devolução prevista não pode ser anterior à data de empréstimo");
            }
        }
    }
}
