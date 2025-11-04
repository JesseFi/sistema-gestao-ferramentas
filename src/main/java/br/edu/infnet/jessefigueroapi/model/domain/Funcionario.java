package br.edu.infnet.jessefigueroapi.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tb_funcionarios")
public class Funcionario extends Pessoa {

    @Column(unique = true, nullable = false, length = 20)
    private String matricula;

    @Column(nullable = false, length = 50)
    private String departamento;

    @Column(nullable = false, length = 50)
    private String cargo;


    @Column(name = "data_admissao", nullable = false)
    private LocalDate dataAdmissao;

    @Column(nullable = false)
    private Boolean ativo;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

//    @Override
//    public String toString() {
//        return String.format(
//                "%s | Matrícula: %-50s | Departamento: %-50s | Cargo: %s | Data de admissão: %s | Status: %s |  Cep: %s ",
//                super.toString(),
//                matricula,
//                departamento,
//                cargo,
//                dataAdmissao,
//                ativo ? "Ativo" : "Inativo",
//                endereco != null ? endereco : "N/A"
//        );
//    }
}