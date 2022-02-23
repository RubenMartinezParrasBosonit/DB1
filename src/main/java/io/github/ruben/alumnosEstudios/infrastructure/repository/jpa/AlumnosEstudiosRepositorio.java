package io.github.ruben.alumnosEstudios.infrastructure.repository.jpa;

import io.github.ruben.alumnosEstudios.domain.AlumnosEstudios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnosEstudiosRepositorio extends JpaRepository<AlumnosEstudios, String> {
}
