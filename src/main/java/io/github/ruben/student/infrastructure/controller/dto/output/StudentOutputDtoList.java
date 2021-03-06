package io.github.ruben.student.infrastructure.controller.dto.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentOutputDtoList {
    private List<StudentOutputDto> studentOutputDtoList;
}
