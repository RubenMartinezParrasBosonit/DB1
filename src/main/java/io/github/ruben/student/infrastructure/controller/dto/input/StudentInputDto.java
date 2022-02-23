package io.github.ruben.student.infrastructure.controller.dto.input;

import io.github.ruben.alumnosEstudios.domain.AlumnosEstudios;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor

public class StudentInputDto implements Serializable {

    @NonNull
    private String idPersona;

    @NonNull
    private Integer numHoursWeek;

    private String comments;

    @NonNull
    private String idProfesor;

    @NonNull
    private String branch;

    @NonNull
    List<String> estudios;
}
