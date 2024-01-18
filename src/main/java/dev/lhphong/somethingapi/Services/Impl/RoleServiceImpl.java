package dev.lhphong.somethingapi.Services.Impl;

import dev.lhphong.somethingapi.Models.Role;
import dev.lhphong.somethingapi.Repository.RoleRepo;
import dev.lhphong.somethingapi.Services.RoleService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class RoleServiceImpl implements RoleService {
    RoleRepo roleRepo;
    @Override
    public Role getById(Integer roleID) {
        return roleRepo.findById(roleID).orElseThrow();
    }
}
