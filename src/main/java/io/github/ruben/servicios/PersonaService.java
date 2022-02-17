package io.github.ruben.servicios;

import io.github.ruben.dtos.PersonaDTO;
import io.github.ruben.dtos.PersonaInputDTO;
import io.github.ruben.dtos.PersonaOutputDTO;
import io.github.ruben.interfaces.IPersona;
import io.github.ruben.interfaces.PersonaRepositorio;
import io.github.ruben.mappers.PersonaMapper;
import io.github.ruben.objetos.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PersonaService implements IPersona {

    @Autowired
    PersonaRepositorio personaRepositorio;

    @Override
    public List<PersonaOutputDTO> todasLasPersonas(){
        List<Persona> personas = personaRepositorio.findAll();
        List<PersonaOutputDTO> personasOutputDTO = new ArrayList<>();
        for(Persona persona : personas){
            personasOutputDTO.add(new PersonaOutputDTO(persona));
        }
        return personasOutputDTO;
    }

    @Override
    public PersonaOutputDTO filtrarPersonasPorId(int id){
        Persona persona = personaRepositorio.findById(id).orElseThrow(() ->new NoSuchElementException("Id no encontrado"));
        PersonaOutputDTO personaOutputDTO = new PersonaOutputDTO(persona);
        return personaOutputDTO;
    }

    @Override
    public List<PersonaOutputDTO> filtrarPersonaPorNombreUsuario(String usuario){
        List<Persona> personas = personaRepositorio.findByUsuario(usuario);
        if(personas.size()==0)
            throw new NoSuchElementException("Usuario no encontrado");
        List<PersonaOutputDTO> personasOutputDTO = new ArrayList<>();
        for(Persona persona : personas){
            personasOutputDTO.add(new PersonaOutputDTO(persona));
        }
        return personasOutputDTO;
    }

    @Override
    public PersonaOutputDTO aniadirPersona(PersonaInputDTO personaInputDTO) throws Exception {
        if(personaInputDTO.getUsuario()==null)
            throw new Exception("Usuario no puede ser nulo");
        if(personaInputDTO.getUsuario().length() < 6 || personaInputDTO.getUsuario().length() > 10){
            throw new Exception("La longitud del nombre de usuario no está entre 6 y 10");
        }
        if(personaInputDTO.getPassword() == null){
            throw new Exception("Password no puede ser nulo");
        }
        if(personaInputDTO.getName() == null){
            throw new Exception("Name no puede ser nulo");
        }
        if(personaInputDTO.getCompany_email() == null){
            throw new Exception("Company_email no puede ser nulo");
        }
        if(personaInputDTO.getPersonal_email() == null){
            throw new Exception("Personal_email no puede ser nulo");
        }
        if(personaInputDTO.getCity() == null){
            throw new Exception("City no puede ser nulo");
        }
        if(personaInputDTO.getActive()==null){
            throw new Exception("Password no puede ser nulo");
        }
        if(personaInputDTO.getCreated_date() == null){
            throw new Exception("Created_date no puede ser nulo");
        }

        PersonaOutputDTO personaOutputDTO = personaInputDTOToTersonaOutputDTO(personaInputDTO);
        Persona persona = personaOutputDTOtoEntity(personaOutputDTO);
        personaRepositorio.saveAndFlush(persona);

        return personaOutputDTO;
    }

    @Override
    public PersonaOutputDTO modificarPersona(Integer id, PersonaInputDTO personaInputDTO) throws Exception {
        Persona persona = personaRepositorio.findById(id).orElseThrow(() ->new NoSuchElementException("Id no encontrado"));
        PersonaOutputDTO personaOutputDTO = new PersonaOutputDTO(persona);

        if(personaInputDTO.getUsuario()!=null){
            if(personaInputDTO.getUsuario().length() < 6 || personaInputDTO.getUsuario().length() > 10){
                throw new Exception("La longitud del nombre de usuario no está entre 6 y 10");
            }
            personaOutputDTO.setUsuario(personaInputDTO.getUsuario());
        }

        if(personaInputDTO.getPassword() != null){
            personaOutputDTO.setPassword(personaInputDTO.getPassword());
        }

        if(personaInputDTO.getName() != null){
            personaOutputDTO.setName(personaInputDTO.getName());
        }

        if(personaInputDTO.getSurname() != null){
            personaOutputDTO.setSurname(personaInputDTO.getSurname());
        }

        if(personaInputDTO.getCompany_email() != null){
            personaOutputDTO.setCompany_email(personaInputDTO.getCompany_email());
        }

        if(personaInputDTO.getPersonal_email() != null){
            personaOutputDTO.setPersonal_email(personaInputDTO.getPersonal_email());
        }

        if(personaInputDTO.getCity() != null){
            personaOutputDTO.setCity(personaInputDTO.getCity());
        }

        if(personaInputDTO.getActive() != null){
            personaOutputDTO.setActive(personaInputDTO.getActive());
        }

        if(personaInputDTO.getCreated_date() != null){
            personaOutputDTO.setCreated_date(personaInputDTO.getCreated_date());
        }

        if(personaInputDTO.getImagen_url() != null){
            personaOutputDTO.setImagen_url(personaInputDTO.getImagen_url());
        }

        if(personaInputDTO.getTermination_date() != null){
            personaOutputDTO.setTermination_date(personaInputDTO.getTermination_date());
        }

        persona = personaOutputDTOtoEntity(personaOutputDTO);
        personaRepositorio.saveAndFlush(persona);
        return personaOutputDTO;
    }

    @Override
    public String borrarPersona(Integer id){
        personaRepositorio.delete(personaRepositorio.findById(id).orElseThrow(() ->new NoSuchElementException("Id no encontrado")));
        return "Persona borrada";
    }

    private Persona personaOutputDTOtoEntity(PersonaOutputDTO personaOutputDTO){
        Persona persona = new Persona();
        persona.setId_persona(personaOutputDTO.getId_persona());
        persona.setUsuario(personaOutputDTO.getUsuario());
        persona.setPassword(personaOutputDTO.getPassword());
        persona.setName(personaOutputDTO.getName());
        persona.setSurname(personaOutputDTO.getSurname());
        persona.setCompany_email(personaOutputDTO.getCompany_email());
        persona.setPersonal_email(personaOutputDTO.getPersonal_email());
        persona.setCity(personaOutputDTO.getCity());
        persona.setActive(personaOutputDTO.getActive());
        persona.setCreated_date(personaOutputDTO.getCreated_date());
        persona.setImagen_url(personaOutputDTO.getImagen_url());
        persona.setTermination_date(personaOutputDTO.getTermination_date());

        return persona;
    }

    private PersonaOutputDTO personaInputDTOToTersonaOutputDTO(PersonaInputDTO personaInputDTO){
        PersonaOutputDTO personaOutputDTO = new PersonaOutputDTO();
        personaOutputDTO.setId_persona(personaInputDTO.getId_persona());
        personaOutputDTO.setUsuario(personaInputDTO.getUsuario());
        personaOutputDTO.setPassword(personaInputDTO.getPassword());
        personaOutputDTO.setName(personaInputDTO.getName());
        personaOutputDTO.setSurname(personaInputDTO.getSurname());
        personaOutputDTO.setCompany_email(personaInputDTO.getCompany_email());
        personaOutputDTO.setPersonal_email(personaInputDTO.getPersonal_email());
        personaOutputDTO.setCity(personaInputDTO.getCity());
        personaOutputDTO.setActive(personaInputDTO.getActive());
        personaOutputDTO.setCreated_date(personaInputDTO.getCreated_date());
        personaOutputDTO.setImagen_url(personaInputDTO.getImagen_url());
        personaOutputDTO.setTermination_date(personaInputDTO.getTermination_date());

        return personaOutputDTO;
    }
}
