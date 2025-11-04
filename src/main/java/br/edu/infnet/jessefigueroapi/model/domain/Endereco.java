package br.edu.infnet.jessefigueroapi.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_enderecos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;

    private String logradouro;

    private String bairro;

    private String localidade;

    private String uf;

    @Column(length = 50)
    private String estado;

    @Override
    public String toString() {
        return String.format(
                "Endereço [CEP: %s | Logradouro: %s | Bairro: %s | Localidade: %s | UF: %s | Estado: %s]",
                cep != null ? cep : "N/A",
                logradouro != null ? logradouro : "N/A",
                bairro != null ? bairro : "N/A",
                localidade != null ? localidade : "N/A",
                uf != null ? uf : "N/A",
                estado != null ? estado : "N/A"
        );
    }

}
