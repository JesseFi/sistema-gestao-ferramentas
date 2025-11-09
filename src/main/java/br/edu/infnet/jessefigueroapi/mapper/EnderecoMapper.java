package br.edu.infnet.jessefigueroapi.mapper;

import br.edu.infnet.jessefigueroapi.dtos.endereco.EnderecoResponseDTO;
import br.edu.infnet.jessefigueroapi.model.domain.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    @Mapping(target = "id",  ignore = true)
    Endereco toEntity(EnderecoResponseDTO enderecoDTO);

    EnderecoResponseDTO toDto(Endereco endereco);
}
