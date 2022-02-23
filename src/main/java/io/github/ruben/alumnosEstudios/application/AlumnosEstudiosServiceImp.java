package io.github.ruben.alumnosEstudios.application;

import io.github.ruben.alumnosEstudios.domain.AlumnosEstudios;
import io.github.ruben.alumnosEstudios.infrastructure.controller.dto.input.AlumnosEstudiosInputDto;
import io.github.ruben.alumnosEstudios.infrastructure.controller.dto.output.AlumnosEstudiosOutputDto;
import io.github.ruben.alumnosEstudios.infrastructure.controller.dto.output.AlumnosEstudiosOutputDtoList;
import io.github.ruben.alumnosEstudios.infrastructure.repository.jpa.AlumnosEstudiosRepositorio;
import io.github.ruben.shared.exceptions.IdNotFoundException;
import io.github.ruben.student.domain.Student;
import io.github.ruben.student.infrastructure.repository.jpa.StudentRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlumnosEstudiosServiceImp implements AlumnosEstudiosService{

    @Autowired
    StudentRepositorio studentRepositorio;

    @Autowired
    AlumnosEstudiosRepositorio alumnosEstudiosRepositorio;

    @Override
    public AlumnosEstudiosOutputDtoList findAll(){
        List<AlumnosEstudios> alumnosEstudios = alumnosEstudiosRepositorio.findAll();
        List<AlumnosEstudiosOutputDto> alumnosEstudiosOutputDto = new ArrayList<>();
        AlumnosEstudiosOutputDtoList alumnosEstudiosOutputDtoList = new AlumnosEstudiosOutputDtoList();
        for (AlumnosEstudios alumnosEstudio : alumnosEstudios) {
            alumnosEstudiosOutputDto.add(new AlumnosEstudiosOutputDto(alumnosEstudio));
        }
        alumnosEstudiosOutputDtoList.setAlumnosEstudiosOutputDtoList(alumnosEstudiosOutputDto);
        return alumnosEstudiosOutputDtoList;
    }

    @Override
    public AlumnosEstudiosOutputDto filtrarAlumnosEstudiosPorId(String id) {
        AlumnosEstudios alumnosEstudios =
                alumnosEstudiosRepositorio.findById(id).orElseThrow(() -> new IdNotFoundException("Estudio con id: "+id+ " no encontrado"));
        AlumnosEstudiosOutputDto alumnosEstudiosOutputDto = new AlumnosEstudiosOutputDto(alumnosEstudios);
        return alumnosEstudiosOutputDto;
    }

    @Override
    public AlumnosEstudiosInputDto aniadirAlumnosEstudios(AlumnosEstudiosInputDto alumnosEstudiosInputDto) {

        AlumnosEstudios alumnosEstudios = alumnosEstudiosInputDtoToAlumnosEstudios(alumnosEstudiosInputDto);
        alumnosEstudiosRepositorio.saveAndFlush(alumnosEstudios);

        return alumnosEstudiosInputDto;
    }

    @Override
    public AlumnosEstudiosOutputDto modificarAlumnosEstudios(String id, AlumnosEstudiosInputDto alumnosEstudiosInputDto) {
        AlumnosEstudios alumnosEstudios =
                alumnosEstudiosRepositorio
                        .findById(id)
                        .orElseThrow(() -> new IdNotFoundException("Estudio con id: "+id+ " no encontrado"));

        AlumnosEstudiosOutputDto alumnosEstudiosOutputDto = modificacionAlumnosEstudios(id, alumnosEstudios, alumnosEstudiosInputDto);
        alumnosEstudios = alumnosEstudiosOutputDtoToAlumnosEstudios(alumnosEstudiosOutputDto);
        alumnosEstudiosRepositorio.saveAndFlush(alumnosEstudios);
        return alumnosEstudiosOutputDto;
    }

    @Override
    public void borrarAlumnosEstudios(String id) {
        alumnosEstudiosRepositorio.delete(
                alumnosEstudiosRepositorio
                        .findById(id)
                        .orElseThrow(() -> new IdNotFoundException("Estudio con id: "+id+ " no encontrado")));
    }


    private AlumnosEstudios alumnosEstudiosInputDtoToAlumnosEstudios(AlumnosEstudiosInputDto alumnosEstudiosInputDto){
        AlumnosEstudios alumnosEstudios = new AlumnosEstudios();
        alumnosEstudios.setEstudio(alumnosEstudiosInputDto.getEstudio());
        alumnosEstudios.setComments(alumnosEstudiosInputDto.getComments());
        alumnosEstudios.setInitialDate(alumnosEstudiosInputDto.getInitialDate());
        alumnosEstudios.setFinishDate(alumnosEstudiosInputDto.getFinishDate());

        List<Student> estudiantes = new ArrayList<>();
        for (int i = 0; i < alumnosEstudiosInputDto.getStudents().size(); i++){
            estudiantes.add(studentRepositorio.findById(alumnosEstudiosInputDto.getStudents().get(i)).orElseThrow(()-> new IdNotFoundException("Estudiante introducido no válido")));
        }
        alumnosEstudios.setStudents(estudiantes);

        return alumnosEstudios;
    }

    private AlumnosEstudios alumnosEstudiosOutputDtoToAlumnosEstudios(AlumnosEstudiosOutputDto alumnosEstudiosOutputDto){
        AlumnosEstudios alumnosEstudios = new AlumnosEstudios();
        alumnosEstudios.setIdEstudio(alumnosEstudiosOutputDto.getIdEstudio());
        alumnosEstudios.setEstudio(alumnosEstudiosOutputDto.getEstudio());
        alumnosEstudios.setComments(alumnosEstudiosOutputDto.getComments());
        alumnosEstudios.setInitialDate(alumnosEstudiosOutputDto.getInitialDate());
        alumnosEstudios.setFinishDate(alumnosEstudiosOutputDto.getFinishDate());

        List<Student> estudiantes = new ArrayList<>();
        for (int i = 0; i < alumnosEstudiosOutputDto.getStudents().size(); i++){
            estudiantes.add(studentRepositorio.findById(alumnosEstudiosOutputDto.getStudents().get(i)).orElseThrow(()-> new IdNotFoundException("Estudiante introducido no válido")));
        }
        alumnosEstudios.setStudents(estudiantes);

        return alumnosEstudios;
    }

    private AlumnosEstudiosOutputDto modificacionAlumnosEstudios(String id, AlumnosEstudios alumnosEstudios, AlumnosEstudiosInputDto alumnosEstudiosInputDto){
        AlumnosEstudiosOutputDto alumnosEstudiosOutputDto = new AlumnosEstudiosOutputDto(alumnosEstudios);

        if (alumnosEstudiosInputDto.getEstudio() != null){
            alumnosEstudiosOutputDto.setEstudio(alumnosEstudiosInputDto.getEstudio());
        }

        if (alumnosEstudiosInputDto.getComments() != null){
            alumnosEstudiosOutputDto.setComments(alumnosEstudiosInputDto.getComments());
        }

        if (alumnosEstudiosInputDto.getInitialDate() != null){
            alumnosEstudiosOutputDto.setInitialDate(alumnosEstudiosInputDto.getInitialDate());
        }

        if (alumnosEstudiosInputDto.getFinishDate() != null){
            alumnosEstudiosOutputDto.setFinishDate(alumnosEstudiosInputDto.getFinishDate());
        }

        if (alumnosEstudiosInputDto.getStudents() != null){
            List<String> students = alumnosEstudiosInputDto.getStudents();
            for(int i = 0; i < students.size(); i++){
                studentRepositorio.findById(students.get(i)).orElseThrow(()->new IdNotFoundException("Student con id: "+id+ " no válido"));
            }
            alumnosEstudiosOutputDto.setStudents(alumnosEstudiosInputDto.getStudents());
        }

        return alumnosEstudiosOutputDto;
    }
}
