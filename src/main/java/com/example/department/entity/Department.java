package com.example.department.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="t_departments")
public class Department {
    @Id
    @GeneratedValue
    private Long Id;

    @Column(columnDefinition = "nvarchar(25)")
    private String DepartmentName;
}
