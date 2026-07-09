package com.example.sms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.entity.Student;
import com.example.sms.repository.StudentRepository;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repo;

    //post
    public Student addStudent(Student s){
        return repo.save(s);
    }

    //get
    public List <Student> getStudents(){
        return repo.findAll();
    }

    //put

    public Student updateStudent(int id,Student s){
        Student s1 = repo.findById(id).orElse(null);
        if (s1 != null){
            s1.setName(s.getName());
            s1.setRegno(s.getRegno());
            s1.setDepartment(s.getDepartment());
            s1.setEmail(s.getEmail());
            s1.setPhone(s.getPhone());
            return repo.save(s1);
        }
        return null;
    }

    //patch

    public Student patchStudent(int id, Student s) {
    Student s1 = repo.findById(id).orElse(null);

    if (s1 != null) {

        if (s.getName() != null && !s.getName().isBlank()) {
            s1.setName(s.getName());
        }
        if(s.getDepartment() != null && !s.getDepartment().isBlank()){
            s1.setDepartment(s.getDepartment());
        }

        return repo.save(s1);
    }

    return null;
}

//delete

public String deleteStudent(int id) {

    Student s1 = repo.findById(id).orElse(null);

    if (s1 != null) {
        repo.deleteById(id);
        return "Student deleted successfully";
    }

    return "Not found";
}
    
}
