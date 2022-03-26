package mainPackage.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mainPackage.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public List<User> findByName(String name) ;
	public List<User> findByUsername(String username) ;
	public List<User> findByPassword(String password) ;
	public List<User> findByRole(String role) ;
}
