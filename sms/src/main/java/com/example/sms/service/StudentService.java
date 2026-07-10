package com.example.sms.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.entity.Student;
import com.example.sms.repository.StudentRepository;
import com.example.sms.service.exception.BadRequestException;
import com.example.sms.service.exception.ResourceNotFoundException;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repo;

    public Student addStudent(Student student) {
        validateStudent(student, false);
        if (student.getRegisterNumber() == null) {
            throw new BadRequestException("Register number is required.");
        }
        repo.findByRegisterNumber(student.getRegisterNumber()).ifPresent(existing -> {
            throw new BadRequestException("Register number already exists.");
        });
        student.setStatus(student.getStatus() == null ? "Active" : student.getStatus());
        student.setCreatedAt(LocalDateTime.now());
        return repo.save(student);
    }

    public List<Student> getStudents() {
        return repo.findAll();
    }

    public Student getStudentById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
    }

    public Student updateStudent(Integer id, Student updatedStudent) {
        validateStudent(updatedStudent, true);
        Student existing = getStudentById(id);
        if (updatedStudent.getRegisterNumber() != null && !updatedStudent.getRegisterNumber().equals(existing.getRegisterNumber())) {
            repo.findByRegisterNumber(updatedStudent.getRegisterNumber()).ifPresent(duplicate -> {
                throw new BadRequestException("Register number already exists.");
            });
        }
        existing.setName(updatedStudent.getName());
        existing.setRegisterNumber(updatedStudent.getRegisterNumber());
        existing.setDepartment(updatedStudent.getDepartment());
        existing.setCourse(updatedStudent.getCourse());
        existing.setYear(updatedStudent.getYear());
        existing.setEmail(updatedStudent.getEmail());
        existing.setPhone(updatedStudent.getPhone());
        existing.setGender(updatedStudent.getGender());
        existing.setAddress(updatedStudent.getAddress());
        existing.setStatus(updatedStudent.getStatus());
        return repo.save(existing);
    }

    public Student patchStudent(Integer id, Student patch) {
        Student existing = getStudentById(id);
        if (patch.getName() != null && !patch.getName().isBlank()) {
            existing.setName(patch.getName());
        }
        if (patch.getRegisterNumber() != null) {
            if (!patch.getRegisterNumber().equals(existing.getRegisterNumber())) {
                repo.findByRegisterNumber(patch.getRegisterNumber()).ifPresent(duplicate -> {
                    throw new BadRequestException("Register number already exists.");
                });
            }
            existing.setRegisterNumber(patch.getRegisterNumber());
        }
        if (patch.getDepartment() != null && !patch.getDepartment().isBlank()) {
            existing.setDepartment(patch.getDepartment());
        }
        if (patch.getCourse() != null && !patch.getCourse().isBlank()) {
            existing.setCourse(patch.getCourse());
        }
        if (patch.getYear() != null && !patch.getYear().isBlank()) {
            existing.setYear(patch.getYear());
        }
        if (patch.getEmail() != null && !patch.getEmail().isBlank()) {
            validateEmail(patch.getEmail());
            existing.setEmail(patch.getEmail());
        }
        if (patch.getPhone() != null && !patch.getPhone().isBlank()) {
            validatePhone(patch.getPhone());
            existing.setPhone(patch.getPhone());
        }
        if (patch.getGender() != null && !patch.getGender().isBlank()) {
            existing.setGender(patch.getGender());
        }
        if (patch.getAddress() != null && !patch.getAddress().isBlank()) {
            existing.setAddress(patch.getAddress());
        }
        if (patch.getStatus() != null && !patch.getStatus().isBlank()) {
            existing.setStatus(patch.getStatus());
        }
        return repo.save(existing);
    }

    public String deleteStudent(Integer id) {
        Student existing = getStudentById(id);
        repo.delete(existing);
        return "Student deleted successfully";
    }

    public List<Student> searchByName(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    public List<Student> filterByDepartment(String department) {
        return repo.findByDepartmentIgnoreCase(department);
    }

    public Student getStudentByRegisterNumber(Integer registerNumber) {
        return repo.findByRegisterNumber(registerNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with register number " + registerNumber));
    }

    private void validateStudent(Student student, boolean isUpdate) {
        if (student == null) {
            throw new BadRequestException("Student payload cannot be null.");
        }
        if (!isUpdate) {
            if (student.getName() == null || student.getName().isBlank()) {
                throw new BadRequestException("Name is required.");
            }
            if (student.getDepartment() == null || student.getDepartment().isBlank()) {
                throw new BadRequestException("Department is required.");
            }
            if (student.getCourse() == null || student.getCourse().isBlank()) {
                throw new BadRequestException("Course is required.");
            }
            if (student.getYear() == null || student.getYear().isBlank()) {
                throw new BadRequestException("Year is required.");
            }
            if (student.getEmail() == null || student.getEmail().isBlank()) {
                throw new BadRequestException("Email is required.");
            }
            if (student.getPhone() == null || student.getPhone().isBlank()) {
                throw new BadRequestException("Phone number is required.");
            }
            if (student.getGender() == null || student.getGender().isBlank()) {
                throw new BadRequestException("Gender is required.");
            }
            if (student.getAddress() == null || student.getAddress().isBlank()) {
                throw new BadRequestException("Address is required.");
            }
        }
        if (student.getEmail() != null && !student.getEmail().isBlank()) {
            validateEmail(student.getEmail());
        }
        if (student.getPhone() != null && !student.getPhone().isBlank()) {
            validatePhone(student.getPhone());
        }
        if (student.getDepartment() != null && !student.getDepartment().isBlank()) {
            validateDepartment(student.getDepartment());
        }
    }

    private void validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new BadRequestException("Invalid email format.");
        }
    }

    private void validatePhone(String phone) {
        String numericPhone = phone.replaceAll("\\D", "");
        if (!numericPhone.matches("\\d{10}")) {
            throw new BadRequestException("Phone number must contain exactly 10 digits.");
        }
    }

    private void validateDepartment(String department) {
        String lowerDepartment = department.trim().toLowerCase();
        if (!lowerDepartment.equals("cs") && !lowerDepartment.equals("it") && !lowerDepartment.equals("ai")
                && !lowerDepartment.equals("ece")) {
            throw new BadRequestException("Department must be one of: CS, IT, AI, ECE.");
        }
    }
}
