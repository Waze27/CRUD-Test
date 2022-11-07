package com.crudtest.crudTest_01.repositories;

import com.crudtest.crudTest_01.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
