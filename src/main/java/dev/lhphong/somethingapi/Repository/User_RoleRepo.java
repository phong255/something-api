package dev.lhphong.somethingapi.Repository;

import dev.lhphong.somethingapi.Models.User_Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface User_RoleRepo extends JpaRepository<User_Role,Integer> {
    public List<User_Role> getAllByUserID(Integer userID);
}
