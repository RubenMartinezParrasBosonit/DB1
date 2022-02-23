package io.github.ruben.persona.infrastructure.controller.dto.output;

import io.github.ruben.persona.domain.Persona;
import io.github.ruben.profesor.domain.Profesor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PersonaProfesorOutputDto extends PersonaOutputDto{
    private String idProfesor;
    private String comments;
    private String branch;
    private List<String> students;

    public PersonaProfesorOutputDto(Persona persona, Profesor profesor){
        super(persona);
        setIdProfesor(profesor.getIdProfesor());
        setComments(profesor.getComments());
        setBranch(profesor.getBranch());

        List<String> studentsaux= new ArrayList<>();
        for(int i = 0; i < profesor.getStudents().size(); i++){
            studentsaux.add(profesor.getStudents().get(i).getIdStudent());
        }
        setStudents(studentsaux);

    }
}
