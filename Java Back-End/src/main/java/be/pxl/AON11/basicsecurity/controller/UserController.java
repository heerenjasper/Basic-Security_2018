package be.pxl.AON11.basicsecurity.controller;

import be.pxl.AON11.basicsecurity.model.User;
import be.pxl.AON11.basicsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ebert Joris on 24/03/2018.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    //Service will do all data retrieval/manipulation work
    @Autowired
    UserService userService;

    @GetMapping("/")
    public List<User> getAll() {
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
        Optional<User> user = userService.findUserById(userId);

        if (user.isPresent()) {
            return new ResponseEntity<User>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) {

        // Check if user with same user number already exists
        // This should not be allowed because it's a unique value.
        if (!userService.doesUserExist(user.getId())) {
            userService.saveUser(user);
            return new ResponseEntity<User>(user, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUserById(@PathVariable Integer userId, @RequestBody User user) {

        if (userService.doesUserExist(userId)) {
            // We set the id to the updated id otherwise it could overwrite another one.
            user.setId(userId);
            userService.updateUser(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUserById(@PathVariable Integer userId) {

        if (userService.doesUserExist(userId)) {
            userService.deleteUserById(userId);
            return new ResponseEntity<User>(HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }

    }
}
