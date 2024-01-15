package dev.lhphong.somethingapi.Services.Impl;

import dev.lhphong.somethingapi.Dto.UserDto;
import dev.lhphong.somethingapi.Models.Role;
import dev.lhphong.somethingapi.Models.User;
import dev.lhphong.somethingapi.Models.User_Role;
import dev.lhphong.somethingapi.Repository.RoleRepo;
import dev.lhphong.somethingapi.Repository.UserRepo;
import dev.lhphong.somethingapi.Repository.User_RoleRepo;
import dev.lhphong.somethingapi.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    UserRepo userRepo;
    User_RoleRepo userRoleRepo;
    RoleRepo roleRepo;
    @Override
    public UserDto getById(Integer userID) {
        return null;
    }

    @Override
    public UserDto getByUsername(String username) {
        UserDto userDto = new UserDto();
        User user = userRepo.getUserByUsername(username);
        if(user == null){
            log.error("This user doesn't exist.");
        }
        else{
            List<User_Role> userRoles = userRoleRepo.getAllByUserID(user.getUserID());
            List<Role> roles = new ArrayList<>();
            for (User_Role ur : userRoles){
                roles.add(roleRepo.findById(ur.getRoleID()).orElseThrow());
            }
            userDto.setUser(user);
            userDto.setRoles(roles);
        }
        return userDto;
    }
}
