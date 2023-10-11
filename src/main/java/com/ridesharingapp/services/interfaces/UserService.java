package com.ridesharingapp.services.interfaces;

import com.ridesharingapp.dto.requests.UpdateUserRequest;
import com.ridesharingapp.models.User;
import com.ridesharingapp.utils.exceptions.BadRequestException;
import com.ridesharingapp.utils.exceptions.NotFoundException;

import java.util.List;

public interface UserService  {

    User getById(int id) throws NotFoundException;

    List<User> getAll();

    void update(UpdateUserRequest request) throws BadRequestException, NotFoundException;

    void delete(int id) throws NotFoundException;


}
