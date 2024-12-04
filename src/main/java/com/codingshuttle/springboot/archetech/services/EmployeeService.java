package com.codingshuttle.springboot.archetech.services;

import com.codingshuttle.springboot.archetech.dto.EmployeeDTO;
import com.codingshuttle.springboot.archetech.entities.EmployeeEntity;
import com.codingshuttle.springboot.archetech.exceptions.ResourceNotFoundException;
import com.codingshuttle.springboot.archetech.repositories.EmployeeRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    public Optional<EmployeeDTO> findEmployeeById(Long employeeid) {
        Optional<EmployeeEntity> employeeDetail = employeeRepository.findById(employeeid);

        return employeeDetail.map(employeeEntity ->modelMapper.map(employeeEntity,EmployeeDTO.class));

    }

    public List<EmployeeDTO> getAllEmployees() {

        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities.stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());

    }

    public EmployeeDTO saveEmployee(EmployeeDTO inputEmployee) {

        EmployeeEntity saveEmployee = modelMapper.map(inputEmployee,EmployeeEntity.class);
          employeeRepository.save(saveEmployee);
        return modelMapper.map(saveEmployee, EmployeeDTO.class);
    }

    private void isEmployeeExistById(Long employeeid){
        boolean isExist = employeeRepository.existsById(employeeid);
        if(!isExist) throw  new ResourceNotFoundException("Employee not found with id:"+employeeid);

    }


    public EmployeeDTO updateEmployeeById(EmployeeDTO updateEmployeeRecord, Long employeeid) {
      isEmployeeExistById(employeeid);
        EmployeeEntity employeeEntity = modelMapper.map(updateEmployeeRecord, EmployeeEntity.class);
        employeeEntity.setId(employeeid);
         EmployeeEntity savedEmployeeentity = employeeRepository.save(employeeEntity);
         return modelMapper.map(savedEmployeeentity, EmployeeDTO.class);
    }


    public boolean deleteEmployeeById(Long employeeid) {
        isEmployeeExistById(employeeid);
        employeeRepository.deleteById(employeeid);
        return true;

    }

    public EmployeeDTO updateEmployeeFieldById(Map<String, Object> update, Long employeeid) {
        isEmployeeExistById(employeeid);
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeid).get();
        update.forEach((field,value)->{
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class,field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated,employeeEntity,value);
        });
        return modelMapper.map(employeeRepository.save(employeeEntity),EmployeeDTO.class);
    }
}
