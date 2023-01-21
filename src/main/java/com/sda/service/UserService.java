package com.sda.service;

import com.sda.dao.UsersDAO;
import com.sda.db.HibernateUtils;
import com.sda.dto.UserDTO;
import com.sda.exception.NotFoundException;
import com.sda.exception.UsernameConflictException;
import com.sda.mapper.UserMapper;
import com.sda.model.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserService {
    private final UsersDAO usersDAO;
    private final UserMapper userMapper;

    public List<UserDTO> findAll() {
        return usersDAO.findAll().stream()
                .map(user -> userMapper.map(user))
                .collect(Collectors.toList());
    }

    public UserDTO findByUsername(String username) {
        User user = usersDAO.findUserByUsername(username);
        if (user == null) {
            throw new NotFoundException("User with username %s not found!".formatted(username));
        }
        return userMapper.map(user);
    }

    public void deleteByUsername(String username) {
        boolean deleted = usersDAO.deleteByUsername(username);
        if (!deleted) {
            throw new NotFoundException("User with username %s not found!".formatted(username));
        }
    }

    public void create(User user) {
        if (usersDAO.existsByUsername(user.getUsername())){
            throw new UsernameConflictException("User with username %s exist!".formatted(user.getUsername()));
        }
        usersDAO.create(user);
    }

    public UserDTO update(User user, String username){
        if (!user.getUsername().equals(username)){
            throw new UsernameConflictException("User with username %s exist!".formatted(user.getUsername()));
        } if (!usersDAO.existsByUsername(user.getUsername())) {
            throw new NotFoundException("User with username %s not found!".formatted(username));
        }
        User result = usersDAO.update(user);
        return userMapper.map(result);
    }

}


