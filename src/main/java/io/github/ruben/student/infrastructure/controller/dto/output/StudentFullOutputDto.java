package io.github.ruben.student.infrastructure.controller.dto.output;

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
public class StudentFullOutputDto extends StudentOutputDto {

    String usuario;
    String password;
    String name;
    String surname;
    String company_email;
    String personal_email;
    String city;
    Boolean active;
    Date created_date;
    String imagen_url;
    Date termination_date;

    public StudentFullOutputDto(Student student){
        super(student);
        setUsuario(student.getIdPersona().getUsuario());
        setPassword(student.getIdPersona().getPassword());
        setName(student.getIdPersona().getName());
        setSurname(student.getIdPersona().getSurname());
        setCompany_email(student.getIdPersona().getCompany_email());
        setPersonal_email(student.getIdPersona().getPersonal_email());
        setCity(student.getIdPersona().getCity());
        setActive(student.getIdPersona().getActive());
        setCreated_date(student.getIdPersona().getCreated_date());
        setImagen_url(student.getIdPersona().getImagen_url());
        setTermination_date(student.getIdPersona().getTermination_date());

    }
}
