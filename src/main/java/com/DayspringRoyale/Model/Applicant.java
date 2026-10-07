package com.DayspringRoyale.Model;

import java.sql.Date;
import java.util.List;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Applicant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstname;
	private String lastname;
	private Date dateOfBirth;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private String address;
	@Enumerated(EnumType.STRING)
	private ApplicationStatus status = ApplicationStatus.Pending;
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private FileData imagefile; 
	@OneToMany(mappedBy = "applicant")
	private List<FileData> documentfile;
	
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
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public ApplicationStatus getStatus() {
		return status;
	}
	public void setStatus(ApplicationStatus status) {
		this.status = status;
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

}
