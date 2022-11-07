package com.crudtest.crudTest_01.controllers;

import com.crudtest.crudTest_01.entities.Student;
import com.crudtest.crudTest_01.repositories.StudentRepository;
import com.crudtest.crudTest_01.services.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class ControllerStudent {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BasicService basicService;

    @PostMapping("")
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @GetMapping("")
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable long id) {
        Optional<Student> findStudent = studentRepository.findById(id);
        if(findStudent.isPresent()){
            return findStudent.get();
        } else {
            return null;
        }
    }

    @PutMapping("/{id}")
    public Student editStudent(@PathVariable long id, @RequestBody Student student) {
        student.setId(id);
        return studentRepository.save(student);
    }

    @PutMapping("/{id}/status")
    public Student editWorking(@PathVariable long id, @RequestParam boolean working) {
        return basicService.setStatus(id, working);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable long id) {
        studentRepository.deleteById(id);
    }
}
