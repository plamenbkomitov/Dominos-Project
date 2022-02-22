package com.example.ittalentsdominosproject.service;

import com.example.ittalentsdominosproject.exception.BadRequestException;
import com.example.ittalentsdominosproject.exception.UnauthorizedException;
import com.example.ittalentsdominosproject.model.dto.UserEditDTO;
import com.example.ittalentsdominosproject.model.dto.UserRegistrationDTO;
import com.example.ittalentsdominosproject.model.entity.User;
import com.example.ittalentsdominosproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    InfoValidator infoValidator;

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (email == null || email.isBlank()) {
            throw new BadRequestException("Username is mandatory");
        }
        if (user == null) {
            throw new UnauthorizedException("Wrong credentials");
        }
        if (password == null || password.isBlank() || !passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("Password is mandatory");
        }
        return user;
    }

    public User register(UserRegistrationDTO u) {
        if (userRepository.findByEmail(u.getEmail()) != null) {
            throw new BadRequestException("user with this email exists");
        }

        infoValidator.passwordValidate(u.getPassword());
        infoValidator.emailValidate(u.getEmail());

        if (!u.getPassword().equals(u.getConfPassword())) {
            throw new BadRequestException("Password mismatch");
        }

        infoValidator.firstNameValidate(u.getFirstName());
        infoValidator.firstNameValidate(u.getLastName());
        infoValidator.phoneValidate(u.getPhone());
//        todo use mapper
        User user = new User();
        user.setFirstName(u.getFirstName());
        user.setLastName(u.getLastName());
        user.setEmail(u.getEmail());
        user.setPhone(u.getPhone());
        user.setPassword(passwordEncoder.encode(u.getPassword()));
        return user;
    }

    public void edit(User user, UserEditDTO userEditDTO) {
        if (userEditDTO.getFirstName() != null) {
            infoValidator.firstNameValidate(userEditDTO.getFirstName());
            user.setFirstName(userEditDTO.getFirstName());
        }
        if (userEditDTO.getLastName() != null) {
            infoValidator.lastNameValidate(userEditDTO.getLastName());
            user.setLastName(userEditDTO.getLastName());
        }

        if (userEditDTO.getEmail() != null) {
            infoValidator.emailValidate(userEditDTO.getEmail());
            user.setEmail(userEditDTO.getEmail());
        }

        if (userEditDTO.getPhone() != null) {
            infoValidator.phoneValidate(userEditDTO.getPhone());
            user.setPhone(userEditDTO.getPhone());
        }

        String oldPassword = userEditDTO.getPassword();
        String newPassword = userEditDTO.getNewPassword();
        String newConfPassword = userEditDTO.getConfNewPassword();

        if (oldPassword != null) {
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                throw new BadRequestException("Password mismatch!");
            }

            if (!newPassword.equals(newConfPassword)) {
                throw new BadRequestException("Password mismatch!");
            }

            infoValidator.passwordValidate(newPassword);

            user.setPassword(passwordEncoder.encode(newPassword));
        }
    }
}
