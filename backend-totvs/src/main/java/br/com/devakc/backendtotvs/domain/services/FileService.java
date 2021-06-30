package br.com.devakc.backendtotvs.domain.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.devakc.backendtotvs.domain.entities.File;
import br.com.devakc.backendtotvs.infra.FileRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {
  private final FileRepository fileRepository;

  @Value("${upload.path}")
  private String uploadPath;

  @PostConstruct
  public void init() {
    try {
      Files.createDirectories(Paths.get(uploadPath));
    } catch (IOException e) {
      throw new RuntimeException("Could not create upload folder!");
    }
  }

  public Optional<File> getById(Long id) {
    return this.fileRepository.findById(id);
  }

  public File save(MultipartFile file) {
    try {
      Path root = Paths.get(uploadPath);
      if (!Files.exists(root)) {
        init();
      }

      var mimeTypeSplited = file.getOriginalFilename().split("\\.");
      var filePath = root.resolve(UUID.randomUUID() + "." + mimeTypeSplited[mimeTypeSplited.length - 1]);

      Files.copy(file.getInputStream(), filePath);

      File fileEntity = new File(null, file.getOriginalFilename(), filePath.toString(), file.getSize());

      return fileRepository.save(fileEntity);
    } catch (Exception e) {
      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }
  }

  public Optional<Resource> load(Long id) {

    return this.getById(id).flatMap(fileFounded -> {
      try {
        Path file = Paths.get(uploadPath).resolve(fileFounded.getUrl());
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
          return Optional.of(resource);
        } else {
          throw new RuntimeException("Could not read the file!");
        }
      } catch (Exception e) {
        return Optional.empty();
      }
    });
  }

  public List<Path> loadAll() {
    try {
      Path root = Paths.get(uploadPath);
      if (Files.exists(root)) {
        return Files.walk(root, 1).filter(path -> !path.equals(root)).collect(Collectors.toList());
      }

      return Collections.emptyList();
    } catch (IOException e) {
      throw new RuntimeException("Could not list the files!");
    }
  }

}
