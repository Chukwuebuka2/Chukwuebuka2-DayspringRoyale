package com.DayspringRoyale.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DayspringRoyale.Model.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Integer>{
	@Query("SELECT s FROM Department s WHERE " +
			"LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<Department> searchDepartment(String keyword);

}
