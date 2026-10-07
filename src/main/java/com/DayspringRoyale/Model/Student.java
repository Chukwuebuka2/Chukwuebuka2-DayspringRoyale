package com.DayspringRoyale.Model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstname;
	private String lastname;
	private String matricnumber;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfBirth;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private String address;
	private int phonenumber;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/year")
	private LocalDate enrollmentDate;
	@Enumerated(EnumType.STRING)
	private StudentLevel level; 
	@ManyToOne
	@JoinColumn(name = "school_id")
	private School school;
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;
	@OneToMany(mappedBy = "student")
	private List<Enrollment> enrollment; 
	private StudentStatus status;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private FileData imagefile; 
	@OneToMany(mappedBy = "student")
	private List<FileData> documentfile;
	private Integer lastPromotedYear;
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getMatricnumber() {
		return matricnumber;
	}
	public void setMatricnumber(String matricnumber) {
		this.matricnumber = matricnumber;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(int phonenumber) {
		this.phonenumber = phonenumber;
	}
	public LocalDate getEnrollmentDate() {
		return enrollmentDate;
	}
	public void setEnrollmentDate(LocalDate enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}
	
	public StudentLevel getLevel() {
		return level;
	}
	public void setLevel(StudentLevel level) {
		this.level = level;
	}
	
	public Department getDepartment() {
		return department;
	}
	public List<Enrollment> getEnrollment() {
		return enrollment;
	}
	public void setEnrollment(List<Enrollment> enrollment) {
		this.enrollment = enrollment;
	}
	public StudentStatus getStatus() {
		return status;
	}
	public void setStatus(StudentStatus status) {
		this.status = status;
	}
	
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public FileData getImagefile() {
		return imagefile;
	}
	public void setImagefile(FileData imagefile) {
		this.imagefile = imagefile;
	}
	public List<FileData> getDocumentfile() {
		return documentfile;
	}
	public void setDocumentfile(List<FileData> documentfile) {
		this.documentfile = documentfile;
	}
	public Integer getLastPromotedYear() {
		return lastPromotedYear;
	}
	public void setLastPromotedYear(Integer lastPromotedYear) {
		this.lastPromotedYear = lastPromotedYear;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@PrePersist
	@PreUpdate
	void enforceStudentRole() {
		if (user != null) {
			user.setRole(new HashSet<>(Set.of(SystemRole.STUDENT)));
		}
	}
}
