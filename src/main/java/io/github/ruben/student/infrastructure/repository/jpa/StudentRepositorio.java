package io.github.ruben.student.infrastructure.repository.jpa;

import io.github.ruben.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepositorio extends JpaRepository<Student, String> {
}
