package com.example.ittalentsdominosproject.service;

import com.example.ittalentsdominosproject.exception.BadRequestException;
import com.example.ittalentsdominosproject.exception.UnauthorizedException;
import com.example.ittalentsdominosproject.model.dto.UserEditDTO;
import com.example.ittalentsdominosproject.model.dto.UserRegistrationDTO;
import com.example.ittalentsdominosproject.model.entity.User;
import com.example.ittalentsdominosproject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private InfoValidator infoValidator;
    @Autowired
    private ModelMapper modelMapper;

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (email == null || email.isBlank()) {
            throw new BadRequestException("Username is mandatory");
        }
        if (user == null) {
            throw new UnauthorizedException("Wrong credentials");
        }
        if (password == null || password.isBlank()) {
            throw new BadRequestException("Password is mandatory");
        }
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new UnauthorizedException("Wrong credentials");
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
        User user = modelMapper.map(u, User.class);
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
