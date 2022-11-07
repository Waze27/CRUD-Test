package com.crudtest.crudTest_01;

import com.crudtest.crudTest_01.entities.Student;
import com.crudtest.crudTest_01.repositories.StudentRepository;
import com.crudtest.crudTest_01.services.BasicService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles(value = "test")
public class ServiceTest {

    @Autowired
    private BasicService basicService;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void checkStudentActivation() {
        Student student = new Student();
        student.setId(1);
        student.setName("Mirko");
        student.setSurname("Di Cristina");
        student.setWorking(true);

        Student studentFromDB = studentRepository.save(student);
        assertThat(studentFromDB).isNotNull();
        assertThat(studentFromDB.getId()).isNotNull();

        Student studentFromService = basicService.setStatus(student.getId(), true);
        assertThat(studentFromService).isNotNull();
        assertThat(studentFromService.getId()).isNotNull();
        assertThat(studentFromService.isWorking()).isTrue();

        Student studentFromFind = studentRepository.findById(studentFromDB.getId()).get();
        Assertions.assertThat(studentFromFind).isNotNull();
        Assertions.assertThat(studentFromFind.getId()).isNotNull();
        Assertions.assertThat(studentFromFind.getId()).isEqualTo(studentFromDB.getId());
        Assertions.assertThat(studentFromFind.isWorking()).isTrue();

    }
}
