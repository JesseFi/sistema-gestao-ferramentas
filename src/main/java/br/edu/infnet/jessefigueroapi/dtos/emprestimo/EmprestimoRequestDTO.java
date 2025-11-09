package br.edu.infnet.jessefigueroapi.dtos.emprestimo;

import br.edu.infnet.jessefigueroapi.model.enums.StatusEmprestimo;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EmprestimoRequestDTO {

    private Integer id;

    @NotNull(message = "O ID da ferramenta é obrigatório.")
    private Integer ferramentaId;

    @NotNull(message = "O ID do funcionário é obrigatório.")
    private Integer funcionarioId;

    @NotNull(message = "A data de empréstimo é obrigatória.")
    private LocalDateTime dataEmprestimo;

    @NotNull(message = "A data de devolução prevista é obrigatória.")
    private LocalDateTime dataDevolucaoPrevista;

    @NotNull(message = "A data de devolução real é obrigatória.")
    @FutureOrPresent(message = "A data de devolução prevista deve ser igual ou posterior à data atual.")
    private LocalDateTime dataDevolucaoReal;

    @NotNull(message = "O status do empréstimo é obrigatório.")
    private StatusEmprestimo status;

    @Size(max = 255, message = "As observações podem ter no máximo 255 caracteres.")
    private String observacoes;

    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade deve ser maior que zero.")
    @Min(1)
    private Integer quantidade;

}
