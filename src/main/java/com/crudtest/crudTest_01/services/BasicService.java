package com.crudtest.crudTest_01.services;

import com.crudtest.crudTest_01.entities.Student;
import com.crudtest.crudTest_01.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BasicService {

    @Autowired
    StudentRepository studentRepository;

    public Student setStatus(Long studentId, boolean isWorking) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (!student.isPresent()) return null;
        student.get().setWorking(isWorking);
        return studentRepository.save(student.get());
    }

}
