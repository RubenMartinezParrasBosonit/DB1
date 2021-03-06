package io.github.ruben.profesor.infrastructure.controller.dto.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProfesorOutputDtoList {
    private List<ProfesorOutputDto> profesorOutputDtoList;
}
