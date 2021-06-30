package br.com.devakc.backendtotvs.application;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.devakc.backendtotvs.domain.entities.Candidato;
import br.com.devakc.backendtotvs.domain.entities.File;
import br.com.devakc.backendtotvs.domain.mappers.RequestPostFile;
import br.com.devakc.backendtotvs.domain.services.CandidatoService;
import br.com.devakc.backendtotvs.domain.services.FileService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/file")
@RequiredArgsConstructor
public class FileController {
  private final FileService fileService;

  private final CandidatoService candidatoService;

  @GetMapping
  public ResponseEntity<List<Path>> getListFiles() {
    List<Path> paths = this.fileService.loadAll();
    return ResponseEntity.ok(paths);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Resource> getById(@PathVariable("id") Long id) {
    return this.fileService.load(id)
        .map(file -> ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file))
        .orElse(ResponseEntity.notFound().build());

  }

  @PostMapping
  public ResponseEntity<HashMap<String, HttpStatus>> createFile(@RequestParam(value = "data") String data,
      @RequestParam("file") MultipartFile file) {
    try {
      RequestPostFile request = new ObjectMapper().readValue(data, RequestPostFile.class);

      Candidato candidato = this.candidatoService.get(request.getCandidato_id()).get();

      if (candidato.equals(null)) {
        return ResponseEntity.badRequest().build();
      }

      File fileSaved = this.fileService.save(file);

      candidato.setFile(fileSaved);
      this.candidatoService.createOrUpdate(candidato);

      HashMap<String, HttpStatus> response = new HashMap<>();
      response.put("ok", HttpStatus.OK);

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

}
