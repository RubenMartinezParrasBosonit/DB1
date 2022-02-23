package io.github.ruben.profesor.infrastructure.controller.dto.output;

import io.github.ruben.profesor.domain.Profesor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProfesorOutputDto implements Serializable {
    private String idProfesor;
    private String idPersona;
    private String comments;
    private String branch;
    private List<String> students = new ArrayList();

    public ProfesorOutputDto(Profesor profesor){
        if(profesor==null)
            return;
        setIdProfesor(profesor.getIdProfesor());
        setIdPersona(String.valueOf(profesor.getIdPersona().getId_persona()));
        setComments(profesor.getComments());
        setBranch(profesor.getBranch());
        if(profesor.getStudents().size()!=0){
            for(int i = 0; i < profesor.getStudents().size(); i++){
                students.add(profesor.getStudents().get(i).getIdStudent());
            }
        }
    }
}
