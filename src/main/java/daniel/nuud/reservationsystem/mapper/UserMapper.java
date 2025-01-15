package daniel.nuud.reservationsystem.mapper;

import daniel.nuud.reservationsystem.dto.UserCreateDTO;
import daniel.nuud.reservationsystem.dto.UserDTO;
import daniel.nuud.reservationsystem.dto.UserUpdateDTO;
import daniel.nuud.reservationsystem.model.UserEntity;
import org.mapstruct.*;

@Mapper(uses = { JsonNullableMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {
    public abstract UserDTO toDTO(UserEntity user);
    public abstract UserEntity toEntity(UserCreateDTO userCreateDTO);
    public abstract void updateUser(@MappingTarget UserEntity user, UserUpdateDTO userUpdateDTO);
}
