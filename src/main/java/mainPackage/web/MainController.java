package mainPackage.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import mainPackage.repositories.AssociateRepository;
import mainPackage.repositories.DepartmentRepository;

@Controller
public class MainController {
	@Autowired
	AssociateRepository associateRepository ;
	@Autowired
	DepartmentRepository departmentRepository ;

	@GetMapping(path = {"/","/index"})
	public String home(Model model) {
		VariablesGlobales.allDepartments = departmentRepository.findAll() ;
		VariablesGlobales.sendObjects(model) ;
		return "index" ;
	}
}
