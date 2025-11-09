package br.edu.infnet.jessefigueroapi.mapper;

import br.edu.infnet.jessefigueroapi.dtos.ferramenta.FerramentaRequestDTO;
import br.edu.infnet.jessefigueroapi.dtos.ferramenta.FerramentaResponseDTO;
import br.edu.infnet.jessefigueroapi.model.domain.Ferramenta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FerramentaMapper {

    @Mapping(target = "id", ignore = true)
    Ferramenta toEntity(FerramentaRequestDTO ferramentaRequestDTO);

    FerramentaResponseDTO toDto(Ferramenta ferramenta);

}
