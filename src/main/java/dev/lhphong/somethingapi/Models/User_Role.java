package dev.lhphong.somethingapi.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_role")
public class User_Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_role_ID;

    @Column(name = "userID")
    private Integer userID;

    @Column(name = "roleID")
    private Integer roleID;
}
