package dev.lhphong.somethingapi.Services;

import dev.lhphong.somethingapi.Dto.UserDto;
import dev.lhphong.somethingapi.Models.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public UserDto getById(Integer userID);
    public UserDetailsService userDetailsService();
    public UserDto signUp(User user);
    public UserDto login(User user);
}
