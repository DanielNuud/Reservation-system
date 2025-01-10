package daniel.nuud.reservationsystem.user.mapper;

import daniel.nuud.reservationsystem.user.dto.UserDTO;
import daniel.nuud.reservationsystem.user.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    UserDTO toDTO(UserEntity user);
    UserEntity toEntity(UserDTO userDTO);
}
