package io.github.ruben.profesor.infrastructure.controller.dto.input;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
public class ProfesorInputDto {

    @NonNull
    private String idPersona;

    private String comments;

    @NonNull
    private String branch;

    private List<String> students;
}
