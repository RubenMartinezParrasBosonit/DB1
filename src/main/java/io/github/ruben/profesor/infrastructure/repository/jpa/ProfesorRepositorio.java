package io.github.ruben.profesor.infrastructure.repository.jpa;

import io.github.ruben.profesor.domain.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesorRepositorio extends JpaRepository<Profesor, String> {
}
