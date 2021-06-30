package br.com.devakc.backendtotvs.domain.services;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface BaseServiceInterface<T> {

  List<T> findAll();

  Optional<T> get(Long id);

  Long createOrUpdate(T object);

  void delete(Long id);

}
