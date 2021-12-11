package com.example.department.controller;

import com.example.department.entity.Department;
import com.example.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public Department saveDepartment(@RequestBody Department department){
        return departmentService.saveDepartment(department);
    }
    @GetMapping("/{id}")
    public Department findDepartmentById(@PathVariable("id")Long orderId){
        return departmentService.findDepartmentById(orderId);
    }
    @GetMapping
    public String helloWorld(){
        return "AA";
    }

    @GetMapping("/alldepartment")
    public ResponseEntity<List<Department>> getAllInvoices(){
        return ResponseEntity.ok(departmentService.getAllInvoices());
    }
    @PutMapping("/update/{id}")
    public Department updateDepartment(@RequestBody Department inv, @PathVariable Long id) {
        return departmentService.updateDepartment(inv, id);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return "Employee with id: "+id+ " Deleted !";
    }

    @GetMapping("/maxId")
    public Long maxID(){
        return departmentService.layMaDepartmentLonNhat();
    }

}
