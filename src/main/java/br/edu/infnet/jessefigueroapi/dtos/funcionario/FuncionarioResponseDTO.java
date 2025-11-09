package br.edu.infnet.jessefigueroapi.dtos.funcionario;

import br.edu.infnet.jessefigueroapi.dtos.endereco.EnderecoResponseDTO;
import br.edu.infnet.jessefigueroapi.model.enums.StatusFuncionario;
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
    private LocalDate dataAdmissao;

    private StatusFuncionario status;

    private EnderecoResponseDTO endereco;

}