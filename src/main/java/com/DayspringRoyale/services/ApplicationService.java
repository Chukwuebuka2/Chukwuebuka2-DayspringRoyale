package com.DayspringRoyale.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.imageio.ImageIO;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.DayspringRoyale.Model.Applicant;
import com.DayspringRoyale.Model.ApplicationStatus;
import com.DayspringRoyale.Model.Department;
import com.DayspringRoyale.Model.FileData;
import com.DayspringRoyale.Repository.ApplicantRepo;
import com.DayspringRoyale.Repository.DepartmentRepo;

import jakarta.persistence.EntityNotFoundException;


@Service
public class ApplicationService {
	
	@Autowired
	private ApplicantRepo repo;

	@Autowired
	private DepartmentRepo departmentrepo;
	
	private static final Set<String> ALLOWED_IMAGE_TYPES = 
			Set.of("image/jpeg", "image/png");
	
	private static final Set<String> ALLOWED_DOCUMENT_TYPES =
			Set.of("application/pdf",
					"application/msword", 
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document",
					"image/jpeg",
					"image/png");

	
	public Applicant apply(Applicant applicant,
			MultipartFile image, List<MultipartFile> document) throws IOException {
		validateImage(image);
		validateDocument(document);
		
		Department department = departmentrepo.findById(applicant.getDepartment()
				.getId()).orElseThrow(() -> new RuntimeException("Department Doesn't exist"));
		
		applicant.setDepartment(department);
		
		FileData imagefile = new FileData();
		imagefile.setName(image.getOriginalFilename());
		imagefile.setType(image.getContentType());
		imagefile.setData(image.getBytes());
		
		applicant.setImagefile(imagefile);
		
		if (document != null) {
			for(MultipartFile doc : document) {
				FileData documents = new FileData();
				documents.setName(doc.getOriginalFilename());
				documents.setType(doc.getContentType());
				documents.setData(doc.getBytes());
				
				applicant.getDocumentfile().add(imagefile);
			}
		}
		return repo.save(applicant);
	}
	
	public void validateImage(MultipartFile image) throws IOException {
		if(image == null || image.isEmpty()) {
			throw new IllegalArgumentException("Upload Passport Photograph");
		}
		if (!ALLOWED_IMAGE_TYPES.contains(image.getContentType())) {
			throw new IllegalArgumentException("Only JPEG & PNG File Allowed");
		}
		if(ImageIO.read(image.getInputStream())  == null) {
			throw new IllegalArgumentException("Invalid File");
		}
	}
	
	public void validateDocument(List<MultipartFile> document) {
		if (document == null) {
			return ;
		}
		
		for (MultipartFile doc : document) {
			
			if(doc == null || doc.isEmpty()) {
				throw new IllegalArgumentException("Document Missing");
			}
			
			if (!ALLOWED_DOCUMENT_TYPES.contains(doc.getContentType())) {
				throw new IllegalArgumentException("Invalid Document Type");
			}
		}
	}

	public List<Applicant> application() {
		return repo.findAll();
	}

	public Optional<Applicant> application(int id) {
		return repo.findById(id);
	}

	public Applicant rejectApplication(int id) {
		// TODO Auto-generated method stub
		return changeStatus(id, ApplicationStatus.Rejected);
	}

	public Applicant acceptApplication(int id) { 
		return changeStatus(id, ApplicationStatus.Accepted);
	}
	
	private Applicant changeStatus(int id, ApplicationStatus newStatus) {
		Applicant applicant = repo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Applicant Not Found"));
		if (applicant.getStatus() != ApplicationStatus.Pending) {
			throw new IllegalStateException("Applicant Has Already Been" + applicant.getStatus());
		}
		applicant.setStatus(newStatus);
		return repo.save(applicant);
	}
}


