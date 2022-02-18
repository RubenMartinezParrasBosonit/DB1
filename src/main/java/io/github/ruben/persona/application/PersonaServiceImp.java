package io.github.ruben.persona.application;

import io.github.ruben.persona.exceptions.IdNotFoundException;
import io.github.ruben.persona.exceptions.UnprocesableException;
import io.github.ruben.persona.infrastructure.controller.dto.input.PersonaInputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDto;
import io.github.ruben.persona.infrastructure.repository.jpa.PersonaRepositorio;
import io.github.ruben.persona.domain.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PersonaServiceImp implements PersonaService {

  @Autowired PersonaRepositorio personaRepositorio;

  @Override
  public List<PersonaOutputDto> todasLasPersonas() {
    List<Persona> personas = personaRepositorio.findAll();
    List<PersonaOutputDto> personasOutputDto = new ArrayList<>();
    for (Persona persona : personas) {
      personasOutputDto.add(new PersonaOutputDto(persona));
    }
    return personasOutputDto;
  }

  @Override
  public PersonaOutputDto filtrarPersonasPorId(int id) {
    Persona persona =
        personaRepositorio.findById(id).orElseThrow(() -> new IdNotFoundException("Persona con id: "+id+ " no encontrado"));
    PersonaOutputDto personaOutputDto = new PersonaOutputDto(persona);
    return personaOutputDto;
  }

  @Override
  public List<PersonaOutputDto> filtrarPersonaPorNombreUsuario(String usuario) {
    List<Persona> personas = personaRepositorio.findByUsuario(usuario);
    if (personas.size() == 0) throw new NoSuchElementException("Usuario no encontrado");
    List<PersonaOutputDto> personasOutputDto = new ArrayList<>();
    for (Persona persona : personas) {
      personasOutputDto.add(new PersonaOutputDto(persona));
    }
    return personasOutputDto;
  }

  @Override
  public PersonaOutputDto aniadirPersona(PersonaInputDto personaInputDto) {

    if (personaInputDto.getUsuario() == null)
      throw new UnprocesableException("Usuario no puede ser nulo");
    if (personaInputDto.getUsuario().length() < 6 || personaInputDto.getUsuario().length() > 10) {
      throw new UnprocesableException("La longitud del nombre de usuario no está entre 6 y 10");
    }
    if (personaInputDto.getPassword() == null) {
      throw new UnprocesableException("Password no puede ser nulo");
    }
    if (personaInputDto.getName() == null) {
      throw new UnprocesableException("Name no puede ser nulo");
    }
    if (personaInputDto.getCompany_email() == null) {
      throw new UnprocesableException("Company_email no puede ser nulo");
    }
    if (personaInputDto.getPersonal_email() == null) {
      throw new UnprocesableException("Personal_email no puede ser nulo");
    }
    if (personaInputDto.getCity() == null) {
      throw new UnprocesableException("City no puede ser nulo");
    }
    if (personaInputDto.getActive() == null) {
      throw new UnprocesableException("Password no puede ser nulo");
    }
    if (personaInputDto.getCreated_date() == null) {
      throw new UnprocesableException("Created_date no puede ser nulo");
    }

    PersonaOutputDto personaOutputDto = personaInputDtoToPersonaOutputDto(personaInputDto);
    Persona persona = personaOutputDtoToEntity(personaOutputDto);
    personaRepositorio.saveAndFlush(persona);

    return personaOutputDto;
  }

  @Override
  public PersonaOutputDto modificarPersona(Integer id, PersonaInputDto personaInputDto)
      throws Exception {
    Persona persona =
        personaRepositorio
            .findById(id)
            .orElseThrow(() -> new IdNotFoundException("Persona con id: "+id+ " no encontrado"));
    PersonaOutputDto personaOutputDto = new PersonaOutputDto(persona);

    if (personaInputDto.getUsuario() != null) {
      if (personaInputDto.getUsuario().length() < 6 || personaInputDto.getUsuario().length() > 10) {
        throw new UnprocesableException("La longitud del nombre de usuario no está entre 6 y 10");
      }
      personaOutputDto.setUsuario(personaInputDto.getUsuario());
    }

    if (personaInputDto.getPassword() != null) {
      personaOutputDto.setPassword(personaInputDto.getPassword());
    }

    if (personaInputDto.getName() != null) {
      personaOutputDto.setName(personaInputDto.getName());
    }

    if (personaInputDto.getSurname() != null) {
      personaOutputDto.setSurname(personaInputDto.getSurname());
    }

    if (personaInputDto.getCompany_email() != null) {
      personaOutputDto.setCompany_email(personaInputDto.getCompany_email());
    }

    if (personaInputDto.getPersonal_email() != null) {
      personaOutputDto.setPersonal_email(personaInputDto.getPersonal_email());
    }

    if (personaInputDto.getCity() != null) {
      personaOutputDto.setCity(personaInputDto.getCity());
    }

    if (personaInputDto.getActive() != null) {
      personaOutputDto.setActive(personaInputDto.getActive());
    }

    if (personaInputDto.getCreated_date() != null) {
      personaOutputDto.setCreated_date(personaInputDto.getCreated_date());
    }

    if (personaInputDto.getImagen_url() != null) {
      personaOutputDto.setImagen_url(personaInputDto.getImagen_url());
    }

    if (personaInputDto.getTermination_date() != null) {
      personaOutputDto.setTermination_date(personaInputDto.getTermination_date());
    }

    persona = personaOutputDtoToEntity(personaOutputDto);
    personaRepositorio.saveAndFlush(persona);
    return personaOutputDto;
  }

  @Override
  public void borrarPersona(Integer id) {
    personaRepositorio.delete(
        personaRepositorio
            .findById(id)
            .orElseThrow(() -> new IdNotFoundException("Persona con id: "+id+ " no encontrado")));
  }

  private Persona personaOutputDtoToEntity(PersonaOutputDto personaOutputDto) {
    Persona persona = new Persona();
    persona.setId_persona(personaOutputDto.getId_persona());
    persona.setUsuario(personaOutputDto.getUsuario());
    persona.setPassword(personaOutputDto.getPassword());
    persona.setName(personaOutputDto.getName());
    persona.setSurname(personaOutputDto.getSurname());
    persona.setCompany_email(personaOutputDto.getCompany_email());
    persona.setPersonal_email(personaOutputDto.getPersonal_email());
    persona.setCity(personaOutputDto.getCity());
    persona.setActive(personaOutputDto.getActive());
    persona.setCreated_date(personaOutputDto.getCreated_date());
    persona.setImagen_url(personaOutputDto.getImagen_url());
    persona.setTermination_date(personaOutputDto.getTermination_date());

    return persona;
  }

  private PersonaOutputDto personaInputDtoToPersonaOutputDto(PersonaInputDto personaInputDto) {
    PersonaOutputDto personaOutputDto = new PersonaOutputDto();
    personaOutputDto.setUsuario(personaInputDto.getUsuario());
    personaOutputDto.setPassword(personaInputDto.getPassword());
    personaOutputDto.setName(personaInputDto.getName());
    personaOutputDto.setSurname(personaInputDto.getSurname());
    personaOutputDto.setCompany_email(personaInputDto.getCompany_email());
    personaOutputDto.setPersonal_email(personaInputDto.getPersonal_email());
    personaOutputDto.setCity(personaInputDto.getCity());
    personaOutputDto.setActive(personaInputDto.getActive());
    personaOutputDto.setCreated_date(personaInputDto.getCreated_date());
    personaOutputDto.setImagen_url(personaInputDto.getImagen_url());
    personaOutputDto.setTermination_date(personaInputDto.getTermination_date());

    return personaOutputDto;
  }
}
