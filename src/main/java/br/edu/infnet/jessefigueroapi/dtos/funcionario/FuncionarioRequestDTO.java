package br.edu.infnet.jessefigueroapi.dtos.funcionario;

import br.edu.infnet.jessefigueroapi.model.domain.Endereco;
import br.edu.infnet.jessefigueroapi.model.domain.Funcionario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
public class FuncionarioRequestDTO {

    @NotBlank(message = "ID é obrigatória")
    private Integer id;

    @NotBlank(message = "Nome é obrigatória")
    private String nome;

    @NotBlank(message = "CPF é obrigatória")
    private String cpf;

    @NotBlank(message = "E-mail é obrigatória")
    private String email;

    @NotBlank(message = "Telefone é obrigatória")
    private String telefone;

    @NotBlank(message = "Matrícula é obrigatória")
    private String matricula;

    @NotBlank(message = "Departamento é obrigatório")
    private String departamento;

    @NotBlank(message = "Cargo é obrigatório")
    private String cargo;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @PastOrPresent(message = "Data de admissão não pode ser futura")
    private LocalDate dataAdmissao;

    @NotNull(message = "Ativo não pode ser nulo!")
    private Boolean ativo;

    @NotNull(message = "Endereço é obrigatório")
    private Endereco endereco;

    public FuncionarioRequestDTO(Funcionario funcionario) {
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
        this.endereco = funcionario.getEndereco();
    }

    public Funcionario toEntity() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(this.id);
        funcionario.setNome(this.nome);
        funcionario.setCpf(this.cpf);
        funcionario.setEmail(this.email);
        funcionario.setTelefone(this.telefone);
        funcionario.setMatricula(this.matricula);
        funcionario.setDepartamento(this.departamento);
        funcionario.setCargo(this.cargo);
        funcionario.setDataAdmissao(this.dataAdmissao);
        funcionario.setAtivo(this.ativo);
        funcionario.setEndereco(this.endereco);

        return funcionario;
    }

}