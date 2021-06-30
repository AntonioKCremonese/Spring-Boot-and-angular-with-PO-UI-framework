package br.com.devakc.backendtotvs.domain.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.devakc.backendtotvs.utils.StatusVagasEnum;
import lombok.Data;

@Entity
@Data
public class Vaga implements Serializable {

  private final static long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "Name cannot be empty")
  private String name;

  @NotNull(message = "Status cannot be empty")
  @Enumerated(EnumType.STRING)
  private StatusVagasEnum status;

  @OneToMany(mappedBy = "vaga")
  @JsonIgnoreProperties(value = { "vaga" })
  private List<Candidato> candidatos;
}
