package me.dongqinglin.pedro_coder.DDaoApi;

import me.dongqinglin.pedro_coder.ZEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {
    
}
