package io.github.ruben.student.infrastructure.controller.dto.output;

import io.github.ruben.student.domain.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudentOutputDto implements Serializable {
    private String idStudent;
    private String idPersona;
    private Integer numHoursWeek;
    private String comments;
    private String idProfesor;
    private String branch;
    List<String> estudios;

    public StudentOutputDto(Student student){
        if(student==null)
            return;
        setIdStudent(student.getIdStudent());
        setIdPersona(String.valueOf(student.getIdPersona().getId_persona()));
        setNumHoursWeek(student.getNumHoursWeek());
        setComments(student.getComments());
        setIdProfesor(student.getIdProfesor().getIdProfesor());
        setBranch(student.getBranch());

        List<String> estudiosaux = new ArrayList<>();
        if(student.getEstudios().size()!=0){
            for(int i = 0; i < student.getEstudios().size(); i++){
                estudiosaux.add(student.getEstudios().get(i).getIdEstudio());
            }
        }
        setEstudios(estudiosaux);
    }
}
