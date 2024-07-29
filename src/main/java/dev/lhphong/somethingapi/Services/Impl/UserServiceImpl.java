package dev.lhphong.somethingapi.Services.Impl;

import dev.lhphong.somethingapi.Config.Constant.MessageCode;
import dev.lhphong.somethingapi.Config.exception.CustomException;
import dev.lhphong.somethingapi.Dto.UserDto;
import dev.lhphong.somethingapi.Models.Role;
import dev.lhphong.somethingapi.Models.User;
import dev.lhphong.somethingapi.Models.User_Role;
import dev.lhphong.somethingapi.Repository.RoleRepo;
import dev.lhphong.somethingapi.Repository.UserRepo;
import dev.lhphong.somethingapi.Repository.User_RoleRepo;
import dev.lhphong.somethingapi.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    User_RoleRepo userRoleRepo;
    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
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
        };
    }

    @Transactional
    @Override
    public UserDto signUp(User user_form) {
        User userDB = userRepo.getUserByUsername(user_form.getUsername());
        if(userDB != null){
            throw new CustomException(MessageCode.USER_EXIST);
        }
        UserDto userDto = new UserDto();
        //------ save user info ---------
        User user = userRepo.save(new User(null,user_form.getUsername(),passwordEncoder.encode(user_form.getPassword())));
        //------ save user-role info -----
        userRoleRepo.save(new User_Role(null,user.getUserID(),3));
        userDto.setUser(user);
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepo.findById(3).orElseThrow());
        userDto.setRoles(roles);
        return userDto;
    }

    @Override
    public UserDto login(User user) {
        UserDto userDto;
        User user_save = userRepo.getUserByUsername(user.getUsername());
        if(user_save == null || !passwordEncoder.matches(user.getPassword(),user_save.getPassword())){
            throw new CustomException(MessageCode.USER_NOT_FOUND);
        }
        else{
            userDto = new UserDto();
            userDto.setUser(user_save);
            List<Role> roles = new ArrayList<>();
            List<User_Role> userRoles = userRoleRepo.getAllByUserID(user_save.getUserID());
            for(User_Role ur : userRoles){
                roles.add(roleRepo.findById(ur.getRoleID()).orElseThrow());
            }
            userDto.setRoles(roles);
            return userDto;
        }
    }

    @Override
    public UserDto getById(Integer userID) {
        return null;
    }

}
