package com.example.sms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.sms.entity.Student;
import com.example.sms.service.StudentService;


@RestController

public class StudentController {
    @Autowired
    private StudentService service;

    //post
    @PostMapping("/student")
    public Student addStudent(@RequestBody Student s)
    {
        return service.addStudent(s);
    }

    //get
    @GetMapping("/students")
    public List <Student> getStudents(){
        return service.getStudents();

    }

    //put

    @PutMapping("/student1/{id}")
    public Student updateStudent(@PathVariable int id, @RequestBody Student s){
        return service.updateStudent(id,s);
    }

    //patch

    @PatchMapping("/student2/{id}")
    public Student patchStudent(@PathVariable int id, @RequestBody Student s) {
        return service.patchStudent(id, s);
    }

    //delete

    //delete

    @DeleteMapping("/student3/{id}")
    public String deleteStudent(@PathVariable int id) {
        return service.deleteStudent(id);
    }



    
}
