package me.dongqinglin.zuul_gateway.CServiceImpl;


import me.dongqinglin.zuul_gateway.DDAO.UserDao;
import me.dongqinglin.zuul_gateway.ZBean.MyUserDetail;
import me.dongqinglin.zuul_gateway.ZEntity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDao.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException(("Not Found" + username)));
        // todo user is not enable
        return user.map(MyUserDetail::new).get();
    }
}