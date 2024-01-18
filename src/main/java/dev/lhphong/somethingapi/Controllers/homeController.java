package dev.lhphong.somethingapi.Controllers;

import dev.lhphong.somethingapi.Config.Response.ResponseHandler;
import dev.lhphong.somethingapi.Dto.UserDto;
import dev.lhphong.somethingapi.Models.Role;
import dev.lhphong.somethingapi.Models.User;
import dev.lhphong.somethingapi.Services.JwtService;
import dev.lhphong.somethingapi.Services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/home")
public class homeController {
    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok("Welcome something page");

    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody User user) {
        UserDto userDto = userService.login(user);
        HashMap<String,String> headers = new HashMap<>();
        headers.put("Authorization",jwtService.generateToken(userDto));
        return ResponseEntity.ok().header("Authorization",jwtService.generateToken(userDto)).body(userDto);
    }
    @PostMapping("/signUp")
    public ResponseEntity<Object> signUp(
            @RequestBody User user
    ) {
        return ResponseHandler.responseBuilder("Sign up", HttpStatus.CREATED,userService.signUp(user));
    }
}
