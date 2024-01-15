package dev.lhphong.somethingapi.Services;

import dev.lhphong.somethingapi.Dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public UserDto getById(Integer userID);
    public UserDto getByUsername(String username);
}
