package daniel.nuud.reservationsystem.order.mapper;

import daniel.nuud.reservationsystem.order.dto.OrderCreateDTO;
import daniel.nuud.reservationsystem.order.dto.OrderDTO;
import daniel.nuud.reservationsystem.order.model.OrderEntity;
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
