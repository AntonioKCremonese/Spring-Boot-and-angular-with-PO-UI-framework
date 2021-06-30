package br.com.devakc.backendtotvs.test.integration;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import br.com.devakc.backendtotvs.domain.entities.Candidato;
import br.com.devakc.backendtotvs.domain.entities.Vaga;
import br.com.devakc.backendtotvs.infra.CandidatoRepository;
import br.com.devakc.backendtotvs.infra.VagaRepository;
import br.com.devakc.backendtotvs.utils.StatusVagasEnum;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CandidatoControllerIT {

  @Autowired
  private CandidatoRepository candidatoRepository;

  @Autowired
  private VagaRepository vagaRepository;

  @Autowired
  private TestRestTemplate testRestTemplate;

  @BeforeEach
  void setUp() {
    Vaga vaga = makeVaga();
    this.vagaRepository.save(vaga);
  }

  @Test
  @DisplayName("Should be return all Candidatos when succesfull")
  void findAll() {
    Candidato candidatoToBeSaved = makeCandidato();
    this.candidatoRepository.save(candidatoToBeSaved);
    List<Candidato> candidatos = testRestTemplate
        .exchange("/candidatos", HttpMethod.GET, null, new ParameterizedTypeReference<List<Candidato>>() {
        }).getBody();

    Assertions.assertThat(candidatos).isNotEmpty();
    Assertions.assertThat(candidatos.size()).isEqualTo(1);

  }

  @Test
  @DisplayName("Should be return Candidato by id when succesfull")
  void findById() {
    Candidato candidatoToBeSaved = makeCandidato();
    this.candidatoRepository.save(candidatoToBeSaved);
    Candidato candidato = testRestTemplate.exchange("/candidatos/" + candidatoToBeSaved.getId(), HttpMethod.GET, null,
        new ParameterizedTypeReference<Candidato>() {
        }).getBody();

    Assertions.assertThat(candidato).isNotNull();
    Assertions.assertThat(candidato.getName()).isEqualTo(candidatoToBeSaved.getName());

  }

  @Test
  @DisplayName("Should be create Vaga when succesfull")
  void create() {
    Candidato candidatoToBeSaved = makeCandidato();
    Long candidatoId = testRestTemplate.postForObject("/candidatos", candidatoToBeSaved, Long.class);

    Assertions.assertThat(candidatoId).isNotNull();
    Assertions.assertThat(candidatoId).isEqualTo(candidatoToBeSaved.getId());
  }

  @Test
  @DisplayName("Should be update Vaga when succesfull")
  void update() {
    Candidato candidatoToBeSaved = makeCandidato();
    Candidato candidatoSaved = this.candidatoRepository.save(candidatoToBeSaved);
    candidatoSaved.setName("Other name");
    ResponseEntity<Void> candidatoResponseEntity = testRestTemplate.exchange("/candidatos", HttpMethod.PUT,
        new HttpEntity<>(candidatoSaved), Void.class);

    Assertions.assertThat(candidatoResponseEntity).isNotNull();
    Assertions.assertThat(candidatoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
  }

  @Test
  @DisplayName("Should be delete Vaga when succesfull")
  void delete() {
    Candidato candidatoToBeSaved = makeCandidato();
    Candidato candidatoSaved = this.candidatoRepository.save(candidatoToBeSaved);

    ResponseEntity<Void> candidatoResponseEntity = testRestTemplate.exchange("/candidatos/{id}", HttpMethod.DELETE,
        null, Void.class, candidatoSaved.getId());

    Assertions.assertThat(candidatoResponseEntity).isNotNull();
    Assertions.assertThat(candidatoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  private Candidato makeCandidato() {
    Candidato candidato = new Candidato();
    candidato.setId(1L);
    candidato.setName("Candidato Teste");
    candidato.setMail("teste@teste.com");
    candidato.setPhone("9991559999");
    candidato.setVaga(makeVaga());
    return candidato;
  }

  private Vaga makeVaga() {
    Vaga vaga = new Vaga();
    vaga.setId(1L);
    vaga.setName("Java Developer");
    vaga.setStatus(StatusVagasEnum.OPEN);
    return vaga;
  }

}
