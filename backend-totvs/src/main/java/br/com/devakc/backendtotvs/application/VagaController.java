package br.com.devakc.backendtotvs.application;

import java.util.List;

import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.devakc.backendtotvs.domain.entities.Vaga;
import br.com.devakc.backendtotvs.domain.services.VagaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/vagas")
@RequiredArgsConstructor
@Log4j2
public class VagaController {

  private final VagaService vagaService;
  private static final Logger log = LogManager.getLogger(VagaController.class);

  @GetMapping
  public ResponseEntity<List<Vaga>> findAllVagas() {
    return ResponseEntity.ok(this.vagaService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Vaga> getVagaById(@PathVariable("id") Long id) {
    return this.vagaService.get(id).map(ResponseEntity::ok)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vaga not found"));
  }

  @PostMapping
  public ResponseEntity<String> createVaga(@RequestBody @Valid Vaga vaga) {
    Long vagaId = this.vagaService.createOrUpdate(vaga);
    return new ResponseEntity<>(vagaId.toString(), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<String> updateVaga(@RequestBody @Valid Vaga vaga) {
    Long vagaId = this.vagaService.createOrUpdate(vaga);
    return new ResponseEntity<>(vagaId.toString(), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteVaga(@PathVariable("id") Long id) {
    this.vagaService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
