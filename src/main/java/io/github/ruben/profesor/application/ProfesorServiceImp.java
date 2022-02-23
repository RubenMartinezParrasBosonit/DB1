package io.github.ruben.profesor.application;

import io.github.ruben.persona.domain.Persona;
import io.github.ruben.persona.infrastructure.controller.dto.input.PersonaInputDto;
import io.github.ruben.persona.infrastructure.controller.dto.output.PersonaOutputDto;
import io.github.ruben.persona.infrastructure.repository.jpa.PersonaRepositorio;
import io.github.ruben.profesor.domain.Profesor;
import io.github.ruben.profesor.infrastructure.controller.dto.input.ProfesorInputDto;
import io.github.ruben.profesor.infrastructure.controller.dto.output.ProfesorOutputDto;
import io.github.ruben.profesor.infrastructure.controller.dto.output.ProfesorOutputDtoList;
import io.github.ruben.profesor.infrastructure.repository.jpa.ProfesorRepositorio;
import io.github.ruben.shared.exceptions.IdNotFoundException;
import io.github.ruben.shared.exceptions.UnprocesableException;
import io.github.ruben.student.domain.Student;
import io.github.ruben.student.infrastructure.repository.jpa.StudentRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfesorServiceImp implements ProfesorService{

    @Autowired
    ProfesorRepositorio profesorRepositorio;

    @Autowired
    PersonaRepositorio personaRepositorio;

    @Autowired
    StudentRepositorio studentRepositorio;

    @Override
    public ProfesorOutputDtoList findAll(){
        List<Profesor> profesores = profesorRepositorio.findAll();
        List<ProfesorOutputDto> profesoresOutputDto = new ArrayList<>();
        ProfesorOutputDtoList profesorOutputDtoList = new ProfesorOutputDtoList();
        for (Profesor profesor : profesores) {
            profesoresOutputDto.add(new ProfesorOutputDto(profesor));
        }
        profesorOutputDtoList.setProfesorOutputDtoList(profesoresOutputDto);
        return profesorOutputDtoList;
    }

    @Override
    public ProfesorOutputDto filtrarProfesorPorId(String id) {
        Profesor profesor =
                profesorRepositorio.findById(id).orElseThrow(() -> new IdNotFoundException("Profesor con id: "+id+ " no encontrado"));
        ProfesorOutputDto profesorOutputDto = new ProfesorOutputDto(profesor);
        return profesorOutputDto;
    }

    @Override
    public ProfesorInputDto aniadirProfesor(ProfesorInputDto profesorInputDto) {
        List<Student> students = studentRepositorio.findAll();
        List<Profesor> profesores = profesorRepositorio.findAll();

        for (int i = 0; i < students.size(); i++){
            if(profesorInputDto.getIdPersona().equals(String.valueOf(students.get(i).getIdPersona().getId_persona()))){
                throw new UnprocesableException("La persona "+profesorInputDto.getIdPersona()+ " ya se encuentra asignada");
            }
        }

        for (int i = 0; i < profesores.size(); i++){
            if(profesorInputDto.getIdPersona().equals(String.valueOf(profesores.get(i).getIdPersona().getId_persona()))){
                throw new UnprocesableException("La persona "+profesorInputDto.getIdPersona()+ " ya se encuentra asignada");
            }
        }

        Profesor profesor = profesorInputDtoToProfesor(profesorInputDto);
        profesorRepositorio.saveAndFlush(profesor);

        return profesorInputDto;
    }


    @Override
    public ProfesorOutputDto modificarProfesor(String id, ProfesorInputDto profesorInputDto) {
        Profesor profesor =
                profesorRepositorio
                        .findById(id)
                        .orElseThrow(() -> new IdNotFoundException("Profesor con id: "+id+ " no encontrado"));
        ProfesorOutputDto profesorOutputDto = new ProfesorOutputDto(profesor);


        if (profesorInputDto.getIdPersona() != null) {
            Persona persona = personaRepositorio.findById(Integer.valueOf(profesorInputDto.getIdPersona())).orElseThrow(()->new IdNotFoundException("Persona con id: "+id+ " no encontrada"));
            List<Student> students = studentRepositorio.findAll();
            List<Profesor> profesores = profesorRepositorio.findAll();

            for (int i = 0; i < students.size(); i++){
                if(profesorInputDto.getIdPersona().equals(String.valueOf(students.get(i).getIdPersona().getId_persona()))){
                    throw new UnprocesableException("La persona "+profesorInputDto.getIdPersona()+ " ya se encuentra asignada");
                }
            }

            for (int i = 0; i < profesores.size(); i++){
                if(profesorInputDto.getIdPersona().equals(String.valueOf(profesores.get(i).getIdPersona().getId_persona()))){
                    throw new UnprocesableException("La persona "+profesorInputDto.getIdPersona()+ " ya se encuentra asignada");
                }
            }
            profesorOutputDto.setIdPersona(String.valueOf(persona.getId_persona()));
        }

        if (profesorInputDto.getComments() != null) {
            profesorOutputDto.setComments(profesorInputDto.getComments());
        }

        if (profesorInputDto.getBranch() != null) {
            profesorOutputDto.setBranch(profesorInputDto.getBranch());
        }

        if(profesorInputDto.getStudents() != null){
            List<String> students = profesorInputDto.getStudents();
            for(int i = 0; i < students.size(); i++){
                studentRepositorio.findById(students.get(i)).orElseThrow(()->new IdNotFoundException("Student no válido"));
            }
            profesorOutputDto.setStudents(profesorInputDto.getStudents());
        }

        profesor = profesorOutputDtoToProfesor(profesorOutputDto);
        profesorRepositorio.saveAndFlush(profesor);
        return profesorOutputDto;
    }



    @Override
    public void borrarProfesor(String id) {
        profesorRepositorio.delete(
                profesorRepositorio
                        .findById(id)
                        .orElseThrow(() -> new IdNotFoundException("Profesor con id: "+id+ " no encontrado")));
    }

    private Profesor profesorInputDtoToProfesor(ProfesorInputDto profesorInputDto){
        Profesor profesor = new Profesor();
        profesor.setIdPersona(personaRepositorio.findById(Integer.valueOf(profesorInputDto.getIdPersona())).orElseThrow(() -> new IdNotFoundException("Persona introducida no válida")));
        profesor.setComments(profesorInputDto.getComments());
        profesor.setBranch(profesorInputDto.getBranch());

        List<Student> estudiantes = new ArrayList<>();
        for (int i = 0; i < profesorInputDto.getStudents().size(); i++){
            estudiantes.add(studentRepositorio.findById(profesorInputDto.getStudents().get(i)).orElseThrow(()-> new IdNotFoundException("Estudiante introducido no válido")));
        }
        profesor.setStudents(estudiantes);

        return profesor;
    }

    private Profesor profesorOutputDtoToProfesor(ProfesorOutputDto profesorOutputDto){
        Profesor profesor = new Profesor();
        profesor.setIdProfesor(profesorOutputDto.getIdProfesor());
        profesor.setIdPersona(personaRepositorio.findById(Integer.valueOf(profesorOutputDto.getIdPersona())).orElseThrow(()-> new IdNotFoundException("Persona introducida no válida")));
        profesor.setBranch(profesorOutputDto.getBranch());
        profesor.setComments(profesorOutputDto.getComments());

        List<Student> estudiantes = new ArrayList<>();
        for (int i = 0; i < profesorOutputDto.getStudents().size(); i++){
            estudiantes.add(studentRepositorio.findById(profesorOutputDto.getStudents().get(i)).orElseThrow(()-> new IdNotFoundException("Estudiante introducido no válida")));
        }
        profesor.setStudents(estudiantes);

        return profesor;
    }
}
