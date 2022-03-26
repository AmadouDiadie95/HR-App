package mainPackage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import mainPackage.entities.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
	public Department findByName(String name) ;
}
