package br.edu.infnet.jessefigueroapi.model.domain;

import br.edu.infnet.jessefigueroapi.model.enums.StatusFuncionario;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusFuncionario status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @Override
    public String toString() {
        return String.format(
                "%s | Matrícula: %-50s | Departamento: %-50s | Cargo: %s | Data de admissão: %s | Status: %s ",
                super.toString(),
                matricula,
                departamento,
                cargo,
                dataAdmissao,
                status
        );
    }
}