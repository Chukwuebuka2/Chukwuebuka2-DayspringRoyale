package com.DayspringRoyale.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.DayspringRoyale.Model.Department;
import com.DayspringRoyale.Model.School;
import com.DayspringRoyale.Model.Staff;
import com.DayspringRoyale.Model.SystemRole;

@Repository
public interface StaffRepo extends JpaRepository<Staff, Integer>{
	@Query("SELECT s FROM Staff s WHERE " +
				"LOWER(s.firstname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
			"LOWER(s.lastname) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<Staff> searchStaff(String keyword);
	
	@Query("SELECT s FROM Staff s WHERE s.department = :dept " + 
			"AND :hodRole MEMBER OF s.user.role")
	Optional<Staff> findHodByDepartment(Department dept, SystemRole hod);
	
	@Query("SELECT s FROM Staff s WHERE s.school = :school " + 
			"AND :schooRole MEMBER OF s.user.role")
	Optional<Staff> findDeanBySchool(School school, SystemRole dean);

	@Query("SELECT s FROM Staff s WHERE s.school.id = :schoolId")
	List<Staff> getStaffBySchool(School school);
	
	@Query("SELECT s FROM Staff s WHERE s.department.id = :deptId")
	List<Staff> getStaffByDepartment(Department dept);

}
