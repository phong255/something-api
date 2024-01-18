package dev.lhphong.somethingapi.Controllers.Client;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.lhphong.somethingapi.Config.Util.HttpUtil;
import dev.lhphong.somethingapi.Dto.UserDto;
import dev.lhphong.somethingapi.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
public class clientController {
    @GetMapping("/hello")
    public String helloClient(){
        return "Hello client.";
    }
}
