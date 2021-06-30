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

import br.com.devakc.backendtotvs.application.VagaController;
import br.com.devakc.backendtotvs.domain.entities.Vaga;
import br.com.devakc.backendtotvs.domain.services.VagaService;
import br.com.devakc.backendtotvs.utils.StatusVagasEnum;

@ExtendWith(SpringExtension.class)
public class VagaControllerTest {

  @InjectMocks
  private VagaController vagaController;

  @Mock
  private VagaService vagaService;

  @BeforeEach
  void setUp() {
    Vaga vaga = makeVaga();
    BDDMockito.when(vagaService.findAll()).thenReturn(List.of(vaga));
    BDDMockito.when(vagaService.get(ArgumentMatchers.anyLong())).thenReturn(Optional.of(vaga));
    BDDMockito.when(vagaService.createOrUpdate(ArgumentMatchers.any(Vaga.class))).thenReturn(vaga.getId());
    BDDMockito.doNothing().when(vagaService).delete(ArgumentMatchers.anyLong());
  }

  @Test
  @DisplayName("Should be return all vagas when succesfull")
  void findAll() {
    Vaga expectedVaga = makeVaga();
    List<Vaga> vagas = this.vagaController.findAllVagas().getBody();

    Assertions.assertThat(vagas).isNotEmpty();
    Assertions.assertThat(vagas.get(0).getName()).isEqualTo(expectedVaga.getName());
  }

  @Test
  @DisplayName("Should be return VagaById when succesfull")
  void get() {
    Vaga expectedVaga = makeVaga();
    Vaga vaga = this.vagaController.getVagaById(1L).getBody();

    Assertions.assertThat(vaga).isNotNull();
    Assertions.assertThat(vaga.getName()).isEqualTo(expectedVaga.getName());
  }

  @Test
  @DisplayName("Should be create Vaga when succesfull")
  void create() {
    Vaga expectedVaga = makeVaga();
    String vagaId = this.vagaController.createVaga(expectedVaga).getBody();

    Assertions.assertThat(vagaId).isNotNull();
    Assertions.assertThat(Long.parseLong(vagaId)).isEqualTo(expectedVaga.getId());
  }

  @Test
  @DisplayName("Should be update Vaga when succesfull")
  void update() {
    Vaga expectedVaga = makeVaga();
    String vagaId = this.vagaController.updateVaga(expectedVaga).getBody();

    Assertions.assertThat(vagaId).isNotNull();
    Assertions.assertThat(Long.parseLong(vagaId)).isEqualTo(expectedVaga.getId());
  }

  @Test
  @DisplayName("Should be delete Vaga when succesfull")
  void delete() {
    Vaga expectedVaga = makeVaga();
    Assertions.assertThatCode(() -> this.vagaController.deleteVaga(expectedVaga.getId())).doesNotThrowAnyException();
  }

  private Vaga makeVaga() {
    Vaga vaga = new Vaga();
    vaga.setId(1L);
    vaga.setName("Java Developer");
    vaga.setStatus(StatusVagasEnum.OPEN);
    return vaga;
  }

}
