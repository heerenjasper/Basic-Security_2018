package be.pxl.AON11.basicsecurity.controller;

import be.pxl.AON11.basicsecurity.model.File;
import be.pxl.AON11.basicsecurity.service.FileService;
import be.pxl.AON11.basicsecurity.service.StorageServiceImpl;
import be.pxl.AON11.basicsecurity.utils.Decryptor;
import be.pxl.AON11.basicsecurity.utils.Encryptor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;

/**
 * Created by Ebert Joris on 28/03/2018.
 */
@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:4200")
public class FileController {

    //Service will do all data retrieval/manipulation work
    private final FileService fileService;
    private StorageServiceImpl storageService;

    private final ResourceLoader resourceLoader;

    @Autowired
    public FileController(FileService fileService, ResourceLoader resourceLoader) {
        this.fileService = fileService;
        this.storageService = new StorageServiceImpl();
        this.resourceLoader = resourceLoader;
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

    @RequestMapping(value = "/image/{fileId}", method = RequestMethod.GET, produces = "image/png")
    public @ResponseBody byte[] getFile(@PathVariable Integer fileId, final HttpServletResponse response) {
        Optional<File> file = fileService.findFileById(fileId);

        if (file.isPresent()) {
            Resource resource = storageService.loadResource(file.get().getHref());
            try {
                InputStream inputStream = resource.getInputStream();
                BufferedImage img = ImageIO.read(inputStream);
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                ImageIO.write(img, "png", bao);

                return bao.toByteArray();
            } catch (IOException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    @PostMapping("/")
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
        java.io.File newFile = new java.io.File("files/" );

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

    @PostMapping("/encrypt/")
    public ResponseEntity<File> encryptFile(@RequestParam("file") MultipartFile file, @RequestPart String message) {
        Integer randomNumber = new Random().nextInt();
        String fileName = "file_" + randomNumber + ".png";
        // Save file
        storageService.store(file, "basic-files/", fileName);
        storageService.store(file, "encoded-files/", fileName);

        // Encrypt file
        String fullPath = storageService.createFullPath("basic-files/", fileName);
        String output = storageService.createFullPath("encoded-files/", fileName);
        Encryptor.encrypt(message, fullPath, output);

        File responseFile = new File();
        responseFile.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
        responseFile.setHref("resources/upload-dir/encoded-files/" + fileName);
        fileService.saveFile(responseFile);

        return new ResponseEntity<File>(responseFile, HttpStatus.OK);
    }

    @GetMapping("/decrypt/{fileId}")
    public ResponseEntity<String> decryptFile(@PathVariable Integer fileId) {

        Optional<File> fileFromRepo = fileService.findFileById(fileId);
        String decryptedMessage = "Not Found";

        // load file
        if(fileFromRepo.isPresent()) {
            Path file = storageService.loadFile(fileFromRepo.get().getHref());
                decryptedMessage = Decryptor.decrypt(file.toString());
        }

        return new ResponseEntity<>(decryptedMessage, HttpStatus.OK);
    }
}
