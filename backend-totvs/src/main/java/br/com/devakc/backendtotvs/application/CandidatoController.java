package br.com.devakc.backendtotvs.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import br.com.devakc.backendtotvs.domain.entities.Candidato;
import br.com.devakc.backendtotvs.domain.services.CandidatoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/candidatos")
@RequiredArgsConstructor
public class CandidatoController {
  private final CandidatoService candidatoService;

  @GetMapping
  public ResponseEntity<List<Candidato>> findAllCandidatos() {
    return ResponseEntity.ok(this.candidatoService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Candidato> getCandidatoById(@PathVariable("id") Long id) {
    return this.candidatoService.get(id).map(ResponseEntity::ok)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Candidato not found"));
  }

  @PostMapping
  public ResponseEntity<String> createCandidato(@RequestBody Candidato candidato) {
    Long candidatoId = this.candidatoService.createOrUpdate(candidato);
    return new ResponseEntity<>(candidatoId.toString(), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<String> updateCandidato(@RequestBody @Valid Candidato candidato) {
    Long candidatoId = this.candidatoService.createOrUpdate(candidato);
    return new ResponseEntity<>(candidatoId.toString(), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCandidato(@PathVariable("id") Long id) {
    this.candidatoService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
