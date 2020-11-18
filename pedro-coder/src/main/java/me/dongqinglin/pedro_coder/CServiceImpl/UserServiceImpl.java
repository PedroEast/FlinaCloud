package me.dongqinglin.pedro_coder.CServiceImpl;

import me.dongqinglin.pedro_coder.DDaoApi.UserDao;
import me.dongqinglin.pedro_coder.ZEntity.User;
import me.dongqinglin.pedro_coder.CService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public void save(User user){userDao.save(user);}
    public void deleteById(int id){userDao.deleteById(id);}
    public List<User> findAll(){return userDao.findAll();}
    public Optional<User> findById(int id){return userDao.findById(id);}

}
