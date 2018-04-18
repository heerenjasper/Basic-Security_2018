package be.pxl.AON11.basicsecurity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageServiceImpl {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    public final Path rootLocation = Paths.get("upload-dir");
    private final Path fullPath = Paths.get("src/main/resources/", rootLocation.toString());

    public void store(MultipartFile multipartFile, String path, String filename) {
        try {
            // Combine the root location and given path to save the file
            Path saveLocation = Paths.get(fullPath.toString(), path);

            // Create a directory of the given location if it doesn't exist yet.
            if (!Files.isDirectory(saveLocation.toAbsolutePath())) {
                Files.createDirectories(saveLocation);
            }

            // Copy the content of the incoming file to the outgoing one.
            Files.copy(multipartFile.getInputStream(), saveLocation.resolve(filename));

        } catch (IOException e) {
            throw new RuntimeException("Could not copy file contents.");
        }
    }

    public Resource loadFile(String filename) {
        try {
            Path file = Paths.get("src/main/resources").resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Resource cannot be found.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Resource cannot be resolved.");
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage.");
        }
    }
}