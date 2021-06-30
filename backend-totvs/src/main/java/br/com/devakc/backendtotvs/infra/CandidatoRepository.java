package br.com.devakc.backendtotvs.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.devakc.backendtotvs.domain.entities.Candidato;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long> {

}
