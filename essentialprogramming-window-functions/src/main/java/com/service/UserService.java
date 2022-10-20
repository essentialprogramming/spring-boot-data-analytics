package com.service;

import com.base.persistence.entities.User;
import com.base.persistence.repository.UserRepository;
import com.mapper.UserMapper;
import com.model.UserInput;
import com.output.UserJSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserJSON createUser(UserInput input) {

        boolean isValid = checkAvailabilityByEmail(input.getEmail());
        if (!isValid)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "EMAIL.ALREADY.TAKEN");

        final User user = UserMapper.inputToUser(input);
        final User result = saveUser(user);

        return UserMapper.userToJson(result);

    }

    @Transactional
    public boolean checkAvailabilityByEmail(String email) {

        Optional<User> user = userRepository.findByEmail(email);
        return !user.isPresent();
    }

    @Transactional
    public boolean checkEmailExists(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }


    @Transactional
    public UserJSON loadUser(String email) {

        final User user = userRepository.findByEmail(email).orElseThrow(
                () -> new HttpClientErrorException(HttpStatus.NOT_FOUND)
        );

        log.info("User with email={} loaded", email);
        return UserMapper.userToJson(user);

    }


    private User saveUser(User user) {

        userRepository.save(user);
        return user;
    }

    @Transactional
    public List<User> loadAll() {
        return userRepository.findAll();
    }

}
