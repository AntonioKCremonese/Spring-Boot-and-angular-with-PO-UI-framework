package br.com.devakc.backendtotvs.test.integration;

import java.util.List;

import org.assertj.core.api.Assertions;
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

import br.com.devakc.backendtotvs.domain.entities.Vaga;
import br.com.devakc.backendtotvs.infra.VagaRepository;
import br.com.devakc.backendtotvs.utils.StatusVagasEnum;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class VagaControllerIT {

  @Autowired
  private VagaRepository vagaRepository;

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  @DisplayName("Should be return all Vagas when succesfull")
  void findAll() {
    Vaga vagaToBeSaved = makeVaga();
    this.vagaRepository.save(vagaToBeSaved);
    List<Vaga> vagas = testRestTemplate
        .exchange("/vagas", HttpMethod.GET, null, new ParameterizedTypeReference<List<Vaga>>() {
        }).getBody();

    Assertions.assertThat(vagas).isNotEmpty();
    Assertions.assertThat(vagas.size()).isEqualTo(1);

  }

  @Test
  @DisplayName("Should be return Vaga by id when succesfull")
  void findById() {
    Vaga vagaToBeSaved = makeVaga();
    this.vagaRepository.save(vagaToBeSaved);
    Vaga vaga = testRestTemplate
        .exchange("/vagas/" + vagaToBeSaved.getId(), HttpMethod.GET, null, new ParameterizedTypeReference<Vaga>() {
        }).getBody();

    Assertions.assertThat(vaga).isNotNull();
    Assertions.assertThat(vaga.getName()).isEqualTo(vagaToBeSaved.getName());

  }

  @Test
  @DisplayName("Should be create Vaga when succesfull")
  void create() {
    Vaga vagaToBeSaved = makeVaga();
    Long vagaId = testRestTemplate.postForObject("/vagas", vagaToBeSaved, Long.class);

    Assertions.assertThat(vagaId).isNotNull();
    Assertions.assertThat(vagaId).isEqualTo(vagaToBeSaved.getId());
  }

  @Test
  @DisplayName("Should be update Vaga when succesfull")
  void update() {
    Vaga vagaToBeSaved = makeVaga();
    Vaga vagaSaved = this.vagaRepository.save(vagaToBeSaved);
    vagaSaved.setName("Other name");
    ResponseEntity<Void> vagaResponseEntity = testRestTemplate.exchange("/vagas", HttpMethod.PUT,
        new HttpEntity<>(vagaSaved), Void.class);

    Assertions.assertThat(vagaResponseEntity).isNotNull();
    Assertions.assertThat(vagaResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
  }

  @Test
  @DisplayName("Should be delete Vaga when succesfull")
  void delete() {
    Vaga vagaToBeSaved = makeVaga();
    Vaga vagaSaved = this.vagaRepository.save(vagaToBeSaved);

    ResponseEntity<Void> vagaResponseEntity = testRestTemplate.exchange("/vagas/{id}", HttpMethod.DELETE, null,
        Void.class, vagaSaved.getId());

    Assertions.assertThat(vagaResponseEntity).isNotNull();
    Assertions.assertThat(vagaResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  private Vaga makeVaga() {
    Vaga vaga = new Vaga();
    vaga.setId(1L);
    vaga.setName("Java Developer");
    vaga.setStatus(StatusVagasEnum.OPEN);
    return vaga;
  }

}
