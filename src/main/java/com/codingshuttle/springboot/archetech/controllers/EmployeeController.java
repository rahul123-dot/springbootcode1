package com.codingshuttle.springboot.archetech.controllers;

import com.codingshuttle.springboot.archetech.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

//    @GetMapping(path = "getSecretMessage")
//    public String getMySuperSecretMessage(){
//        return "Secret Message : rahul dhole";
//    }

//    @GetMapping(path = "{employeeid}")
//    public EmployeeDTO getEmployeeById(@PathVariable Long employeeid){
//        return new EmployeeDTO(employeeid,"Rahul Dhole","rahuldhole464@gmail.com",23, LocalDate.of(2024,3,1),true);
//    }

//    @GetMapping(path = "{employeeid}")
//    public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeid") Long id){
//        return new EmployeeDTO(id,"Rahul Dhole","rahuldhole464@gmail.com",23, LocalDate.of(2024,3,1),true);
//    }

//    @GetMapping(path = "")
//    public String getAllEmployees(@RequestParam Integer age,
//                                  @RequestParam String sortBy){
//        return "My age "+age+"+"+sortBy;
//    }
@GetMapping(path = "")
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
}

}
