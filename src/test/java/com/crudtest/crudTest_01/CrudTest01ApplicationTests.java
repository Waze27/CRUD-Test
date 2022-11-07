package com.crudtest.crudTest_01;

import com.crudtest.crudTest_01.controllers.ControllerStudent;
import com.crudtest.crudTest_01.entities.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc
class CrudTest01ApplicationTests {

    @Autowired
    private ControllerStudent controllerStudent;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
        assertThat(controllerStudent).isNotNull();
    }

    private Student createAStudent() throws Exception {
        Student student = new Student();
        student.setId(1);
        student.setName("Mirko");
        student.setSurname("Di Cristina");
        student.setWorking(true);

        return createAStudent(student);
    }

    private Student createAStudent(Student student) throws Exception {
        MvcResult result = createAStudentRequest(student);
        Student studentFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Student.class);

        Assertions.assertThat(studentFromResponse).isNotNull();
        Assertions.assertThat(studentFromResponse.getId()).isNotNull();

        return studentFromResponse;
    }

    private MvcResult createAStudentRequest(Student student) throws Exception {
        if (student == null) return null;
        String userJSON = objectMapper.writeValueAsString(student);

        return this.mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    private @Nullable Student getStudentFromId(Long id) throws Exception {
        MvcResult result = this.mockMvc.perform(get("/student/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        try {
            String studentJSON = result.getResponse().getContentAsString();
            Student student = objectMapper.readValue(studentJSON, Student.class);

            Assertions.assertThat(student).isNotNull();
            Assertions.assertThat(student.getId()).isNotNull();

            return student;
        } catch (Exception e) {
            return null;
        }
    }

    @Test
    void createStudentTest() throws Exception {
        createAStudent();
    }

    @Test
    void readStudentList() throws Exception {
        createStudentTest();

        MvcResult result = this.mockMvc.perform(get("/student/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<Student> studentsFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        assertThat(studentsFromResponse.size()).isNotNull();
    }

    @Test
    void readSingleStudent() throws Exception {
        createAStudent();
    }

    @Test
    void updateUser() throws Exception {
        Student student = createAStudent();

        String newName = "Franco";
        student.setName(newName);

        String studentJSON = objectMapper.writeValueAsString(student);

        MvcResult result = this.mockMvc.perform(put("/student/" + student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Student studentFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Student.class);

        Assertions.assertThat(studentFromResponse.getId()).isEqualTo(student.getId());
        Assertions.assertThat(studentFromResponse.getName()).isEqualTo(newName);

    }

    @Test
    void deleteUser() throws Exception {
        Student student = createAStudent();
        Assertions.assertThat(student.getId()).isNotNull();

        this.mockMvc.perform(delete("/student/" + student.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Student studentFromResponse = getStudentFromId(student.getId());
        Assertions.assertThat(studentFromResponse).isNull();
    }

    @Test
    void activateUser() throws Exception {
        Student student = createAStudent();
        Assertions.assertThat(student.getId()).isNotNull();

        MvcResult result = this.mockMvc.perform(put("/student/" + student.getId() + "/status?working=true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Student studentFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Student.class);
        Assertions.assertThat(studentFromResponse).isNotNull();
        Assertions.assertThat(studentFromResponse.getId()).isEqualTo(student.getId());
        Assertions.assertThat(studentFromResponse.isWorking()).isEqualTo(true);

        Student userFromResponseGet = getStudentFromId(student.getId());
        Assertions.assertThat(userFromResponseGet).isNotNull();
        Assertions.assertThat(userFromResponseGet.getId()).isEqualTo(student.getId());
        Assertions.assertThat(userFromResponseGet.isWorking()).isEqualTo(true);
    }
}

