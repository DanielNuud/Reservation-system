package daniel.nuud.reservationsystem.user.service;

import daniel.nuud.reservationsystem.user.mapper.UserMapper;
import daniel.nuud.reservationsystem.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;



}
