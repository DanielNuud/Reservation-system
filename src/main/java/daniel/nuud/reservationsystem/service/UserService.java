package daniel.nuud.reservationsystem.service;

import daniel.nuud.reservationsystem.dto.UserCreateDTO;
import daniel.nuud.reservationsystem.dto.UserDTO;
import daniel.nuud.reservationsystem.dto.UserUpdateDTO;
import daniel.nuud.reservationsystem.exception.ConflictException;
import daniel.nuud.reservationsystem.exception.ResourceNotFoundException;
import daniel.nuud.reservationsystem.mapper.UserMapper;
import daniel.nuud.reservationsystem.model.UserEntity;
import daniel.nuud.reservationsystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        if (userRepository.existsByEmail(userCreateDTO.getEmail())) {
            log.warn("Attempt to create user with duplicate email: {}", userCreateDTO.getEmail());
            throw new ConflictException(String.format("Email '%s' is already in use", userCreateDTO.getEmail()));
        }

//        if (userRepository.existsByPhoneNumber(userCreateDTO.getPhoneNumber())) {
//            log.warn("Attempt to create user with duplicate phone: {}", userCreateDTO.getPhoneNumber());
//            throw new ConflictException(
//                    String.format("Phone number '%s' is already in use", userCreateDTO.getPhoneNumber()));
//        }

        var user = userMapper.toEntity(userCreateDTO);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public UserEntity findByUserName(String userName) {
        var user = userRepository.findByEmail(userName).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with email '%s' not found", userName))
        );
        return user;
    }

    public List<UserDTO> getAllUsers() {
        var users = userRepository.findAll();
        var result = users.stream().map(user -> userMapper.toDTO(user))
                .toList();
        return result;
    }

    public UserDTO updateUser(UserUpdateDTO userUpdateDTO, Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + "not found"));

//        if (userRepository.existsByEmail(user.getEmail()) && userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
//            throw new ConflictException("Email or phone number is already in use");
//        }

        userMapper.updateUser(user, userUpdateDTO);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User with id " + userId + " not found");
        }

        userRepository.deleteById(userId);
    }

}
