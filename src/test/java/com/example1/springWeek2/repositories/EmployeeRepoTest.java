package com.example1.springWeek2.repositories;

import com.example1.springWeek2.entities.EmployeeEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class EmployeeRepoTest {

    @Autowired
    private EmployeeRepo employeeRepo;

    private EmployeeEntity employee;

    @BeforeEach
    void setUp(){
        employee=EmployeeEntity.builder()
                .name("Ayush Yadav")
                .email("anayAyushYadav275205@gmail.com")
                .salary(12345L)
                .build();
    }

    @Test
    void testFindByEmailWhenEmailIsValid_ReturnEmployee() {
        //Arrange  or given
        employeeRepo.save(employee);

        //Act or when
        List<EmployeeEntity> employeeEntityList=employeeRepo
                .findByEmail(employee.getEmail());

        //Assert or then
        Assertions.assertThat(employeeEntityList).isNotNull();
        Assertions.assertThat(employeeEntityList).isNotEmpty();

        Assertions.assertThat(employeeEntityList.get(0).getEmail())
                .isEqualTo(employee.getEmail());


    }
    @Test
    void testFindByEmailWhenEmailIsNotValid_ReturnEmployee() {
        //given
        String email="ayush@gmaail.com";


        //act

        List<EmployeeEntity> employeeEntityList=employeeRepo
                .findByEmail(email);


        //then

        Assertions.assertThat(employeeEntityList).isNotNull();
        Assertions.assertThat(employeeEntityList).isEmpty();

    }
}