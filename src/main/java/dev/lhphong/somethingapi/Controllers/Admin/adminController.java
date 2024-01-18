package dev.lhphong.somethingapi.Controllers.Admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/admin")
public class adminController {
    @GetMapping("/hello")
    public String helloAdmin(){
        return "Hello admin.";
    }
}
