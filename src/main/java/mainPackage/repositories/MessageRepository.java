package mainPackage.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mainPackage.entities.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
	public List<Message> findByContentContains(String content) ;
	public List<Message> findBySource(String source) ;
	public List<Message> findByDestination(String destination) ;
}
