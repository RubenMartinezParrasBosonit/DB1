package io.github.ruben.persona.infrastructure.controller.dto.input;

import io.github.ruben.persona.domain.Persona;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class PersonaInputDto implements Serializable  {
    private String usuario;
    private String password;
    private String name;
    private String surname;
    private String company_email;
    private String personal_email;
    private String city;
    private Boolean active;
    private Date created_date;
    private String imagen_url;
    private Date termination_date;

    public PersonaInputDto(Persona persona){
        if(persona==null)
            return;
        setUsuario(persona.getUsuario());
        setPassword(persona.getPassword());
        setName(persona.getName());
        setSurname(persona.getSurname());
        setCompany_email(persona.getCompany_email());
        setPersonal_email(persona.getPersonal_email());
        setCity(persona.getCity());
        setActive(persona.getActive());
        setCreated_date(persona.getCreated_date());
        setImagen_url(persona.getImagen_url());
        setTermination_date(persona.getTermination_date());
    }
}
