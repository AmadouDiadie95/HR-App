package mainPackage.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Message {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	private String content ;
	private String source ;
	private String destination ;

}
