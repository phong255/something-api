package dev.lhphong.somethingapi.Dto;

import dev.lhphong.somethingapi.Models.Role;
import dev.lhphong.somethingapi.Models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roleNames = new ArrayList<>();
        for(Role r : roles){
            roleNames.add(new SimpleGrantedAuthority(r.getRole_name()));
        }
        return roleNames;
    }

    public void setUser(User user){
        this.id = user.getUserID();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    public User getUser(){
        return new User(this.id,this.username,this.password);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
