package mainPackage.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mainPackage.entities.Associate;

public interface AssociateRepository extends JpaRepository<Associate, Long> {
	public List<Associate> findByNameOrSurnameContains(String name, String surname) ;
	public List<Associate> findByaddressContains(String address) ;
	public List<Associate> findByPhoneNumberContains(String phoneNumber) ;
	public List<Associate> findByFixeNumberContains(String fixeNumber) ;
	// public List<Associate> findByEmailContains(String email) ;
	public List<Associate> findByDepartment(String department) ;
	public List<Associate> findByPosition(String position) ;
	// public List<Associate> findBySituation(String situation) ;
	// public List<Associate> findBySalary(double salary) ;
	// public List<Associate> findBySalaryBetween(double salary1, double salary2) ;
	public List<Associate> findByStagiaire(boolean stagiaire) ;
	public List<Associate> findByStillAssociate(boolean stillAssociate) ;
}
