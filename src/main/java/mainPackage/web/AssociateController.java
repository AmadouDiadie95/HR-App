package mainPackage.web;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import mainPackage.entities.Associate;
import mainPackage.repositories.AssociateRepository;
import mainPackage.repositories.DepartmentRepository;
import mainPackage.repositories.VacationRepository;

@Controller
public class AssociateController {
	@Autowired
	AssociateRepository associateRepository ;
	@Autowired
	DepartmentRepository departmentRepository ;
	@Autowired
	VacationRepository vacationRepository ;

	@GetMapping(path = "/associate-add")
	public String associateAdd(Model model) {
		VariablesGlobales.allDepartments = departmentRepository.findAll() ;
		VariablesGlobales.sendObjects(model) ;
		return "associate-add" ;
	}
	
	@PostMapping(path="/associate-add-to-confirm-before-save")
	public String AssociateToConfirm(Model model, @ModelAttribute Associate associateReceived, @RequestParam("file")MultipartFile file) throws IOException {

		VariablesGlobales.associateToConfirm = associateReceived ;
		VariablesGlobales.associateToConfirm.setImage(VariablesGlobales.associateToModif.getImage()) ;
			
			if ( StringUtils.cleanPath(associateReceived.getFile().getOriginalFilename()).contains(".") ) { 
				associateReceived.setImage(Base64.getEncoder().encodeToString(file.getBytes())) ;
				VariablesGlobales.associateToConfirm.setImage(associateReceived.getImage())  ;
			  }
		VariablesGlobales.vacationsDayCalculOneAssociate(VariablesGlobales.associateToConfirm);
		VariablesGlobales.sendObjects(model) ;
		return "associate-confirm-before-save" ;
	}
	
	@GetMapping(path = "/associate-modif")
	public String associateModif(Model model) {
		VariablesGlobales.associateToModif = VariablesGlobales.associateToConfirm ;
		VariablesGlobales.allDepartments = departmentRepository.findAll() ;
		VariablesGlobales.sendObjects(model) ;
		return "associate-modif" ;
	}
	
	@GetMapping(path = "/associate-to-save")
	public String associateSave(Model model) {
		VariablesGlobales.departmentDetail = departmentRepository.findByName(VariablesGlobales.associateToConfirm.getDepartment()) ;
//		VariablesGlobales.departmentDetail.setTotalMember(VariablesGlobales.departmentDetail.getTotalMember()+1) ;
//		departmentRepository.save(VariablesGlobales.departmentDetail) ;
		VariablesGlobales.vacationsDayCalculOneAssociate(VariablesGlobales.associateToConfirm);
		associateRepository.save(VariablesGlobales.associateToConfirm) ;
		VariablesGlobales.associatesByDepartment = associateRepository.findByDepartment(VariablesGlobales.associateToConfirm.getDepartment()) ;
		VariablesGlobales.associateToConfirm = new Associate() ;
		VariablesGlobales.associateToModif = new Associate() ;
		VariablesGlobales.sendObjects(model) ;
		return "associate-list-one-department" ;
	}
	
	@GetMapping(path="/associate-list")
	public String associateList(Model model) {
		VariablesGlobales.allAssociates = associateRepository.findAll() ;
		VariablesGlobales.sendObjects(model) ;
		return "associate-list";
	}
	
	@GetMapping(path="/associate-detail")
	public String associateDetail(Model model, @RequestParam Long id) {
		VariablesGlobales.associateDetail = associateRepository.findById(id).get() ;
		VariablesGlobales.vacationsByAssociate = vacationRepository.findByAssociateId(id) ;
		VariablesGlobales.vacationsDayCalculOneAssociate(VariablesGlobales.associateDetail);
		associateRepository.save(VariablesGlobales.associateDetail) ;
		VariablesGlobales.sendObjects(model) ;
		return "associate-detail";
	}
	
	@GetMapping(path="/associate-to-modif-before-update")
	public String associateModifUpdate(Model model) {
		VariablesGlobales.associateToUpdate = VariablesGlobales.associateDetail ;
		VariablesGlobales.allDepartments = departmentRepository.findAll() ;
		VariablesGlobales.sendObjects(model) ;
		return "associate-modif-before-update" ;
	}
	
	@PostMapping(path = "/associate-update")
	public String associateUpdate(Model model,@ModelAttribute Associate associateReceived, @RequestParam("file")MultipartFile file) throws IOException {
		if ( StringUtils.cleanPath(associateReceived.getFile().getOriginalFilename()).contains(".") ) { 
			associateReceived.setImage(Base64.getEncoder().encodeToString(file.getBytes())) ;
		  } else {
			  associateReceived.setImage(VariablesGlobales.associateToUpdate.getImage()) ;
		}
		associateReceived.setId(VariablesGlobales.associateToUpdate.getId()) ;
		VariablesGlobales.associateToUpdate = associateReceived ;
		VariablesGlobales.vacationsByAssociate = vacationRepository.findByAssociateId(VariablesGlobales.associateToUpdate.getId()) ;
		VariablesGlobales.vacationsDayCalculOneAssociate(VariablesGlobales.associateToUpdate);
		associateRepository.save(VariablesGlobales.associateToUpdate) ;
		// VariablesGlobales.departmentDetail = departmentRepository.findByName(VariablesGlobales.associateToConfirm.getDepartment()) ;
		VariablesGlobales.departmentDetail.setName("IT") ;
		VariablesGlobales.associatesByDepartment = associateRepository.findByDepartment(VariablesGlobales.associateToUpdate.getDepartment()) ;		
		VariablesGlobales.associateDetail = VariablesGlobales.associateToUpdate ;
		VariablesGlobales.associateToUpdate = new Associate() ;
		VariablesGlobales.sendObjects(model) ;
		return "associate-detail";
	}
	
	@GetMapping(path="/associate-confirm-before-delete")
	public String associateConfirmDelete(Model model) {
		VariablesGlobales.associateToDelete = VariablesGlobales.associateDetail ;
		VariablesGlobales.sendObjects(model) ;
		return "associate-confirm-before-delete" ;
	}
	
	@GetMapping(path = "/associate-to-delete")
	public String associateDelete(Model model) {
		associateRepository.delete(VariablesGlobales.associateToDelete) ;
		// VariablesGlobales.departmentDetail = departmentRepository.findByName(VariablesGlobales.associateToConfirm.getDepartment()) ;
		VariablesGlobales.departmentDetail.setName("IT") ;
		VariablesGlobales.associatesByDepartment = associateRepository.findByDepartment(VariablesGlobales.associateDetail.getDepartment()) ;
		VariablesGlobales.associateDetail = new Associate() ;
		VariablesGlobales.sendObjects(model) ;
		return "associate-list-one-department" ;
	}
	
	@GetMapping (path = "/associate-search")
	public String searchAssociate(Model model, @RequestParam String associateToSearch) {
		VariablesGlobales.associateToSearch = associateToSearch ;
		VariablesGlobales.associatesByName = associateRepository.findByNameOrSurnameContains(associateToSearch, associateToSearch) ;
		VariablesGlobales.sendObjects(model) ;
		return "associate-search" ;
	} ;
}
