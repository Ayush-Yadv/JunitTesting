package com.example1.springWeek2.services.impl;

import com.example1.springWeek2.dto.EmployeeDto;
import com.example1.springWeek2.entities.EmployeeEntity;
import com.example1.springWeek2.repositories.EmployeeRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private EmployeeRepo employeeRepo;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeEntity mockEmployee;
    private EmployeeDto mockEmployeeDto;

    @BeforeEach
    void setup(){
        mockEmployee =EmployeeEntity.builder()
                .id(1L)
                .name("aaysh")
                .email("anayaushyadav@gmail.com")
                .salary(234556L)
                .build();

        mockEmployeeDto=modelMapper.map(mockEmployee,EmployeeDto.class);
    }

    @Test
    void TestEmployeeById_whenEmployeeIsPreset_ThenReturnEmployeeDto(){
        Long id=1L;
        EmployeeEntity MockEmployee=new EmployeeEntity();

        when(employeeRepo.findById(id)).thenReturn(Optional.of(MockEmployee));


        //act

        EmployeeDto employeeDto=employeeService.getEmployeeById(id);

        //asert
       Assertions.assertThat(employeeDto.getId()).isEqualTo(id);
       Assertions.assertThat(employeeDto.getEmail()).isEqualTo(MockEmployee.getEmail());
    }

    @Test
     void  testCreateEmployee_whenValidEmployee_ThenCreateNewEmployee(){

        // asign
        when(employeeRepo.findByEmail(anyString())).thenReturn(List.of());
        when(employeeRepo.save(any(EmployeeEntity.class))).thenReturn(mockEmployee);


        //act
        EmployeeDto employeeDto=employeeService.createNewEmployee(mockEmployeeDto);

        //asert

        Assertions.assertThat(employeeDto).isNotNull();
        Assertions.assertThat(employeeDto.getEmail()).isEqualTo(mockEmployeeDto
                .getEmail());

        ArgumentCaptor<EmployeeEntity> employeeEntityArgumentCaptor=ArgumentCaptor.forClass(EmployeeEntity.class);
        verify(employeeRepo).save(employeeEntityArgumentCaptor.capture());

        EmployeeEntity employeeCapture=employeeEntityArgumentCaptor.getValue();
        Assertions.assertThat(employeeCapture.getEmail()).isEqualTo(mockEmployee.getEmail());






    }


}