package daniel.nuud.reservationsystem.user.service;

import daniel.nuud.reservationsystem.user.dto.UserCreateDTO;
import daniel.nuud.reservationsystem.user.dto.UserDTO;
import daniel.nuud.reservationsystem.user.dto.UserUpdateDTO;
import daniel.nuud.reservationsystem.user.mapper.UserMapper;
import daniel.nuud.reservationsystem.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        var user = userMapper.toEntity(userCreateDTO);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        var users = userRepository.findAll();
        var result = users.stream().map(user -> userMapper.toDTO(user))
                .toList();
        return result;
    }

    public UserDTO updateUser(UserUpdateDTO userUpdateDTO, Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id " + userId + "not found"));
        userMapper.updateUser(user, userUpdateDTO);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public void deleteUser(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(String.format("User with %d id not found", userId)));
        userRepository.deleteById(user.getId());
    }

}
