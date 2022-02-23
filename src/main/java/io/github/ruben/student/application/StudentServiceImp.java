package io.github.ruben.student.application;

import io.github.ruben.alumnosEstudios.domain.AlumnosEstudios;
import io.github.ruben.alumnosEstudios.infrastructure.repository.jpa.AlumnosEstudiosRepositorio;
import io.github.ruben.persona.domain.Persona;
import io.github.ruben.persona.infrastructure.repository.jpa.PersonaRepositorio;
import io.github.ruben.profesor.domain.Profesor;
import io.github.ruben.profesor.infrastructure.repository.jpa.ProfesorRepositorio;
import io.github.ruben.shared.exceptions.IdNotFoundException;
import io.github.ruben.shared.exceptions.UnprocesableException;
import io.github.ruben.student.domain.Student;
import io.github.ruben.student.infrastructure.controller.dto.input.StudentInputDto;
import io.github.ruben.student.infrastructure.controller.dto.output.StudentFullOutputDto;
import io.github.ruben.student.infrastructure.controller.dto.output.StudentOutputDto;
import io.github.ruben.student.infrastructure.controller.dto.output.StudentOutputDtoList;
import io.github.ruben.student.infrastructure.repository.jpa.StudentRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImp implements StudentService{

    @Autowired
    ProfesorRepositorio profesorRepositorio;

    @Autowired
    PersonaRepositorio personaRepositorio;

    @Autowired
    StudentRepositorio studentRepositorio;


    @Autowired
    AlumnosEstudiosRepositorio alumnosEstudiosRepositorio;

    @Override
    public StudentOutputDtoList findAll(){
        List<Student> students = studentRepositorio.findAll();
        List<StudentOutputDto> studentsOutputDto = new ArrayList<>();
        StudentOutputDtoList studentsOutputDtoList = new StudentOutputDtoList();
        for (Student student : students) {
            studentsOutputDto.add(new StudentOutputDto(student));
        }
        studentsOutputDtoList.setStudentOutputDtoList(studentsOutputDto);
        return studentsOutputDtoList;
    }

    @Override
    public StudentOutputDto filtrarStudentPorId(String id, String outputType) {
        Student student =
                studentRepositorio.findById(id).orElseThrow(() -> new IdNotFoundException("Student con id: "+id+ " no encontrado"));
        if(outputType.equalsIgnoreCase("full")){
            StudentFullOutputDto studentFullOutputDto  = new StudentFullOutputDto(student);
            return studentFullOutputDto;
        }else{
            StudentOutputDto studentOutputDto = new StudentOutputDto(student);
            return studentOutputDto;
        }

    }

    @Override
    public StudentInputDto aniadirStudent(StudentInputDto studentInputDto) {

        comprobarSiPersonaAsignada(studentInputDto);

        Student student = studentInputDtoToStudent(studentInputDto);
        studentRepositorio.saveAndFlush(student);

        return studentInputDto;
    }

    @Override
    public StudentOutputDto modificarStudent(String id, StudentInputDto studentInputDto) {
        Student student =
                studentRepositorio
                        .findById(id)
                        .orElseThrow(() -> new IdNotFoundException("Student con id: "+id+ " no encontrado"));

        StudentOutputDto studentOutputDto = modificacionStudent(id, student, studentInputDto);
        student = studentOutputDtoToStudent(studentOutputDto);
        studentRepositorio.saveAndFlush(student);
        return studentOutputDto;
    }

    @Override
    public void borrarStudent(String id) {
        studentRepositorio.delete(
                studentRepositorio
                        .findById(id)
                        .orElseThrow(() -> new IdNotFoundException("Student con id: "+id+ " no encontrado")));
    }

    @Override
    public List<String> asignarAsignaturas(String id, List<String> asignaturas){
        Student student =
        studentRepositorio
            .findById(id)
            .orElseThrow(() -> new IdNotFoundException("Student con id: "+id+ " no encontrado"));
        List<AlumnosEstudios> asignaturasaux = student.getEstudios();
        boolean encontrado = false;
        for(int i = 0; i < asignaturas.size(); i++){
            for(int j = 0; j < asignaturasaux.size() && !encontrado; j++){
                if(asignaturasaux.get(j).getIdEstudio().equals(asignaturas.get(i))){
                    encontrado = true;
                }
            }
            if(!encontrado){
                asignaturasaux.add(alumnosEstudiosRepositorio.findById(asignaturas.get(i)).orElseThrow());
            }
        }

        student.setEstudios(asignaturasaux);
        studentRepositorio.saveAndFlush(student);

        List<String> asignaturasMostrar = new ArrayList<>();
        for(int i = 0; i < asignaturasaux.size(); i++){
            asignaturasMostrar.add(asignaturasaux.get(i).getIdEstudio());
        }

        return asignaturasMostrar;
    }

    @Override
    public List<String> desasignarAsignaturas(String id, List<String> asignaturas){
        Student student =
                studentRepositorio
                        .findById(id)
                        .orElseThrow(() -> new IdNotFoundException("Student con id: "+id+ " no encontrado"));

        List<AlumnosEstudios> asignaturasaux = student.getEstudios();
        boolean encontrado = false;
        for(int i = 0; i < asignaturas.size(); i++){
            for(int j = 0; j < asignaturasaux.size() && !encontrado; j++){
                if(asignaturasaux.get(j).getIdEstudio().equals(asignaturas.get(i))){
                    asignaturasaux.remove(j);
                    encontrado = true;
                }
            }
            encontrado = false;
        }

        student.setEstudios(asignaturasaux);
        studentRepositorio.saveAndFlush(student);

        List<String> asignaturasMostrar = new ArrayList<>();
        for(int i = 0; i < asignaturasaux.size(); i++){
            asignaturasMostrar.add(asignaturasaux.get(i).getIdEstudio());
        }

        return asignaturasMostrar;
    }

    private Student studentInputDtoToStudent(StudentInputDto studentInputDto){
        Student student = new Student();

        student.setIdPersona(personaRepositorio.findById(Integer.valueOf(studentInputDto.getIdPersona())).orElseThrow(()->new IdNotFoundException("Persona introducida no válida")));
        student.setNumHoursWeek(studentInputDto.getNumHoursWeek());
        student.setComments(studentInputDto.getComments());
        student.setBranch(studentInputDto.getBranch());
        student.setIdProfesor(profesorRepositorio.findById(studentInputDto.getIdProfesor()).orElseThrow(()->new IdNotFoundException("Profesor introducido no válido")));

        List<AlumnosEstudios> alumnosEstudios = new ArrayList<>();
        for (int i = 0; i < studentInputDto.getEstudios().size(); i++){
            alumnosEstudios.add(alumnosEstudiosRepositorio.findById(studentInputDto.getEstudios().get(i)).orElseThrow(()-> new IdNotFoundException("Estudio introducido no válido")));
        }
        student.setEstudios(alumnosEstudios);
        return student;
    }

    private Student studentOutputDtoToStudent(StudentOutputDto studentOutputDto){
        Student student = new Student();
        student.setIdStudent(studentOutputDto.getIdStudent());
        student.setIdPersona(personaRepositorio.findById(Integer.valueOf(studentOutputDto.getIdPersona())).orElseThrow(()-> new IdNotFoundException("Persona introducida no válida")));
        student.setBranch(studentOutputDto.getBranch());
        student.setComments(studentOutputDto.getComments());
        student.setNumHoursWeek(studentOutputDto.getNumHoursWeek());
        student.setIdProfesor(profesorRepositorio.findById(studentOutputDto.getIdProfesor()).orElseThrow(()->new IdNotFoundException("Profesor introducido no válido")));

        List<AlumnosEstudios> estudios = new ArrayList<>();
        for (int i = 0; i < studentOutputDto.getEstudios().size(); i++){
            estudios.add(alumnosEstudiosRepositorio.findById(studentOutputDto.getEstudios().get(i)).orElseThrow(()-> new IdNotFoundException("Estudio introducido no válido")));
        }
        student.setEstudios(estudios);

        return student;
    }

    private void comprobarSiPersonaAsignada(StudentInputDto studentInputDto){
        List<Student> students = studentRepositorio.findAll();
        List<Profesor> profesores = profesorRepositorio.findAll();

        for (int i = 0; i < students.size(); i++){
            if(studentInputDto.getIdPersona().equals(String.valueOf(students.get(i).getIdPersona().getId_persona()))){
                throw new UnprocesableException("La persona "+studentInputDto.getIdPersona()+ " ya se encuentra asignada");
            }
        }

        for (int i = 0; i < profesores.size(); i++){
            if(studentInputDto.getIdPersona().equals(String.valueOf(profesores.get(i).getIdPersona().getId_persona()))){
                throw new UnprocesableException("La persona "+studentInputDto.getIdPersona()+ " ya se encuentra asignada");
            }
        }
    }

    private StudentOutputDto modificacionStudent(String id, Student student, StudentInputDto studentInputDto){
        StudentOutputDto studentOutputDto = new StudentOutputDto(student);

        if (studentInputDto.getIdPersona() != null) {
            Persona persona = personaRepositorio.findById(Integer.valueOf(studentInputDto.getIdPersona())).orElseThrow(()->new IdNotFoundException("Persona con id: "+id+ " no encontrada"));
            comprobarSiPersonaAsignada(studentInputDto);
            studentOutputDto.setIdPersona(persona.getId_persona().toString());
        }

        if (studentInputDto.getIdProfesor() != null) {
            Profesor profesor = profesorRepositorio.findById(studentInputDto.getIdProfesor()).orElseThrow(()->new IdNotFoundException("Profesor con id: "+id+ " no encontrado"));
            studentOutputDto.setIdProfesor(studentInputDto.getIdProfesor());
        }

        if (studentInputDto.getBranch() != null){
            studentOutputDto.setBranch(studentInputDto.getBranch());
        }

        if (studentInputDto.getComments() != null){
            studentOutputDto.setComments(studentInputDto.getComments());
        }

        if (studentInputDto.getNumHoursWeek() != null){
            studentOutputDto.setNumHoursWeek(studentInputDto.getNumHoursWeek());
        }

        if (studentInputDto.getEstudios() != null){
            List<String> estudios = studentInputDto.getEstudios();
            for(int i = 0; i < estudios.size(); i++){
                alumnosEstudiosRepositorio.findById(estudios.get(i)).orElseThrow(()->new IdNotFoundException("Estudio con id: "+id+ " no válido"));
            }
            studentOutputDto.setEstudios(studentInputDto.getEstudios());
        }

        return studentOutputDto;
    }
}
