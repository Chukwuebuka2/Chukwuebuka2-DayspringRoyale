package com.DayspringRoyale.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DayspringRoyale.Model.Department;
import com.DayspringRoyale.Repository.DepartmentRepo;

@Service
public class DepartmentService {
	@Autowired
	private DepartmentRepo repo;

	public List<Department> getAllDepartment() {
		return repo.findAll();
	}

	public Optional<Department> getDepartmentById(int id) {
		return repo.findById(id);
	}

	public Department addDepartment(Department department) {
		return repo.save(department);
	}

	public List<Department> searchDepartment(String keyword) {
		// TODO Auto-generated method stub
		return repo.searchDepartment(keyword);
	}
	
	

}
