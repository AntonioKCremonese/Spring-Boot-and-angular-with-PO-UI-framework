package br.com.devakc.backendtotvs.domain.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import br.com.devakc.backendtotvs.domain.entities.Candidato;
import br.com.devakc.backendtotvs.infra.CandidatoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CandidatoService implements BaseServiceInterface<Candidato> {

  private final CandidatoRepository candidatoRepository;

  @Override
  public List<Candidato> findAll() {
    return this.candidatoRepository.findAll();
  }

  @Override
  public Optional<Candidato> get(Long id) {
    return candidatoRepository.findById(id);
  }

  @Override
  public Long createOrUpdate(Candidato object) {
    Candidato candidatoCreatedOrUpdate = this.candidatoRepository.save(object);
    return candidatoCreatedOrUpdate.getId();

  }

  @Override
  public void delete(Long id) {
    this.candidatoRepository.delete(this.get(id).get());
  }

}
