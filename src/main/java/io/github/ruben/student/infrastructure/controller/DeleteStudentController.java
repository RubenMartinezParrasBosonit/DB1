package io.github.ruben.student.infrastructure.controller;

import io.github.ruben.student.application.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("student")
@RestController
public class DeleteStudentController {
    @Autowired
    StudentService studentService;

    @DeleteMapping("{id}")
    public void borrarStudent(@PathVariable String id){
        studentService.borrarStudent(id);
    }
}
