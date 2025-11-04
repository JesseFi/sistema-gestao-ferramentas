package br.edu.infnet.jessefigueroapi.model.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String email;
    private String cpf;
    private String telefone;

    @Override
    public String toString() {
        return String.format(
                "%5d | Nome: %-50s | Email: %-50s | CPF: %s | Telefone: %s",
                id,
                nome,
                email,
                cpf,
                telefone
        );
    }
}
