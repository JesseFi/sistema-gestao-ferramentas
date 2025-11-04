package br.edu.infnet.jessefigueroapi.dtos.emprestimo;

import br.edu.infnet.jessefigueroapi.dtos.funcionario.FuncionarioResponseDTO;
import br.edu.infnet.jessefigueroapi.dtos.ferramenta.FerramentaResponseDTO;
import br.edu.infnet.jessefigueroapi.model.domain.Emprestimo;
import br.edu.infnet.jessefigueroapi.model.enums.StatusEmprestimo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EmprestimoResponseDTO {

    private Integer id;
    private LocalDateTime dataEmprestimo;
    private LocalDateTime dataDevolucaoPrevista;
    private LocalDateTime dataDevolucaoReal;
    private StatusEmprestimo status;
    private String observacoes;
    private Integer quantidade;
    private FerramentaResponseDTO ferramenta;
    private FuncionarioResponseDTO funcionario;

    public EmprestimoResponseDTO(Emprestimo emprestimo) {
        this.id = emprestimo.getId();
        this.dataEmprestimo = emprestimo.getDataEmprestimo();
        this.dataDevolucaoPrevista = emprestimo.getDataDevolucaoPrevista();
        this.dataDevolucaoReal = emprestimo.getDataDevolucaoReal();
        this.status = emprestimo.getStatus();
        this.observacoes = emprestimo.getObservacoes();
        this.quantidade = emprestimo.getQuantidade();
        this.ferramenta = new FerramentaResponseDTO(emprestimo.getFerramenta());
        this.funcionario = new FuncionarioResponseDTO(emprestimo.getFuncionario());
    }
}
