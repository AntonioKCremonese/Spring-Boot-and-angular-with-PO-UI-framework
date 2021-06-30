package br.com.devakc.backendtotvs.test.controller;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.devakc.backendtotvs.application.CandidatoController;
import br.com.devakc.backendtotvs.domain.entities.Candidato;
import br.com.devakc.backendtotvs.domain.entities.Vaga;
import br.com.devakc.backendtotvs.domain.services.CandidatoService;
import br.com.devakc.backendtotvs.utils.StatusVagasEnum;

@ExtendWith(SpringExtension.class)
@DisplayName("CandidatoControllerTest")
public class CandidatoControllerTest {

  @InjectMocks
  CandidatoController candidatoController;

  @Mock
  CandidatoService candidatoService;

  @BeforeEach
  void setUp() {
    Candidato candidato = makeCandidato();
    BDDMockito.when(candidatoService.findAll()).thenReturn(List.of(candidato));
    BDDMockito.when(candidatoService.get(ArgumentMatchers.anyLong())).thenReturn(Optional.of(candidato));
    BDDMockito.when(candidatoService.createOrUpdate(ArgumentMatchers.any(Candidato.class)))
        .thenReturn(candidato.getId());
    BDDMockito.doNothing().when(candidatoService).delete(ArgumentMatchers.anyLong());
  }

  @Test
  @DisplayName("Should be return all candidatos when succesfull")
  void findAll() {
    Candidato expectedCandidato = makeCandidato();
    List<Candidato> candidatos = this.candidatoController.findAllCandidatos().getBody();

    Assertions.assertThat(candidatos).isNotEmpty();
    Assertions.assertThat(candidatos.get(0).getName()).isEqualTo(expectedCandidato.getName());
  }

  @Test
  @DisplayName("Should be return candidato by id when succesfull")
  void get() {
    Candidato expectedCandidato = makeCandidato();
    Candidato candidato = this.candidatoController.getCandidatoById(1L).getBody();

    Assertions.assertThat(candidato).isNotNull();
    Assertions.assertThat(candidato.getName()).isEqualTo(expectedCandidato.getName());
  }

  @Test
  @DisplayName("Should be create Candidato when succesfull")
  void create() {
    Candidato expectedCandidato = makeCandidato();
    String candidatoId = this.candidatoController.createCandidato(expectedCandidato).getBody();

    Assertions.assertThat(candidatoId).isNotNull();
    Assertions.assertThat(Long.parseLong(candidatoId)).isEqualTo(expectedCandidato.getId());
  }

  @Test
  @DisplayName("Should be update Candidato when succesfull")
  void update() {
    Candidato expectedCandidato = makeCandidato();
    String candidatoId = this.candidatoController.createCandidato(expectedCandidato).getBody();

    Assertions.assertThat(candidatoId).isNotNull();
    Assertions.assertThat(Long.parseLong(candidatoId)).isEqualTo(expectedCandidato.getId());
  }

  @Test
  @DisplayName("Should be delete Candidato when succesfull")
  void delete() {
    Candidato expectedCandidato = makeCandidato();
    Assertions.assertThatCode(() -> this.candidatoController.deleteCandidato(expectedCandidato.getId()))
        .doesNotThrowAnyException();
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
