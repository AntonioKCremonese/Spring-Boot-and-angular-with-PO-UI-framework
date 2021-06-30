package br.com.devakc.backendtotvs.domain.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.devakc.backendtotvs.domain.entities.Vaga;
import br.com.devakc.backendtotvs.infra.VagaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VagaService implements BaseServiceInterface<Vaga> {

  private final VagaRepository vagaRepository;

  @Override
  public List<Vaga> findAll() {
    return this.vagaRepository.findAll();
  }

  @Override
  public Optional<Vaga> get(Long id) {
    return vagaRepository.findById(id);
  }

  @Override
  public Long createOrUpdate(Vaga object) {
    Vaga vagaCreatedOrUpdated = this.vagaRepository.save(object);
    return vagaCreatedOrUpdated.getId();
  }

  @Override
  public void delete(Long id) {
    this.vagaRepository.delete(get(id).get());
  }

}
