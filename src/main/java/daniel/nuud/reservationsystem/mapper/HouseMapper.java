package daniel.nuud.reservationsystem.mapper;

import daniel.nuud.reservationsystem.dto.HouseCreateDTO;
import daniel.nuud.reservationsystem.dto.HouseDTO;
import daniel.nuud.reservationsystem.dto.HouseUpdateDTO;
import daniel.nuud.reservationsystem.model.HouseEntity;
import org.mapstruct.*;

@Mapper(uses = { JsonNullableMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class HouseMapper {

    public abstract HouseDTO toDTO(HouseEntity entity);
    public abstract HouseEntity toEntity(HouseCreateDTO houseCreateDTO);
    public abstract void updateHouse(HouseUpdateDTO houseUpdateDTO, @MappingTarget HouseEntity entity);
}
