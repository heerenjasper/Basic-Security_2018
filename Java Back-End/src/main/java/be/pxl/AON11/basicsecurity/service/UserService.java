package be.pxl.AON11.basicsecurity.service;

import be.pxl.AON11.basicsecurity.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ebert Joris on 24/03/2018.
 */
public interface UserService {

    Optional<User> findUserById(Integer id);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserById(Integer id);

    void deleteAllUsers();

    List<User> findAllUsers();

    boolean doesUserExist(Integer UserId);

}
