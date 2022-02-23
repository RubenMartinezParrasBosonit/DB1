package io.github.ruben.persona.infrastructure.controller.dto.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PersonaOutputDtoList {
    private List<PersonaOutputDto> personaOutputDtoList;
    private List<PersonaStudentOutputDto> personaStudentOutputDtoList;
    private List<PersonaProfesorOutputDto> personaProfesorOutputDtoList;
}
