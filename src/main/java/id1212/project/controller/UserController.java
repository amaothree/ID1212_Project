package id1212.project.controller;

import id1212.project.entity.User;
import id1212.project.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(String username, String password){
        User user = userRepository.findByUsername(username);
        if (user == null){
            return "UserNotExist";
        } else if (!user.getPassword().equals(password)){
            return "PasswordNotRight";
        } else {
            return user.toString();
        }
    }

    @PostMapping("/register")
    @ResponseBody
    public User register(String username,
                         String password,
                         String email,
                         String phone_number){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone_number(phone_number);
        return userRepository.save(user);
    }
}
