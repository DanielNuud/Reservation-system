package daniel.nuud.reservationsystem.mapper;

import daniel.nuud.reservationsystem.dto.OrderCreateDTO;
import daniel.nuud.reservationsystem.dto.OrderDTO;
import daniel.nuud.reservationsystem.model.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class OrderMapper {
    public abstract OrderDTO toDto(OrderEntity orderEntity);
    public abstract OrderEntity toEntity(OrderCreateDTO dto);
}
