package io.github.ruben.student.application;

import io.github.ruben.student.infrastructure.controller.dto.input.StudentInputDto;
import io.github.ruben.student.infrastructure.controller.dto.output.StudentOutputDto;
import io.github.ruben.student.infrastructure.controller.dto.output.StudentOutputDtoList;

import java.util.List;

public interface StudentService {
    public StudentOutputDtoList findAll();
    public StudentOutputDto filtrarStudentPorId(String id, String outputType);
    public StudentInputDto aniadirStudent(StudentInputDto studentInputDto);
    public StudentOutputDto modificarStudent(String id, StudentInputDto studentInputDto);
    public void borrarStudent(String id);
    public List<String> asignarAsignaturas(String id, List<String> asignaturas);
    public List<String> desasignarAsignaturas(String id, List<String> asignaturas);
}
