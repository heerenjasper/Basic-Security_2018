package be.pxl.AON11.basicsecurity.controller;

import be.pxl.AON11.basicsecurity.model.File;
import be.pxl.AON11.basicsecurity.service.FileService;
import be.pxl.AON11.basicsecurity.service.StorageServiceImpl;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

/**
 * Created by Ebert Joris on 28/03/2018.
 */
@RestController
@RequestMapping("/api/files")
public class FileController {

    //Service will do all data retrieval/manipulation work
    private final FileService fileService;
    private StorageServiceImpl storageService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<File> getFileById(@PathVariable Integer fileId) {
        Optional<File> file = fileService.findFileById(fileId);

        if (file.isPresent()) {
            return new ResponseEntity<File>(file.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<File>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<File> createFile(@RequestParam("file") MultipartFile file) {

        // Check if file with same file number already exists
        // This should not be allowed because it's a unique value.
        File fileForDatabase = new File();
        fileForDatabase.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
        fileService.saveFile(fileForDatabase);
        storageService.store(file, "encoded", "file_1");

        InputStream inputStream = null;
        OutputStream outputStream = null;
        String fileName = file.getOriginalFilename();
        java.io.File newFile = new java.io.File("encoded_files/" );

        return new ResponseEntity<File>(fileForDatabase, HttpStatus.CREATED);
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<File> updateFileById(@PathVariable Integer fileId, @RequestBody File file) {

        if (fileService.doesFileExist(fileId)) {
            // We set the id to the updated id otherwise it could overwrite another one.
            file.setId(fileId);
            fileService.updateFile(file);
            return new ResponseEntity<File>(file, HttpStatus.OK);
        } else {
            return new ResponseEntity<File>(HttpStatus.CONFLICT);
        }

    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<File> deleteFileById(@PathVariable Integer fileId) {

        if (fileService.doesFileExist(fileId)) {
            fileService.deleteFileById(fileId);
            return new ResponseEntity<File>(HttpStatus.OK);
        } else {
            return new ResponseEntity<File>(HttpStatus.CONFLICT);
        }

    }
}
