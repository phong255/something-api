package dev.lhphong.somethingapi.Services;

import dev.lhphong.somethingapi.Models.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    public Role getById(Integer roleID);
}
