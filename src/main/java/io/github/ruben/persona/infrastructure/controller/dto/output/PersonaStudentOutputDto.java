package io.github.ruben.persona.infrastructure.controller.dto.output;

import io.github.ruben.persona.domain.Persona;
import io.github.ruben.student.domain.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PersonaStudentOutputDto extends PersonaOutputDto{
    private String idStudent;
    private Integer numHoursWeek;
    private String comments;
    private String idProfesor;
    private String branch;
    List<String> estudios;

    public PersonaStudentOutputDto(Persona persona, Student student){
        super(persona);
        setIdStudent(student.getIdStudent());
        setNumHoursWeek(student.getNumHoursWeek());
        setComments(student.getComments());
        setIdProfesor(student.getIdProfesor().getIdProfesor());
        setBranch(student.getBranch());

        List<String> estudiosaux= new ArrayList<>();
        for(int i = 0; i < student.getEstudios().size(); i++){
            estudiosaux.add(student.getEstudios().get(i).getIdEstudio());
        }
        setEstudios(estudiosaux);
    }
}
