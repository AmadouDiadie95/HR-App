package mainPackage.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mainPackage.entities.Vacation;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
	public List<Vacation> findByAssociateId(Long id) ;
}
