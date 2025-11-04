package br.edu.infnet.jessefigueroapi.dtos.ferramenta;

import br.edu.infnet.jessefigueroapi.model.domain.Ferramenta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FerramentaRequestDTO {

    @NotBlank
    private Integer id;

    @NotBlank(message = "Nome é obrigatório!")
    private String nome;

    @NotBlank(message = "Tipo é obrigatório!")
    private String tipo;

    @NotBlank(message = "Fabricante é obrigatório")
    private String fabricante;

    @NotNull(message = "Preço deve ser maior que zero")
    private Double preco;

    @NotBlank(message = "Número de série é obrigatório")
    private String numeroSerie;

    @PastOrPresent(message = "Data de aquisição não pode ser futura")
    private LocalDateTime dataAquisicao;

    @Column(nullable = false)
    private Boolean disponivel;

    @Column(length = 500)
    private String descricao;

    public FerramentaRequestDTO(Ferramenta ferramenta) {
        this.id = ferramenta.getId();
        this.nome = ferramenta.getNome();
        this.tipo = ferramenta.getTipo();
        this.fabricante = ferramenta.getFabricante();
        this.preco = ferramenta.getPreco();
        this.numeroSerie = ferramenta.getNumeroSerie();
        this.dataAquisicao = ferramenta.getDataAquisicao();
        this.disponivel = ferramenta.getDisponivel();
        this.descricao = ferramenta.getDescricao();
    }

    public Ferramenta toEntity() {
        Ferramenta ferramenta = new Ferramenta();
        ferramenta.setId(this.id);
        ferramenta.setNome(this.nome);
        ferramenta.setTipo(this.tipo);
        ferramenta.setFabricante(this.fabricante);
        ferramenta.setPreco(this.preco);
        ferramenta.setNumeroSerie(this.numeroSerie);
        ferramenta.setDataAquisicao(this.dataAquisicao);
        ferramenta.setDisponivel(this.disponivel);
        ferramenta.setDescricao(this.descricao);

        return ferramenta;
    }
}