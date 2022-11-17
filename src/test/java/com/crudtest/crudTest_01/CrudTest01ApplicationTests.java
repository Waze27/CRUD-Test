package com.crudtest.crudTest_01;

import com.crudtest.crudTest_01.controllers.ControllerStudent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles(value = "test")
class CrudTest01ApplicationTests {

    @Autowired
    private ControllerStudent controllerStudent;

    @Test
    void contextLoads() {
        assertThat(controllerStudent).isNotNull();
    }
}

