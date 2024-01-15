package dev.lhphong.somethingapi.Repository;

import dev.lhphong.somethingapi.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Integer> {
    public User getUserByUsername(String username);
}
