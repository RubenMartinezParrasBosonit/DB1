package io.github.ruben.alumnosEstudios.infrastructure.controller.dto.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AlumnosEstudiosOutputDtoList {
    private List<AlumnosEstudiosOutputDto> alumnosEstudiosOutputDtoList;
}
