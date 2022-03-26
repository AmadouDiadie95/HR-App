package mainPackage.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Associate {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	private String name ;
	private String surname ;
	private String address ;
	private String phoneNumber ;
	private String fixeNumber ;
	private String email ;
	private String department ;
	private String position ;
	private String situation ; 
	private double salary ;
	private String birthDay ;
	private String birthDayOneTheMonth ;
	private String startDay ;
	private int age ;
	private double vacationsSinceStarted ;
	private double vacationsRemaining ;
	private boolean stagiaire ;
	private boolean stillAssociate ;
	@Lob
	private String image;
	@Lob @Transient
	private MultipartFile file ;
}
