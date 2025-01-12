package daniel.nuud.reservationsystem.house.mapper;

import daniel.nuud.reservationsystem.house.dto.HouseCreateDTO;
import daniel.nuud.reservationsystem.house.dto.HouseDTO;
import daniel.nuud.reservationsystem.house.dto.HouseUpdateDTO;
import daniel.nuud.reservationsystem.house.model.HouseEntity;
import org.mapstruct.*;

@Mapper(uses = { HouseJsonNullableMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class HouseMapper {

    public abstract HouseDTO toDTO(HouseEntity entity);
    public abstract HouseEntity toEntity(HouseCreateDTO houseCreateDTO);
    public abstract void updateHouse(HouseUpdateDTO houseUpdateDTO, @MappingTarget HouseEntity entity);
}
