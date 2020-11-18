package me.dongqinglin.pedro_coder.BController;

import me.dongqinglin.pedro_coder.ZEntity.User;
import me.dongqinglin.pedro_coder.CService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping
    public void addUser(@RequestBody User user) { userService.save(user); }
    @PutMapping
    public void updateUser(@RequestBody User user) {userService.save(user);}
    @DeleteMapping("{id}")
    public void deleteUserBy(@PathVariable int id){userService.deleteById(id);}
    @GetMapping("all")
    public List<User> getAllUser() { return userService.findAll(); }
    @GetMapping("{id}")
    public Optional<User> getUserBy(@PathVariable int id) { return userService.findById(id); }

}
