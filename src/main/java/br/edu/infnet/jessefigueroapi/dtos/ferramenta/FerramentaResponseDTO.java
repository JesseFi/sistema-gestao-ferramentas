package br.edu.infnet.jessefigueroapi.dtos.ferramenta;

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

}
