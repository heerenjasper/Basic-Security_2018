package be.pxl.AON11.basicsecurity.service;

import be.pxl.AON11.basicsecurity.model.File;
import be.pxl.AON11.basicsecurity.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by Ebert Joris on 24/03/2018.
 */
@Service("fileService")
@Transactional
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public Optional<File> findFileById(Integer id) {
        return fileRepository.findById(id);
    }

    @Override
    public void saveFile(File file) {
        fileRepository.save(file);
    }

    @Override
    public void updateFile(File file) {
        saveFile(file);
    }

    @Override
    public void deleteFileById(Integer id) {
        fileRepository.deleteById(id);
    }

    @Override
    public void deleteAllFiles() {
        fileRepository.deleteAll();
    }

    @Override
    public List<File> findAllFiles() {
        return fileRepository.findAll();
    }

    @Override
    public boolean doesFileExist(Integer FileId) {
        return fileRepository.existsById(FileId);
    }

}

