package mainPackage.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.ui.Model;

import mainPackage.entities.Anniversary;
import mainPackage.entities.Associate;
import mainPackage.entities.Department;
import mainPackage.entities.Vacation;

public class VariablesGlobales {
	
	/************ TOTAL VARIABLES *****************************/
	public static int allDepartmentsTotal ;
	public static int allDepartmentsTotalMember ;
	public static double allDepartmentsTotalSalary ;
	

	/*********** ALL COLLECTIONS VARIABLES ********************/
	public static Collection<Associate> allAssociates;
	public static Collection<Department> allDepartments ;
	// public static Collection<Notification> allNotifications ;
	public static Collection<Anniversary> allAnniversary ;
	
	
	
	/*********** BY TYPE COLLECTIONS VARIABLES ********************/
	public static Collection<Associate> associatesByDepartment ;
	public static Collection<Associate> associatesByName ;
	public static Collection<Vacation> vacationsByAssociate ;
	public static ArrayList<Associate> associatesByAnniversaryMonth = new ArrayList<Associate>() ;
	
	/*********** NEW VARIABLES ***************/
	public static Associate newAssociate = new Associate() ;
	public static Department newDepartment = new Department() ;
	public static Vacation newVacation = new Vacation() ;
	public static String associateToSearch ;
	
	
	/*********** CONFIRM VARIABLES ***************/
	public static Associate associateToConfirm = new Associate() ;
	public static Associate associateToModif = new Associate() ;
	public static Associate associateToUpdate = new Associate() ;
	public static Associate associateToDelete = new Associate() ;
	public static Vacation vacationToConfirm = new Vacation() ;
	public static Vacation vacationToModif = new Vacation() ;
	public static Vacation vacationToUpdate = new Vacation() ;
	public static Vacation vacationToDelete = new Vacation() ;
	
	/************ STATUS VARIABLES *************/
	public static Department departmentDetail = new Department() ;
	public static Associate associateDetail = new Associate() ;
	public static Vacation vacationDetail = new Vacation() ;
	public static Anniversary anniversaryDetail = new Anniversary() ;
	
	
	
	
	
	
	
	
	
	/*********** PUBLICS METHODES ************/
	
	
	public static void sendObjects(Model model) {
		
	/******** New Variables ******/
		model.addAttribute("newAssociate", VariablesGlobales.newAssociate) ;
		model.addAttribute("newDepartment", VariablesGlobales.newDepartment) ;
		model.addAttribute("newVacation", VariablesGlobales.newVacation) ;
		model.addAttribute("associateToSearch", VariablesGlobales.associateToSearch) ;
		
		
	/******** Variables To Confirm ***********/
		model.addAttribute("associateToConfirm", VariablesGlobales.associateToConfirm) ;
		
	/******** Variables To Modif, Delete & Update ***********/
		model.addAttribute("associateToModif", VariablesGlobales.associateToModif) ;
		model.addAttribute("associateToUpdate", VariablesGlobales.associateToUpdate) ;
		model.addAttribute("associateToDelete", VariablesGlobales.associateToDelete) ;
		model.addAttribute("vacationToConfirm", VariablesGlobales.vacationToConfirm) ;
		model.addAttribute("vacationToModif", VariablesGlobales.vacationToModif) ;
		model.addAttribute("vacationToUpdate", VariablesGlobales.vacationToUpdate) ;
		model.addAttribute("vacationToDelete", VariablesGlobales.vacationToDelete) ;
		
		
	/******** Variables For Status ***********/
		model.addAttribute("departmentDetail", VariablesGlobales.departmentDetail) ;
		model.addAttribute("associateDetail", VariablesGlobales.associateDetail) ;
		model.addAttribute("vacationDetail", VariablesGlobales.vacationDetail) ;
		model.addAttribute("anniversaryDetail", VariablesGlobales.anniversaryDetail) ;
		
	/******** Variables Collections By Type ***********/
		model.addAttribute("associatesByDepartment", VariablesGlobales.associatesByDepartment) ;
		model.addAttribute("associatesByName", VariablesGlobales.associatesByName) ;
		model.addAttribute("vacationsByAssociate", VariablesGlobales.vacationsByAssociate) ;
		model.addAttribute("associatesByAnniversaryMonth", VariablesGlobales.associatesByAnniversaryMonth) ;
		
	/******** Variables Collections ALL ***********/
		model.addAttribute("allAssociates", VariablesGlobales.allAssociates) ;
		model.addAttribute("allDepartments", VariablesGlobales.allDepartments) ;
		// model.addAttribute("allNotifications", VariablesGlobales.allNotifications) ;
		model.addAttribute("allAnniversary", VariablesGlobales.allAnniversary) ;
		
	/*************** TOTAL SALARY ******************************/
		model.addAttribute("allDepartmentsTotal", VariablesGlobales.allDepartmentsTotal) ;
		model.addAttribute("allDepartmentsTotalMember", VariablesGlobales.allDepartmentsTotalMember) ;
		model.addAttribute("allDepartmentsTotalSalary", VariablesGlobales.allDepartmentsTotalSalary) ;
	}
	
	public static void vacationsDayCalculOneAssociate(Associate associate) {
		String today ;
		Calendar c = Calendar.getInstance() ;
		int year = c.get(Calendar.YEAR) ;
		int mouth = c.get(Calendar.MONTH) + 1 ;
		int day = c.get(Calendar.DAY_OF_MONTH) ;
		int test = day / 10 ;
		int testMouth = mouth / 10 ;
		if (test == 0) {
			if (testMouth == 0) {
				today = year + "-0" + mouth + "-" + "0" + day ;
			} else {
				today = year + "-" + mouth + "-" + "0" + day ;
			}
			
		} else {
			if (testMouth == 0) {
				today = year + "-0" + mouth + "-" + day ;
			} else {
				today = year + "-" + mouth + "-" + day ;
			}
			
		}
		System.out.println("today = " + today);
		
		if (associate.getStartDay() == null || associate.getStartDay().isEmpty()) {
			
		} else {
			// Split date of associateStart and today for test each split
			String tabValueOfToday[] = today.split("-") ;
			String tabValueOfAssociateDayStart[] = associate.getStartDay().split("-") ;
			System.out.println("year today string tab = " + tabValueOfToday[0]);
			System.out.println("year start day string tab = " + tabValueOfAssociateDayStart[0]);
			int yearToday = Integer.parseInt(tabValueOfToday[0]) ;
			int yearStartDay = Integer.parseInt(tabValueOfAssociateDayStart[0]) ;
			int mouthToday = Integer.parseInt(tabValueOfToday[1]) ;
			int mouthStartDay = Integer.parseInt(tabValueOfAssociateDayStart[1]) ; 
			System.out.println("year today int = " + yearToday);
			System.out.println("year Start Day int  = " + yearStartDay );
			System.out.println("mouth today int = " + mouthToday );
			System.out.println("mouth Start Day int = " + mouthStartDay);
			// Vacation calcul
			int numberMouthForVacation = 0 ;

			if (yearToday == yearStartDay) {
				numberMouthForVacation = mouthToday - mouthStartDay ; 
			} else {
				while (yearStartDay < yearToday) {
					if (( yearStartDay + 1 ) == yearToday) {
						numberMouthForVacation += ( mouthToday ) + (12 - mouthStartDay) ;
					} else {
						numberMouthForVacation += 12 ;
					}		
					yearStartDay ++ ;
				}
			}
			System.out.println("nombre de mouth de service pour vacances  = " + numberMouthForVacation);
			
			// Affectation vacation to associate
			associate.setVacationsSinceStarted(numberMouthForVacation * (2.5));
			System.out.println("Jour de conges Total de " + associate.getName() + " = " +  associate.getVacationsSinceStarted());	
			System.out.println(associate.getName() + " a pris les vacances suivantes : " );
			associate.setVacationsRemaining(associate.getVacationsSinceStarted());
			if (VariablesGlobales.vacationsByAssociate!= null ) {
				VariablesGlobales.vacationsByAssociate.forEach(vacationTaked -> {
					System.out.println(" - Du " + vacationTaked.getStartDay() + " au " + vacationTaked.getEndDay() + " pour " + vacationTaked.getNumberOfDay() + " Jours");
					associate.setVacationsRemaining(associate.getVacationsSinceStarted() - vacationTaked.getNumberOfDay());
				}); ;
				System.out.println("Jour de congés apres reduction des jours pris de " + associate.getName() + " = " +  associate.getVacationsRemaining());
				
			}
		}
		
		
			}
	
	
	public static void calculVacationNumberDay(Vacation vacation) {
		String[] tabOfStart = vacation.getStartDay().split("-") ;
		String[] tabOfEnd = vacation.getEndDay().split("-") ;
		int mouthStart = Integer.parseInt(tabOfStart[1]) ;
		int mouthEnd = Integer.parseInt(tabOfEnd[1]) ; 
		int dayStart = Integer.parseInt(tabOfStart[2]) ;
		int dayEnd = Integer.parseInt(tabOfEnd[2]) ;
		vacation.setNumberOfDay(0);
		if (mouthStart == mouthEnd) {
			vacation.setNumberOfDay(dayEnd - dayStart);
		} else {
			while (mouthStart < mouthEnd) {
				if ((mouthStart + 1) == mouthEnd) {
					vacation.setNumberOfDay(vacation.getNumberOfDay() + (dayEnd + (30 - dayStart)));
				} else {
					vacation.setNumberOfDay(vacation.getNumberOfDay() + 30);
				}
				mouthStart ++ ;
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
