package mainPackage.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mainPackage.entities.Anniversary;
import mainPackage.repositories.AnniversaryRepository;
import mainPackage.repositories.AssociateRepository;
import mainPackage.repositories.DepartmentRepository;

@Controller
public class AnniversaryController {
	@Autowired
	AssociateRepository associateRepository ;
	@Autowired
	DepartmentRepository departmentRepository ;
	@Autowired
	AnniversaryRepository anniversaryRepository ;
	
	@Bean
	public void initMonth() {
		VariablesGlobales.allAnniversary = anniversaryRepository.findAll() ;
		if (VariablesGlobales.allAnniversary.isEmpty()) {
			anniversaryRepository.save(new Anniversary(null, "Janvier", "01", 0)) ;
			anniversaryRepository.save(new Anniversary(null, "Fevrier", "02", 0)) ;
			anniversaryRepository.save(new Anniversary(null, "Mars", "03", 0)) ;
			anniversaryRepository.save(new Anniversary(null, "Avril", "04", 0)) ;
			anniversaryRepository.save(new Anniversary(null, "Mai", "05", 0)) ;
			anniversaryRepository.save(new Anniversary(null, "juin", "06", 0)) ;
			anniversaryRepository.save(new Anniversary(null, "Juillet", "07", 0)) ;
			anniversaryRepository.save(new Anniversary(null, "Aout", "08", 0)) ;
			anniversaryRepository.save(new Anniversary(null, "Septembre", "09", 0)) ;
			anniversaryRepository.save(new Anniversary(null, "Octobre", "10", 0)) ;
			anniversaryRepository.save(new Anniversary(null, "Novembre", "11", 0)) ;
			anniversaryRepository.save(new Anniversary(null, "Decembre", "12", 0)) ;
		}
	}

	@GetMapping(path = "anniversary-list-month")
	public String anniversaryMonthList(Model model) {
		VariablesGlobales.allAnniversary = anniversaryRepository.findAll() ;
		VariablesGlobales.allAssociates = associateRepository.findAll() ;
		VariablesGlobales.allAnniversary.forEach(anniv -> { 
			anniv.setTotalAssociate(0) ;
				VariablesGlobales.allAssociates.forEach(associate -> {
					if (associate.getBirthDay() == null || associate.getBirthDay().isEmpty()) {
						
					} else {
					if (anniv.getMonthNumber().equals(associate.getBirthDay().split("-")[1])) {
						anniv.setTotalAssociate(anniv.getTotalAssociate()+1) ;	
					}
					}
				}) ;
				anniversaryRepository.save(anniv) ;
			// VariablesGlobales.associatesByAnniversaryMonth.sort
		}) ;
		VariablesGlobales.sendObjects(model) ;
		return "anniversary-list-month" ;
	}
	
	@GetMapping(path = "/anniversary-associate-list-one-month")
	public String associateListOneDept(Model model, @RequestParam String month) {
		VariablesGlobales.anniversaryDetail = anniversaryRepository.findByMonthName(month) ;
		VariablesGlobales.allAssociates = associateRepository.findAll() ;
		VariablesGlobales.associatesByAnniversaryMonth.clear() ;
		VariablesGlobales.allAssociates.forEach(associate -> {
			if (associate.getBirthDay() == null || associate.getBirthDay().isEmpty()) {
				
			} else {
				associate.setBirthDayOneTheMonth(associate.getBirthDay().split("-")[2]) ;
				if (VariablesGlobales.anniversaryDetail.getMonthNumber().equals(associate.getBirthDay().split("-")[1])) {
					VariablesGlobales.associatesByAnniversaryMonth.add(associate) ;
				}
			}
			// VariablesGlobales.associatesByAnniversaryMonth.sort
		}) ;
		VariablesGlobales.sendObjects(model) ;
		return "anniversary-associate-list-one-month" ;
	}
}
