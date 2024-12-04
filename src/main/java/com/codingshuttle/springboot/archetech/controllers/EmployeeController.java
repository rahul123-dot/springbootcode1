package com.codingshuttle.springboot.archetech.controllers;

import com.codingshuttle.springboot.archetech.dto.EmployeeDTO;
import com.codingshuttle.springboot.archetech.entities.EmployeeEntity;
import com.codingshuttle.springboot.archetech.exceptions.ResourceNotFoundException;
import com.codingshuttle.springboot.archetech.repositories.EmployeeRepository;
import com.codingshuttle.springboot.archetech.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

/*    @GetMapping(path = "getSecretMessage")
    public String getMySuperSecretMessage(){
        return "Secret Message : rahul dhole";
    }

    @GetMapping(path = "{employeeid}")
    public EmployeeDTO getEmployeeById(@PathVariable Long employeeid){
        return new EmployeeDTO(employeeid,"Rahul Dhole","rahuldhole464@gmail.com",23, LocalDate.of(2024,3,1),true);
    }

    @GetMapping(path = "{employeeid}")
    public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeid") Long id){
        return new EmployeeDTO(id,"Rahul Dhole","rahuldhole464@gmail.com",23, LocalDate.of(2024,3,1),true);
    }

    @GetMapping(path = "")
    public String getAllEmployees(@RequestParam Integer age,
                                  @RequestParam String sortBy){
        return "My age "+age+"+"+sortBy;
    }
    */

/*@GetMapping(path = "")
public String getAllEmployees(@RequestParam (required = false,name = "inputName") Integer age,
                              @RequestParam (required = false)String sortBy){
    return "My age "+age+" "+sortBy;
}

@PostMapping
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployee){
    inputEmployee.setId(100L);
    return inputEmployee;
}

@PutMapping String updateEmployeeById(){
    return "Hello from put";

}*/

    /*
//week 2 video3

    final private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping(path = "{employeeid}")
    public EmployeeEntity getEmployeeById(@PathVariable Long employeeid){
        return employeeRepository.findById(employeeid).orElse(null);
    }

    @PostMapping
    public EmployeeEntity createNewEmployee(@RequestBody EmployeeEntity inputEmployee){
        return employeeRepository.save(inputEmployee);
    }
*/

    final private EmployeeService employeeService;

    public EmployeeController(EmployeeRepository employeeRepository, EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "{employeeid}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long employeeid){
        Optional<EmployeeDTO> employeeDTO = employeeService.findEmployeeById(employeeid);
        return employeeDTO.map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                .orElseThrow(()->new ResourceNotFoundException("Employee not found"));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getEmployeeById(@RequestParam (required = false,name = "inputName") Integer age,
                                             @RequestParam (required = false)String sortBy){
        return  ResponseEntity.ok(employeeService.getAllEmployees());
    }


    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody EmployeeDTO inputEmployee){
        EmployeeDTO savedEmployee = employeeService.saveEmployee(inputEmployee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{employeeid}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById (@RequestBody @Valid  EmployeeDTO updateEmployeeRecord, @PathVariable Long employeeid){
        return ResponseEntity.ok(employeeService.updateEmployeeById(updateEmployeeRecord, employeeid));
    }

    @DeleteMapping(path = "/{employeeid}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeid){
        boolean gotDeleted = employeeService.deleteEmployeeById(employeeid);
        if(gotDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping (path = "/{employeeid}")
    public ResponseEntity<EmployeeDTO> updateEmployeeFieldById(@RequestBody Map<String,Object> update, @PathVariable Long employeeid){

        EmployeeDTO employeeDTO = employeeService.updateEmployeeFieldById(update, employeeid);
        if(employeeDTO==null) return  ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO); 
    }

}
