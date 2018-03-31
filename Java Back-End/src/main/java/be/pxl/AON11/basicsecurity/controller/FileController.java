package be.pxl.AON11.basicsecurity.controller;

import be.pxl.AON11.basicsecurity.model.File;
import be.pxl.AON11.basicsecurity.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ebert Joris on 28/03/2018.
 */
@RestController
@RequestMapping("/api/files")
public class FileController {

    //Service will do all data retrieval/manipulation work
    @Autowired
    FileService fileService;

    @GetMapping("/")
    public List<File> getAll() {
        return fileService.findAllFiles();
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
    public ResponseEntity<File> createFile(@RequestBody File file) {

        // Check if file with same file number already exists
        // This should not be allowed because it's a unique value.
        if (!fileService.doesFileExist(file.getId())) {
            fileService.saveFile(file);
            return new ResponseEntity<File>(file, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<File>(HttpStatus.CONFLICT);
        }
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
