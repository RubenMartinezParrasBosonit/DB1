package io.github.ruben.alumnosEstudios.infrastructure.controller.dto.output;

import io.github.ruben.alumnosEstudios.domain.AlumnosEstudios;
import io.github.ruben.student.domain.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AlumnosEstudiosOutputDto implements Serializable {
    private String idEstudio;
    private List<String> students;
    private String estudio;
    private String comments;
    private Date initialDate;
    private Date finishDate;

    public AlumnosEstudiosOutputDto(AlumnosEstudios alumnosEstudios){
        if(alumnosEstudios==null)
            return;
        setIdEstudio(alumnosEstudios.getIdEstudio());

        List<String> studentsaux = new ArrayList<>();
        if(alumnosEstudios.getStudents().size()!=0){
            for(int i = 0; i < alumnosEstudios.getStudents().size(); i++){
                studentsaux.add(alumnosEstudios.getStudents().get(i).getIdStudent());
            }
        }
        setStudents(studentsaux);

        setEstudio(alumnosEstudios.getEstudio());
        setComments(alumnosEstudios.getComments());
        setInitialDate(alumnosEstudios.getInitialDate());
        setFinishDate(alumnosEstudios.getFinishDate());
    }
}
