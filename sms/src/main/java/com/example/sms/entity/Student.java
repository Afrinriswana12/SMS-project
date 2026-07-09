package com.example.sms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="std1")


public class Student {

    
    @Id
    private int id;
    private String name;
    private int regno;
    private String department;
    private String email;
    private long phone;


    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }


    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public int getRegno(){
        return regno;
    }
    public void setRegno(int regno){
        this.regno = regno;
    }

    public String getDepartment(){
        return department;
    }
    public void setDepartment(String department){
        this.department = department;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public Long getPhone(){
        return phone;
    }
    public void setPhone(Long phone){
        this.phone = phone;
    }



    
}
