package mainPackage.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mainPackage.entities.Vacation;
import mainPackage.repositories.AssociateRepository;
import mainPackage.repositories.DepartmentRepository;
import mainPackage.repositories.VacationRepository;

@Controller
public class VacationController {
	@Autowired
	AssociateRepository associateRepository ;
	@Autowired
	DepartmentRepository departmentRepository ;
	@Autowired
	VacationRepository vacationRepository ;

	@GetMapping(path = "/vacation-edit" )
	public String vacationEdit(Model model) {
		VariablesGlobales.vacationsByAssociate = vacationRepository.findByAssociateId(VariablesGlobales.associateDetail.getId()) ;
		VariablesGlobales.sendObjects(model) ;
		return "vacation-edit";
	}
	
	@GetMapping(path = "/vacation-add")
	public String vacationAdd (Model model) {
		VariablesGlobales.sendObjects(model);
		return "vacation-add" ;
	}
	
	@PostMapping(path = "/vacation-add-to-confirm")
	public String vacationAddToConfirm(Model model, @ModelAttribute Vacation vacationReceived ) {
		VariablesGlobales.calculVacationNumberDay(vacationReceived);
		VariablesGlobales.vacationToConfirm = vacationReceived ;
		VariablesGlobales.sendObjects(model);
		return "vacation-confirm-before-save" ;
	}
	
	@GetMapping(path = "/vacation-modif")
	public String vacationModif(Model model) {
		VariablesGlobales.vacationToModif = VariablesGlobales.vacationToConfirm ;
		VariablesGlobales.sendObjects(model);
		return "vacation-modif" ;
	}
	
	@GetMapping(path = "/vacation-save")
	public String vacationSave(Model model) {
		VariablesGlobales.vacationToConfirm.setAssociateId(VariablesGlobales.associateDetail.getId());
		vacationRepository.save(VariablesGlobales.vacationToConfirm) ;
		VariablesGlobales.vacationsByAssociate = vacationRepository.findByAssociateId(VariablesGlobales.associateDetail.getId()) ;
		VariablesGlobales.associateDetail.setVacationsRemaining(VariablesGlobales.associateDetail.getVacationsRemaining() - VariablesGlobales.vacationToConfirm.getNumberOfDay());
		VariablesGlobales.associateDetail.setId(VariablesGlobales.associateDetail.getId());
		associateRepository.save(VariablesGlobales.associateDetail) ;
		VariablesGlobales.vacationToConfirm = new Vacation() ;
		VariablesGlobales.sendObjects(model);
		return "vacation-edit" ;
	}
	
	@GetMapping(path = "/vacation-edit-one")
	public String vacationEditOne(Model model, @RequestParam Long vacationId) {
		VariablesGlobales.vacationDetail =  vacationRepository.findById(vacationId).get() ;
		VariablesGlobales.sendObjects(model);
		return "vacation-detail" ;
	}
	
//	@GetMapping(path = "/vacation-update")
//	public String vacationUpdate(Model model) {
//		VariablesGlobales.vacationToUpdate= VariablesGlobales.vacationDetail ;
//		VariablesGlobales.sendObjects(model);
//		return "vacation-update" ;
//	}
//	
//	@PostMapping(path = "/vacation-update-confirmed")
//	public String vacationUpdateToConfirm(Model model, @ModelAttribute Vacation vacationReceived ) {
//		VariablesGlobales.calculVacationNumberDay(vacationReceived);
//		vacationReceived.setId(VariablesGlobales.vacationToUpdate.getId()) ;
//		VariablesGlobales.vacationToUpdate = vacationReceived ;
//		vacationRepository.save(VariablesGlobales.vacationToUpdate) ;
//		VariablesGlobales.vacationsByAssociate = vacationRepository.findByAssociateId(VariablesGlobales.associateDetail.getId()) ;
//		VariablesGlobales.sendObjects(model);
//		return "vacation-edit" ;
//	}
//	
	@GetMapping(path = "/vacation-delete")
	public String vacationDelete(Model model) {
		VariablesGlobales.vacationToDelete = VariablesGlobales.vacationDetail ;
		vacationRepository.delete(VariablesGlobales.vacationToDelete);
		VariablesGlobales.vacationsByAssociate = vacationRepository.findByAssociateId(VariablesGlobales.associateDetail.getId()) ;
		VariablesGlobales.associateDetail.setVacationsRemaining(VariablesGlobales.associateDetail.getVacationsRemaining() + VariablesGlobales.vacationToDelete.getNumberOfDay());
		VariablesGlobales.associateDetail.setId(VariablesGlobales.associateDetail.getId());
		associateRepository.save(VariablesGlobales.associateDetail) ;
		VariablesGlobales.vacationDetail = new Vacation() ;
		VariablesGlobales.vacationToDelete = new Vacation() ;
		VariablesGlobales.sendObjects(model);
		return "vacation-edit" ;
	}
	
	@GetMapping(path = "/vacation-department-list")
	public String departmentListVacation(Model model) {
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
		return "vacation-department-list";
	}
	
	@GetMapping(path = "/associate-list-one-department-for-vacation")
	public String associateListOneDeptVacation(Model model, @RequestParam String departmentName) {
		VariablesGlobales.departmentDetail = departmentRepository.findByName(departmentName) ;
		VariablesGlobales.associatesByDepartment = associateRepository.findByDepartment(departmentName) ;
		VariablesGlobales.associatesByDepartment.forEach(associate -> {
			VariablesGlobales.vacationsByAssociate = vacationRepository.findByAssociateId(associate.getId()) ;
			VariablesGlobales.vacationsDayCalculOneAssociate(associate);
			associateRepository.save(associate) ;
		});
		VariablesGlobales.sendObjects(model) ;
		return "associate-list-one-department-for-vacation" ;
	}
	
}
