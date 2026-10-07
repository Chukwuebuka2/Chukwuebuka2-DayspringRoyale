package com.DayspringRoyale.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.DayspringRoyale.Model.Student;
import com.DayspringRoyale.services.StudentService;

@CrossOrigin
@RestController
@RequestMapping("/api")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
public class StudentController {
	@Autowired
	private StudentService service;
	
	@PreAuthorize("hasRole('REGISTRY', 'SUPPORT_STAFF')")
	@GetMapping("/student")
	public ResponseEntity<List<Student>> getAllStudent(){
		return new ResponseEntity<>(service.getAllStudent(), HttpStatus.OK);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/student/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable int id){
		Optional<Student> student = service.getStudentById(id);
		if(student.isPresent())
			return new ResponseEntity<Optional<Student>>(student, HttpStatus.OK);
		else
			return new ResponseEntity<>("Student Not Found", HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasRole('REGISTRY', 'SUPPORT_STAFF')")
	@PostMapping("/student/register/{applicantId}")
	public ResponseEntity<?> admitStudent(@PathVariable int applicantId, @RequestPart Student student,
			@RequestPart MultipartFile imagefile,
			@RequestPart List<MultipartFile> document)
	{
	try {
		Student student1 = service.admitStudent(applicantId, student, imagefile, document);
		return new ResponseEntity<>(student1, HttpStatus.OK);
	}
	catch (Exception e) {
		return new ResponseEntity<>("Input Not Valid" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/student/search")
	public ResponseEntity<List<Student>> searchStudent(@RequestParam String keyword){
		List<Student> student = service.searchStudent(keyword);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('REGISTRY', 'SUPPORT_STAFF', 'DEAN')")
	@GetMapping("/student/school/{schoolId}")
	public ResponseEntity<List<Student>> getStudentBySchool(@PathVariable int schoolId){
		List<Student> student = service.getStudentBySchool(schoolId);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('REGISTRY', 'SUPPORT_STAFF', 'DEAN', 'HOD', 'FACAULTY')")
	@GetMapping("/student/department/{deptId}")
	public ResponseEntity<List<Student>> getStudentByDepartment(@PathVariable int deptId){
		List<Student> student = service.getStudentByDepartment(deptId);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}

}
