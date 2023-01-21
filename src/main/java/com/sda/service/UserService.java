package com.sda.service;

import com.sda.dao.UsersDAO;
import com.sda.dto.UserDTO;
import com.sda.mapper.UserMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserService {
     private final UsersDAO usersDAO;
     private final UserMapper userMapper;

     public List<UserDTO> findAll(){
         return usersDAO.findAll().stream()
                 .map(user -> userMapper.map(user))
                 .collect(Collectors.toList());
     }
}
