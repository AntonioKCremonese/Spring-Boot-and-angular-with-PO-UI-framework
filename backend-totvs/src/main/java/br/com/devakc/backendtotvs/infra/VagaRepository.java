package br.com.devakc.backendtotvs.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.devakc.backendtotvs.domain.entities.Vaga;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {

}
