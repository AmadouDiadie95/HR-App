package mainPackage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import mainPackage.entities.Anniversary;

public interface AnniversaryRepository extends JpaRepository<Anniversary, Long> {
	public Anniversary findByMonthName(String monthName) ;
	public Anniversary findByMonthNumber(String monthNumber) ;
}
