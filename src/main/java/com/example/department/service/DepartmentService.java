package com.example.department.service;

import com.example.department.entity.Department;
import com.example.department.repository.DepartmentNotFoundException;
import com.example.department.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    private HashOperations hashOperations;
    @Autowired
    private RedisTemplate redisTemplate;

    public DepartmentService(RedisTemplate redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
        this.redisTemplate = redisTemplate;
    }
    public Department saveDepartment(Department department){
        System.out.println("NHAN" + department);
        try {
            Department department1 = departmentRepository.findById(department.getId()).get();
            if(department1 != null){
                department1.setDepartmentName(department.getDepartmentName());
                Department kq = departmentRepository.save(department1);
                hashOperations.put("DEPARTMENT", kq.getId(), kq);
                return kq;
            }
        }catch(Exception e){
            Department d = new Department();
            d.setDepartmentName(department.getDepartmentName());
            Department dzz = departmentRepository.save(d);
            hashOperations.put("DEPARTMENT", dzz.getId(), dzz);
            return dzz;
        }
        return null;
    }
    public Department findDepartmentById(Long Id){
        if(hashOperations.get("DEPARTMENT", Id) !=null){
            return (Department) hashOperations.get("DEPARTMENT", Id);
        }
        return departmentRepository.findById(Id).get();
    }
    public List<Department> getAllInvoices() {
        if(hashOperations.values("DEPARTMENT").size()>0){
            return hashOperations.values("DEPARTMENT");
        }
        List<Department> list = departmentRepository.findAll();
        for (Department d:list) {
            hashOperations.put("DEPARTMENT", d.getId(), d);
        }
        return list;
    }
    public Department updateDepartment(Department inv, Long Id) {
        hashOperations.put("DEPARTMENT", inv.getId(), inv);
        Department department = departmentRepository.findById(Id)
                .orElseThrow(() -> new DepartmentNotFoundException("Invoice Not Found"));
        department.setDepartmentName(inv.getDepartmentName());
        return departmentRepository.save(department);
    }
    public void deleteDepartment(Long Id) {
        hashOperations.delete("DEPARTMENT", Id);
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
