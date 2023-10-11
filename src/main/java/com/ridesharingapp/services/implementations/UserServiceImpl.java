package com.ridesharingapp.services.implementations;

import com.ridesharingapp.dto.requests.UpdateUserRequest;
import com.ridesharingapp.models.User;
import com.ridesharingapp.repositories.UserRepository;
import com.ridesharingapp.services.interfaces.UserService;
import com.ridesharingapp.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public User getById(int id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not found user with current id: " + id));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void update(UpdateUserRequest request) throws  NotFoundException {
        User user = this.getById(request.getId());

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        log.info("Changing information for User: {}", user);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(int id) throws NotFoundException {
        User user = this.getById(id);

        userRepository.delete(user);
    }
}
