package com.example.department.service;

import com.example.department.entity.Department;
import com.example.department.repository.DepartmentNotFoundException;
import com.example.department.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public Department saveDepartment(Department department){
        System.out.println("NHAN" + department);
        try {
            Department department1 = departmentRepository.findById(department.getId()).get();
            if(department1 != null){
                department1.setDepartmentName(department.getDepartmentName());
                System.out.println("try" + department1);
                return departmentRepository.save(department1);
            }
        }catch(Exception e){
            Department d = new Department();
            d.setDepartmentName(department.getDepartmentName());
            System.out.println("catch" + d);
            return departmentRepository.save(d);
        }
        return null;





//        System.out.println("haha" + department);
//        if(department.getId() != null){
//            Department department1 = departmentRepository.findById(department.getId()).get();
//            department1.setId(department.getId());
//            department1.setDepartmentName(department.getDepartmentName());
//            return departmentRepository.save(department1);
//        }
//        else{
//            Department d = new Department();
//            d.setDepartmentName(department.getDepartmentName());
//            System.out.println("hehe" + d);
//            return departmentRepository.save(d);
//        }
    }

    //@Cacheable(value="Department", key = "#Id")
    public Department findDepartmentById(Long Id){
        return departmentRepository.findById(Id).get();
    }

    public List<Department> getAllInvoices() {
        return departmentRepository.findAll();
    }

    @CachePut(value="Department", key="#Id")
    public Department updateDepartment(Department inv, Long Id) {
        Department department = departmentRepository.findById(Id)
                .orElseThrow(() -> new DepartmentNotFoundException("Invoice Not Found"));
        department.setDepartmentName(inv.getDepartmentName());
        return departmentRepository.save(department);
    }

    @CacheEvict(value="Department", key="#Id")
    public void deleteDepartment(Long Id) {
        Department invoice = departmentRepository.findById(Id)
                .orElseThrow(() -> new DepartmentNotFoundException("Invoice Not Found"));
        departmentRepository.delete(invoice);
    }

    public Long layMaDepartmentLonNhat(){
        Long maxid = 0l;
        List<Department> departments = departmentRepository.findAll();
        for (Department department : departments){
            if(maxid < department.getId()){
                maxid = department.getId();
            }
        }
        return maxid;
    }
}
