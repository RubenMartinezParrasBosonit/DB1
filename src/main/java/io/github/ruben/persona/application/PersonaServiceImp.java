package io.github.ruben.persona.application;

import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDtoList;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaProfesorOutputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaStudentOutputDto;
import io.github.ruben.profesor.domain.Profesor;
import io.github.ruben.profesor.infrastructure.repository.jpa.ProfesorRepositorio;
import io.github.ruben.shared.exceptions.IdNotFoundException;
import io.github.ruben.shared.exceptions.UnprocesableException;
import io.github.ruben.persona.infrastructure.controller.dto.input.PersonaInputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDto;
import io.github.ruben.persona.infrastructure.repository.jpa.PersonaRepositorio;
import io.github.ruben.persona.domain.Persona;
import io.github.ruben.student.domain.Student;
import io.github.ruben.student.infrastructure.repository.jpa.StudentRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PersonaServiceImp implements PersonaService, UserDetailsService {

  @Autowired PersonaRepositorio personaRepositorio;

  @Autowired StudentRepositorio studentRepositorio;

  @Autowired ProfesorRepositorio profesorRepositorio;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  public PersonaOutputDtoList findAll(String outputType) {
    List<Persona> personas = personaRepositorio.findAll();

    if (personas.size() == 0) throw new IdNotFoundException("Ninguna persona encontrada");
    List<PersonaOutputDto> personasOutputDto = new ArrayList<>();
    for (Persona persona : personas) {
      personasOutputDto.add(new PersonaOutputDto(persona));
    }

    return busquedaTipoPersona(outputType, personasOutputDto);

  }

  @Override
  public PersonaOutputDto filtrarPersonasPorId(int id, String outputType) {
    Persona persona =
        personaRepositorio.findById(id).orElseThrow(() -> new IdNotFoundException("Persona con id: "+id+ " no encontrado"));

    if(outputType.equalsIgnoreCase("full")){
      List<Student> estudiantes = studentRepositorio.findAll();
      for(int i = 0; i < estudiantes.size(); i++){
        if (estudiantes.get(i).getIdPersona().getId_persona().equals(persona.getId_persona())){
          return new PersonaStudentOutputDto(persona, estudiantes.get(i));
        }
      }

      List<Profesor> profesores = profesorRepositorio.findAll();
      for (int i = 0; i < profesores.size(); i++) {
        if (profesores.get(i).getIdPersona().getId_persona().equals(persona.getId_persona())) {
          return new PersonaProfesorOutputDto(persona, profesores.get(i));
        }
      }

      PersonaOutputDto personaOutputDto = new PersonaOutputDto(persona);
      return personaOutputDto;

    }else{
      PersonaOutputDto personaOutputDto = new PersonaOutputDto(persona);
      return personaOutputDto;
    }
  }

  @Override
  public PersonaOutputDtoList filtrarPersonaPorNombreUsuario(String usuario, String outputType) {
    List<Persona> personas = personaRepositorio.findByUsuario(usuario);


    if (personas.size() == 0) throw new IdNotFoundException("Usuario no encontrado");
      List<PersonaOutputDto> personasOutputDto = new ArrayList<>();
    for (Persona persona : personas) {
      personasOutputDto.add(new PersonaOutputDto(persona));
    }

    return busquedaTipoPersona(outputType, personasOutputDto);
  }

  @Override
  public PersonaOutputDto aniadirPersona(PersonaInputDto personaInputDto) {
    validacionAniadirPersona(personaInputDto);

    PersonaOutputDto personaOutputDto = personaInputDtoToPersonaOutputDto(personaInputDto);
    Persona persona = personaOutputDtoToEntity(personaOutputDto);
    persona.setPassword(passwordEncoder.encode(persona.getPassword()));
    personaRepositorio.saveAndFlush(persona);

    return personaOutputDto;
  }

  @Override
  public PersonaOutputDto modificarPersona(Integer id, PersonaInputDto personaInputDto){
    Persona persona =
        personaRepositorio
            .findById(id)
            .orElseThrow(() -> new IdNotFoundException("Persona con id: "+id+ " no encontrado"));

    PersonaOutputDto personaOutputDto = modificacionPersona(persona, personaInputDto);
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

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    List<Persona> listPersona = personaRepositorio.findByUsuario(username);
    Persona persona;
    if(listPersona == null){
      throw new UsernameNotFoundException("User not found");
    }else{
      persona = listPersona.get(0);
    }

    Collection<SimpleGrantedAuthority> auothorities = new ArrayList<>();
    if(persona.getAdmin()){
      auothorities.add(new SimpleGrantedAuthority("ADMIN"));
    }else{
      auothorities.add(new SimpleGrantedAuthority("USER"));
    }

    return new org.springframework.security.core.userdetails.User(persona.getUsuario(), persona.getPassword(), auothorities);
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
    persona.setAdmin(personaOutputDto.getAdmin());

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
    personaOutputDto.setAdmin(personaInputDto.getAdmin());

    return personaOutputDto;
  }

  private PersonaOutputDtoList busquedaTipoPersona(String outputType, List<PersonaOutputDto> personasOutputDto) {
    PersonaOutputDtoList personaOutputDtoList = new PersonaOutputDtoList();

    if (outputType.equalsIgnoreCase("estudiantes") || outputType.equalsIgnoreCase("full")) {
      List<Student> students = studentRepositorio.findAll();

      List<PersonaStudentOutputDto> personaStudentOutputDto = new ArrayList<>();
      boolean encontrado = false;
      for (int i = 0; i < personasOutputDto.size(); i++) {
        encontrado = false;
        for (int j = 0; j < students.size() && !encontrado; j++) {
          if (personasOutputDto
              .get(i)
              .getId_persona()
              .equals(students.get(j).getIdPersona().getId_persona())) {
            personaStudentOutputDto.add(
                new PersonaStudentOutputDto(
                    personaRepositorio
                        .findById(personasOutputDto.get(i).getId_persona())
                        .orElseThrow(),
                    students.get(j)));
            encontrado = true;
          }
        }
      }
      personaOutputDtoList.setPersonaStudentOutputDtoList(personaStudentOutputDto);
      if (outputType.equalsIgnoreCase("estudiantes")) {
        return personaOutputDtoList;
      }
    }

    if (outputType.equalsIgnoreCase("profesores") || outputType.equalsIgnoreCase("full")) {
      List<Profesor> profesores = profesorRepositorio.findAll();

      List<PersonaProfesorOutputDto> personaProfesorOutputDto = new ArrayList<>();
      boolean encontrado = false;
      for (int i = 0; i < personasOutputDto.size(); i++) {
        encontrado = false;
        for (int j = 0; j < profesores.size() && !encontrado; j++) {
          if (personasOutputDto
              .get(i)
              .getId_persona()
              .equals(profesores.get(j).getIdPersona().getId_persona())) {
            personaProfesorOutputDto.add(
                new PersonaProfesorOutputDto(
                    personaRepositorio
                        .findById(personasOutputDto.get(i).getId_persona())
                        .orElseThrow(),
                    profesores.get(j)));
            encontrado = true;
          }
        }
      }
      personaOutputDtoList.setPersonaProfesorOutputDtoList(personaProfesorOutputDto);

      if (outputType.equalsIgnoreCase("profesores")) {
        return personaOutputDtoList;
      }
    }

    if (outputType.equalsIgnoreCase("full")) {
      return personaOutputDtoList;
    }

    personaOutputDtoList.setPersonaOutputDtoList(personasOutputDto);
    return personaOutputDtoList;
    }

  private void validacionAniadirPersona(PersonaInputDto personaInputDto){
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
    if (personaInputDto.getAdmin() == null) {
      throw new UnprocesableException("Admin no puede ser nulo");
    }
  }

  private PersonaOutputDto modificacionPersona(Persona persona, PersonaInputDto personaInputDto){
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

    if (personaInputDto.getAdmin() != null) {
      personaOutputDto.setAdmin(personaInputDto.getAdmin());
    }

    return personaOutputDto;
  }


}
