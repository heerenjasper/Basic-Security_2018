package be.pxl.AON11.basicsecurity.service;

import be.pxl.AON11.basicsecurity.model.File;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ebert Joris on 24/03/2018.
 */
public interface FileService {

    Optional<File> findFileById(Integer id);

    void saveFile(File file);

    void updateFile(File file);

    void deleteFileById(Integer id);

    void deleteAllFiles();

    List<File> findAllFiles();

    boolean doesFileExist(Integer FileId);

}
