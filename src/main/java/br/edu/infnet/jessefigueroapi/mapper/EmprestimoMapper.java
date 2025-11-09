package br.edu.infnet.jessefigueroapi.mapper;

import br.edu.infnet.jessefigueroapi.dtos.emprestimo.EmprestimoRequestDTO;
import br.edu.infnet.jessefigueroapi.dtos.emprestimo.EmprestimoResponseDTO;
import br.edu.infnet.jessefigueroapi.model.domain.Emprestimo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmprestimoMapper {

    @Mapping(target = "ferramenta.id", source = "ferramentaId")
    @Mapping(target = "funcionario.id", source = "funcionarioId")
    Emprestimo toEntity(EmprestimoRequestDTO emprestimoRequestDTO);
    EmprestimoResponseDTO toDto(Emprestimo emprestimo);

}