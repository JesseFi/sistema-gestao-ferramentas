package br.edu.infnet.jessefigueroapi.dtos.ferramenta;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FerramentaRequestDTO {

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

}