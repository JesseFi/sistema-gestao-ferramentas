package br.edu.infnet.jessefigueroapi.model.domain;

import br.edu.infnet.jessefigueroapi.model.enums.StatusEmprestimo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tb_emprestimos")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ferramenta_id")
    private Ferramenta ferramenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @Column(name = "data_emprestimo")
    private LocalDateTime dataEmprestimo;

    @Column(name = "data_devolucao_prevista")
    private LocalDateTime dataDevolucaoPrevista;

    @Column(name = "data_devolucao_real")
    private LocalDateTime dataDevolucaoReal;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEmprestimo status;

    @Column(length = 500)
    private String observacoes;

    private Integer quantidade;


    @Override
    public String toString() {
        return String.format(
                "%3d | Ferramenta: %-25s | Funcionário: %-20s | Empréstimo: %s | Devolução: %s | Status: %-10s",
                id,
                ferramenta != null ? ferramenta.getNome() : "N/A",
                funcionario != null ? funcionario.getNome() : "N/A",
                dataEmprestimo != null ? dataEmprestimo.toLocalDate().toString() : "N/A",
                dataDevolucaoPrevista != null ? dataDevolucaoPrevista.toLocalDate().toString() : "N/A",
                status
        );
    }

    public boolean isConcluido() {
        return StatusEmprestimo.CONCLUIDO.equals(this.status);
    }

    public boolean isAtivo() {
        return StatusEmprestimo.ATIVO.equals(this.status);
    }

}
