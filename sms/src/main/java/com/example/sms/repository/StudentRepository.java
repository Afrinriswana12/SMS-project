package com.example.sms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sms.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByNameContainingIgnoreCase(String name);
    List<Student> findByDepartmentIgnoreCase(String department);
    Optional<Student> findByRegisterNumber(Integer registerNumber);
    long countByDepartmentIgnoreCase(String department);
}

