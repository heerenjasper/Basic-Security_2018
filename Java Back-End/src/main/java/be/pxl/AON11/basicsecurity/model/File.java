package be.pxl.AON11.basicsecurity.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Ebert Joris on 24/03/2018.
 */
@Entity
@Table(name = "files", catalog = "bs_api")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String extension;

    @NotNull
    private String href;

    public File() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
