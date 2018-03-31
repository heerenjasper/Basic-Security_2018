package be.pxl.AON11.basicsecurity.repository;

import be.pxl.AON11.basicsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Ebert Joris on 24/03/2018.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

}
