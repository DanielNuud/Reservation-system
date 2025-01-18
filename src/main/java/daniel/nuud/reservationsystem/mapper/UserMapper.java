package daniel.nuud.reservationsystem.mapper;

import daniel.nuud.reservationsystem.dto.UserCreateDTO;
import daniel.nuud.reservationsystem.dto.UserDTO;
import daniel.nuud.reservationsystem.dto.UserUpdateDTO;
import daniel.nuud.reservationsystem.model.UserEntity;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(uses = { JsonNullableMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeMapping
    public void encryptPassword(UserCreateDTO userCreateDTO) {
        var password = userCreateDTO.getPassword();
        userCreateDTO.setPassword(passwordEncoder.encode(password));
    }

    @BeforeMapping
    public void encryptUpdatePassword(UserUpdateDTO userUpdateDTO, @MappingTarget UserEntity userEntity) {
        var password = userUpdateDTO.getPassword();
        if (password != null && password.isPresent()) {
            userEntity.setPassword(passwordEncoder.encode(password.get()));
        }
    }

    public abstract UserDTO toDTO(UserEntity user);
    public abstract UserEntity toEntity(UserCreateDTO userCreateDTO);
    public abstract void updateUser(@MappingTarget UserEntity user, UserUpdateDTO userUpdateDTO);
}
