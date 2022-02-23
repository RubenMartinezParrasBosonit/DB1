package io.github.ruben.alumnosEstudios.infrastructure.controller.dto.input;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class AlumnosEstudiosInputDto implements Serializable {

    @NonNull
    private List<String> students;

    @NonNull
    private String estudio;

    private String comments;

    @NonNull
    private Date initialDate;

    private Date finishDate;
}
