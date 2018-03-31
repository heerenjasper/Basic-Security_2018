package be.pxl.AON11.basicsecurity.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Ebert Joris on 24/03/2018.
 */
@Entity
@Table(name = "users", catalog = "bs_api")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String public_key;

    @NotNull
    private String private_key;


    public User() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPublicKeyPath() {
        return public_key;
    }

    public void setPublicKeyPath(String publicKeyPath) {
        this.public_key = publicKeyPath;
    }

    public String getPrivateKeyPath() {
        return private_key;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.private_key = privateKeyPath;
    }
}
