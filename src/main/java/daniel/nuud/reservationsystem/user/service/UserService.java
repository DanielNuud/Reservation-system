package daniel.nuud.reservationsystem.user.service;

import daniel.nuud.reservationsystem.user.dto.UserDTO;
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

    public UserDTO createUser(UserDTO userDTO) {
        var user = userMapper.toEntity(userDTO);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        var users = userRepository.findAll();
        var result = users.stream().map(user -> userMapper.toDTO(user))
                .toList();
        return result;
    }

}
