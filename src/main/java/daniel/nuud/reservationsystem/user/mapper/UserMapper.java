package daniel.nuud.reservationsystem.user.mapper;

import daniel.nuud.reservationsystem.user.dto.UserCreateDTO;
import daniel.nuud.reservationsystem.user.dto.UserDTO;
import daniel.nuud.reservationsystem.user.dto.UserUpdateDTO;
import daniel.nuud.reservationsystem.user.model.UserEntity;
import org.mapstruct.*;

@Mapper(uses = { UserJsonNullableMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {
    public abstract UserDTO toDTO(UserEntity user);
    public abstract UserEntity toEntity(UserCreateDTO userCreateDTO);
    public abstract void updateUser(@MappingTarget UserEntity user, UserUpdateDTO userUpdateDTO);
}
