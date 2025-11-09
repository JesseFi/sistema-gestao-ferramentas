package br.edu.infnet.jessefigueroapi.dtos.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoRequestDTO {

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP deve estar no formato 00000-000 ou 00000000")
    private String cep;

    @NotBlank(message = "Logradouro é obrigatório")
    @Size(max = 255, message = "Logradouro deve ter no máximo 255 caracteres")
    private String logradouro;

    @NotBlank(message = "Bairro é obrigatório")
    @Size(max = 255, message = "Bairro deve ter no máximo 255 caracteres")
    private String bairro;

    @NotBlank(message = "Localidade é obrigatória")
    @Size(max = 255, message = "Localidade deve ter no máximo 255 caracteres")
    private String localidade;

    @NotBlank(message = "UF é obrigatória")
    @Pattern(regexp = "[A-Z]{2}", message = "UF deve conter exatamente 2 letras maiúsculas")
    private String uf;

    @NotBlank(message = "Estado é obrigatório")
    @Size(max = 50, message = "Estado deve ter no máximo 50 caracteres")
    private String estado;

    @NotBlank(message = "Região é obrigatório")
    @Size(max = 50, message = "Região deve ter no máximo 50 caracteres")
    private String regiao;
}