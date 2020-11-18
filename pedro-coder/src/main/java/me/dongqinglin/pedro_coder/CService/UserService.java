package me.dongqinglin.pedro_coder.CService;

import me.dongqinglin.pedro_coder.ZEntity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public void save(User user);
    public void deleteById(int id);
    public List<User> findAll();
    public Optional<User> findById(int id);
}
