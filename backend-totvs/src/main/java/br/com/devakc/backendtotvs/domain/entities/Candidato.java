package br.com.devakc.backendtotvs.domain.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Entity
@Data
public class Candidato implements Serializable {

  private final static long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "Name cannot be empty")
  private String name;

  @NotEmpty(message = "Mail cannot be empty")
  private String mail;

  @NotEmpty(message = "Phone cannot be empty")
  private String phone;

  @OneToOne()
  @JoinColumn(name = "file_id")
  @JsonIgnoreProperties(value = { "url", "size" })
  private File file;

  @OneToOne
  @JoinColumn(name = "vaga_id")
  @JsonIgnoreProperties(value = { "name", "status", "candidatos" })
  private Vaga vaga;

}
