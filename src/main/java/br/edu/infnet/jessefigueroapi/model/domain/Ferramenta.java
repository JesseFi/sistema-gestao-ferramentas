package br.edu.infnet.jessefigueroapi.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tb_ferramentas")
public class Ferramenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Nome é obrigatório!")
    @Column(length = 50, nullable = false)
    private String nome;

    @NotBlank(message = "Tipo é obrigatório!")
    @Column(nullable = false)
    private String tipo;

    @NotBlank(message = "Fabricante é obrigatório")
    @Column(nullable = false, length = 15)
    private String fabricante;

    @NotNull(message = "Preço deve ser maior que zero")
    @Column(nullable = false)
    private Double preco;

    @NotBlank(message = "Número de série é obrigatório")
    @Column(name = "numero_serie", nullable = false, unique = true)
    private String numeroSerie;

    @PastOrPresent(message = "Data de aquisição não pode ser futura")
    @Column(name = "data_aquisicao", nullable = false)
    private LocalDateTime dataAquisicao;

    @Column(nullable = false)
    private Boolean disponivel;

    @Column(length = 500)
    private String descricao;

    public Ferramenta() {
        this.dataAquisicao = LocalDateTime.now();
        this.disponivel = true;
    }

    @Override
    public String toString() {
        return String.format(
                "%3d | Nome: %-30s | Tipo: %-15s | Fabricante: %-15s | Preço: R$ %7.2f | Série: %-12s | Disponível: %-3s",
                id,
                nome,
                tipo,
                fabricante,
                preco,
                numeroSerie,
                disponivel ? "Sim" : "Não"
        );
    }
}