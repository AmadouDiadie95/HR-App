package mainPackage.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mainPackage.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	public List<Notification> findByContentContains(String content) ;
}
