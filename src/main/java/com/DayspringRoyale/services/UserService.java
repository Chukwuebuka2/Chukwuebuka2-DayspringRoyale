package com.DayspringRoyale.services;

import java.security.SecureRandom;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.DayspringRoyale.Model.Staff;
import com.DayspringRoyale.Model.Student;
import com.DayspringRoyale.Model.SystemRole;
import com.DayspringRoyale.Model.User;
import com.DayspringRoyale.Repository.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private AuthenticationManager authmanager;
	
	@Autowired
	private JWTservice jwtservice;

	private BCryptPasswordEncoder encode = new BCryptPasswordEncoder(12);
	
	private static final String CHARS =
		    "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";
		private final SecureRandom random = new SecureRandom();

		private String generatePassword() {
		    StringBuilder sb = new StringBuilder(8);
		    for (int i = 0; i < 8; i++) {
		        sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
		    }
		    return sb.toString();
		}

		private String generateUsername(String first, String last) {
		    String base = (first + "." + last).toLowerCase().replaceAll("\\s+", "");
		    String username = base;
		    int count = 1;
		    while (repo.existsByUsername(username)) {
		        username = base + count++;
		    }
		    return username;
		}


	public String addNewStudent(Student student) {
		User user =new User();
		String rawPassword = generatePassword();
		user.setUsername(generateUsername(student.getFirstname(), student.getLastname()));
		user.setPassword(encode.encode(rawPassword)); 
		repo.save(user);
		
		return rawPassword;
	}

	public String addNewStaff(Staff staff, Set<SystemRole> role) {
		User user = new User();
		String rawPassword = generatePassword();
		user.setUsername(generateUsername(staff.getFirstname(), staff.getLastname()));
		user.setPassword(encode.encode(rawPassword));
		repo.save(user);
		
		return rawPassword;
	}

 public String verify(User user) {
	 Authentication authentication = authmanager
			 .authenticate(new UsernamePasswordAuthenticationToken(user
					 .getUsername(), user.getPassword()));
	 
	 if (authentication.isAuthenticated())
		 return jwtservice.generateToken(user.getUsername());
	 else
	 return "Failed";
 	}
	
}
