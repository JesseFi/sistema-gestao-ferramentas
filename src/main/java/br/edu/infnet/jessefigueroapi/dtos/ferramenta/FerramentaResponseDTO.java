package br.edu.infnet.jessefigueroapi.dtos.ferramenta;

import br.edu.infnet.jessefigueroapi.model.domain.Ferramenta;
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
public class FerramentaResponseDTO {

    private Integer id;
    private String nome;
    private String tipo;
    private String fabricante;
    private Double preco;
    private String numeroSerie;
    private LocalDateTime dataAquisicao;
    private Boolean disponivel;
    private String descricao;

    public FerramentaResponseDTO(Ferramenta ferramenta) {
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

}
