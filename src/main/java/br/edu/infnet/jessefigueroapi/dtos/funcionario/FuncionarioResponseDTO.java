package br.edu.infnet.jessefigueroapi.dtos.funcionario;

import br.edu.infnet.jessefigueroapi.model.domain.Funcionario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FuncionarioResponseDTO {

    private Integer id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;

    private String matricula;
    private String departamento;
    private String cargo;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAdmissao;

    private Boolean ativo;

    public FuncionarioResponseDTO(Funcionario funcionario) {
        this.id = funcionario.getId();
        this.nome = funcionario.getNome();
        this.cpf = funcionario.getCpf();
        this.email = funcionario.getEmail();
        this.telefone = funcionario.getTelefone();
        this.matricula = funcionario.getMatricula();
        this.departamento = funcionario.getDepartamento();
        this.cargo = funcionario.getCargo();
        this.dataAdmissao = funcionario.getDataAdmissao();
        this.ativo = funcionario.getAtivo();
    }
}