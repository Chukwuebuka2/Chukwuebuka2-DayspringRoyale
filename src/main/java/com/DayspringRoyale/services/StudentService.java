package com.DayspringRoyale.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.DayspringRoyale.Model.Applicant;
import com.DayspringRoyale.Model.ApplicationStatus;
import com.DayspringRoyale.Model.Department;
import com.DayspringRoyale.Model.FileData;
import com.DayspringRoyale.Model.School;
import com.DayspringRoyale.Model.Student;
import com.DayspringRoyale.Model.StudentLevel;
import com.DayspringRoyale.Model.SystemRole;
import com.DayspringRoyale.Repository.ApplicantRepo;
import com.DayspringRoyale.Repository.DepartmentRepo;
import com.DayspringRoyale.Repository.SchoolRepo;
import com.DayspringRoyale.Repository.StudentRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentService {
	
	@Autowired
	private MatricNumberService matricnumberservice;
	
	@Autowired
	private StudentRepo repo;
	
	@Autowired
	private DepartmentRepo departmentrepo;
	
	@Autowired
	private ApplicantRepo applicantrepo;
	
	@Autowired
	private SchoolRepo schoolrepo;

	public List<Student> getAllStudent() {
		return repo.findAll();
	}

	public Optional<Student> getStudentById(int id) {
		return repo.findById(id);
	}
	
	private static final Set<String> ALLOWED_IMAGE_TYPES = 
			Set.of("image/jpeg", "image/png");
	
	private static final Set<String> ALLOWED_DOCUMENT_TYPES =
			Set.of("application/pdf",
					"application/msword", 
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document",
					"image/jpeg",
					"image/png");

	public Student admitStudent(int applicantId, Student student, MultipartFile imagefile,
			List<MultipartFile> document) throws IOException {
		
		validateImage(imagefile);
		validateDocument(document);
	
		Applicant applicant = applicantrepo.findById(applicantId)
				.orElseThrow(() -> new EntityNotFoundException("Application Not Found"));
		if(applicant.getStatus() == ApplicationStatus.Pending) {
			throw new IllegalArgumentException("Application Status Pending");
		}
		if(applicant.getStatus() == ApplicationStatus.Rejected) {
			throw new IllegalArgumentException("Application Rejected");
		}
		Department department = departmentrepo.findById(student.getDepartment()
		.getId()).orElseThrow(() -> new RuntimeException("Department Not found"));
		
		student.setDepartment(department);
		student.setMatricnumber(matricnumberservice.generateMatricNumber());
		student.setEnrollmentDate(LocalDate.now());
		
		FileData file = new FileData();
		file.setName(imagefile.getOriginalFilename());
		file.setType(imagefile.getContentType());
		file.setData(imagefile.getBytes());
		
		student.setImagefile(file);
		
		if(document != null) {
		for(MultipartFile doc : document) {
			FileData documents = new FileData();
			documents.setName(doc.getOriginalFilename());
			documents.setType(doc.getContentType());
			documents.setData(doc.getBytes());
			student.getDocumentfile().add(documents);
		}
		}
		
		return repo.save(student);
		
	}
	
	public void validateImage(MultipartFile imagefile) throws IOException {
		if (imagefile == null || imagefile.isEmpty()) {
			throw new IllegalArgumentException("Image is Required");
		}
		if (!ALLOWED_IMAGE_TYPES.contains(imagefile.getContentType())) {
			throw new IllegalArgumentException("Only JPEG or PNG allowed");
		}
		if(ImageIO.read(imagefile.getInputStream()) == null) {
			throw new IllegalArgumentException("File is Not a Valid image");
		}
	}
	
	public void validateDocument(List<MultipartFile> document) {
		if(document == null) {
			return ;
		}
		for (MultipartFile doc : document) {
			if (doc == null || doc.isEmpty()) {
				throw new IllegalArgumentException("Document is Needed");
			}
			if (!ALLOWED_DOCUMENT_TYPES.contains(doc.getContentType())) {
				throw new IllegalArgumentException("Only PDF, WORD Documents Allowed");
			}
		}
	}

	public List<Student> searchStudent(String keyword) {
		return repo.searchStudent(keyword);
	}

	public List<Student> getStudentBySchool(int schoolId) {
		School school = schoolrepo.findById(schoolId).orElseThrow(() ->
		new RuntimeException("Not Found"));
		// TODO Auto-generated method stub
		return repo.getStudentBySchool(school);
	}

	public List<Student> getStudentByDepartment(int deptId) {
		Department dept = departmentrepo.findById(deptId).orElseThrow(() ->
				new RuntimeException("Not Found"));
		return repo.getStudentByDeprtment(dept);

	}
	

}
