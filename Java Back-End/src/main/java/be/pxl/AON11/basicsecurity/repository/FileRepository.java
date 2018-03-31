package be.pxl.AON11.basicsecurity.repository;

import be.pxl.AON11.basicsecurity.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Ebert Joris on 24/03/2018.
 */
public interface FileRepository extends JpaRepository<File, Integer> {

}
