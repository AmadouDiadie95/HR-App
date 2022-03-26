package mainPackage.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mainPackage.entities.Department;
import mainPackage.repositories.AssociateRepository;
import mainPackage.repositories.DepartmentRepository;

@Controller
public class DepartmentController {
	
	@Autowired
	DepartmentRepository departmentRepository ;
	@Autowired
	AssociateRepository associateRepository ;

	@GetMapping(path = "/department-add")
	public String departmentAdd(Model model) {
		VariablesGlobales.sendObjects(model) ;
		return "department-add" ;
	}
	
	@PostMapping(path = "/department-save") 
	public String departmentSave(Model model, @ModelAttribute Department departmentReceived) {
		VariablesGlobales.departmentDetail = departmentReceived ;
		departmentRepository.save(VariablesGlobales.departmentDetail) ;
		VariablesGlobales.allDepartments = departmentRepository.findAll() ;
		VariablesGlobales.sendObjects(model) ;
		return "department-list";
	}
	
	
	
	@GetMapping(path = "/associate-list-one-department")
	public String associateListOneDept(Model model, @RequestParam String departmentName) {
		VariablesGlobales.departmentDetail = departmentRepository.findByName(departmentName) ;
		VariablesGlobales.associatesByDepartment = associateRepository.findByDepartment(departmentName) ;
		VariablesGlobales.sendObjects(model) ;
		return "associate-list-one-department" ;
	}
	
	@GetMapping(path = "/department-edit")
	public String departmentEdit(Model model, @RequestParam String departmentName) {
		VariablesGlobales.departmentDetail = departmentRepository.findByName(departmentName) ;
		model.addAttribute("newDepartment", VariablesGlobales.departmentDetail) ;
		return "department-edit" ;
	}
	
	@PostMapping(path = "/department-update") 
	public String departmentUpdate(Model model, @ModelAttribute Department departmentReceived) {
		departmentReceived.setId(VariablesGlobales.departmentDetail.getId()) ;
		VariablesGlobales.departmentDetail = departmentReceived ;
		departmentRepository.save(VariablesGlobales.departmentDetail) ;
		VariablesGlobales.allDepartments = departmentRepository.findAll() ;
		VariablesGlobales.sendObjects(model) ;
		return "department-list";
	}
	
	@GetMapping(path = "/department-delete")
	public String departmentDelete(Model model, @RequestParam String departmentName) {
		VariablesGlobales.departmentDetail = departmentRepository.findByName(departmentName) ;
		departmentRepository.delete(VariablesGlobales.departmentDetail) ;
		VariablesGlobales.departmentDetail = new Department() ;
		VariablesGlobales.allDepartments = departmentRepository.findAll() ;
		VariablesGlobales.sendObjects(model) ;
		return "department-list";
	}
	
	@GetMapping(path = "/department-list")
	public String departmentList(Model model) {
		VariablesGlobales.allDepartments = departmentRepository.findAll() ;
		VariablesGlobales.allAssociates = associateRepository.findAll() ;
		VariablesGlobales.allDepartmentsTotal = 0 ;
		VariablesGlobales.allDepartmentsTotalMember =0  ;
		VariablesGlobales.allDepartmentsTotalSalary = 0 ;
		VariablesGlobales.allDepartments.forEach(department -> {
			department.setTotalMember(0) ;
			department.setTotalSalary(0) ;
			VariablesGlobales.allAssociates.forEach(associate -> {		
				if (associate.getDepartment().equals(department.getName())) {
					department.setTotalMember(department.getTotalMember() + 1) ;
					department.setTotalSalary(department.getTotalSalary() + associate.getSalary()) ;
				}
			}) ;
			departmentRepository.save(department) ;
			VariablesGlobales.allDepartmentsTotal ++ ;
			VariablesGlobales.allDepartmentsTotalMember += department.getTotalMember() ;
			VariablesGlobales.allDepartmentsTotalSalary += department.getTotalSalary() ;
		}) ;
		VariablesGlobales.sendObjects(model) ;
		return "department-list";
	}
	
	
}
