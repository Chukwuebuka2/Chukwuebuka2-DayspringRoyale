package com.DayspringRoyale.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.DayspringRoyale.Model.Department;
import com.DayspringRoyale.Model.FileData;
import com.DayspringRoyale.Model.School;
import com.DayspringRoyale.Model.Staff;
import com.DayspringRoyale.Model.SystemRole;
import com.DayspringRoyale.Repository.DepartmentRepo;
import com.DayspringRoyale.Repository.SchoolRepo;
import com.DayspringRoyale.Repository.StaffRepo;

@Service
public class StaffService {
	@Autowired
	private StaffRepo repo;
	
	@Autowired
	private DepartmentRepo departmentrepo;
	
	@Autowired
	private SchoolRepo schoolrepo;

	public List<Staff> getAllStaff() {
		return repo.findAll();
	}

	public Optional<Staff> getStaffById(int id) {
		return repo.findById(id);
	}
	
	private static final Set<String> ALLOWED_IMAGE_TYPES = 
			Set.of("image/png", "image/jpeg");
	private static final Set<String> ALLOWED_DOCUMENT_TYPES = 
			Set.of("application.msword",
					"application,pdf",
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document",
					"image/png",
					"image/jpeg");

	public Staff addNewStaff(Staff staff, MultipartFile imagefile, List<MultipartFile> document) throws IOException {
		
		validateImage(imagefile);
		validateDocument(document);
		Department department = departmentrepo.findById(staff.getDepartment()
				.getId()).orElseThrow(() -> new RuntimeException("Department not Found"));
		
		staff.setDepartment(department);
		
		FileData image = new FileData();
		image.setName(imagefile.getOriginalFilename());
		image.setType(imagefile.getContentType());
		image.setData(imagefile.getBytes());
		
		staff.getImagefile();
		
		if(document != null) {
			for(MultipartFile doc : document) {
				FileData documents = new FileData();
				documents.setName(doc.getOriginalFilename());
				documents.setType(doc.getContentType());
				documents.setData(doc.getBytes());
				staff.getDocumentfile().add(documents);
			}
			}
		return repo.save(staff);
	}
	
	public void validateImage(MultipartFile imagefile) throws IOException {
		if(imagefile == null || imagefile.isEmpty()) {
			throw new IllegalArgumentException("Upload Your Photo");
		}
		if(!ALLOWED_IMAGE_TYPES.contains(imagefile.getContentType())) {
			throw new IllegalArgumentException("Only JPEG and PNG Accepted");
		}
		if(ImageIO.read(imagefile.getInputStream()) == null ) {
			throw new IllegalArgumentException("File Is Not A Valid Image");
		}
	}
	
	public void validateDocument(List<MultipartFile> document) {
		if (document == null)
			return ;
		for(MultipartFile doc : document) {
			if (doc == null || doc.isEmpty()) {
				throw new IllegalArgumentException("Upload Your Document");
			}
			if (!ALLOWED_DOCUMENT_TYPES.contains(doc.getContentType())) {
				throw new IllegalArgumentException("File Not Supported");
			}
		}
	}
	

	public List<Staff> searchStaff(String keyword) {
		return repo.searchStaff(keyword);
	}

	public void assignHod(int staffId) {
		
		Staff newHOD = repo.findById(staffId).orElseThrow(()->
		new RuntimeException("Staff Not Found"));
		Department dept = newHOD.getDepartment();
		repo.findHodByDepartment(dept, SystemRole.HOD).ifPresent(old -> 
					old.getUser().getRole().remove(SystemRole.HOD));
		newHOD.getUser().getRole().add(SystemRole.HOD);
	}
	
	public void assignDean(int staffId) {
		
		Staff newDean = repo.findById(staffId).orElseThrow(() ->
		new RuntimeException("Staff Not Found"));
		School school = newDean.getSchool();
		
		repo.findDeanBySchool(school, SystemRole.DEAN).ifPresent(old ->
					old.getUser().getRole().remove(SystemRole.DEAN));
		newDean.getUser().getRole().add(SystemRole.DEAN);
	}

	public List<Staff> getStaffBySchool(int schoolId) {
		School school = schoolrepo.findById(schoolId).orElseThrow(() -> 
		new RuntimeException("Not Found"));
		return repo.getStaffBySchool(school);
	}

	public List<Staff> getStaffByDepartment(int deptId) {
		Department dept = departmentrepo.findById(deptId).orElseThrow(() ->
		new RuntimeException("Not Found"));
		return repo.getStaffByDepartment(dept);
	}

}
