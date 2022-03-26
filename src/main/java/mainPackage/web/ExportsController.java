package mainPackage.web;

import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import mainPackage.repositories.AssociateRepository;
import mainPackage.repositories.DepartmentRepository;

@Controller
public class ExportsController {
	
	@Autowired
	AssociateRepository associateRepository ;
	@Autowired
	DepartmentRepository departmentRepository ;

	@GetMapping(path = "/ExportOneDepartmentList")
	public String ExportOneDepartmentList(Model model, @RequestParam String departmentName) {
		VariablesGlobales.departmentDetail = departmentRepository.findByName(departmentName) ;
		VariablesGlobales.associatesByDepartment = associateRepository.findByDepartment(departmentName) ;
		
	/**************** Creation Fichier PDF ****************************/
	
	    	String chemin = "C:\\HR-Docs\\"  + VariablesGlobales.departmentDetail.getName() + ".pdf"  ;
            	try {
        			// creation of the document with a certain size and certain margins
        			Document document = new Document(PageSize.A4);

                    
        			// creating table and set the column width
        			PdfPTable table = new PdfPTable(4);
        			float widths[] = { 7, 4, 2, 4 };
        			table.setWidths(widths);
        			table.setHeaderRows(2);

        			// add cell of table - header cell
        			PdfPCell cell = new PdfPCell(new Phrase("Name"));
        			cell.setBackgroundColor(new BaseColor(131, 110, 110));
        			table.addCell(cell);

        			cell = new PdfPCell(new Phrase("Position"));
        			cell.setBackgroundColor(new BaseColor(131, 110, 110));
        			table.addCell(cell);

        			cell = new PdfPCell(new Phrase("Tel/ext    "));
        			cell.setBackgroundColor(new BaseColor(131, 110, 110));
        			table.addCell(cell);
        			
        			cell = new PdfPCell(new Phrase("Personal Mobile"));
        			cell.setBackgroundColor(new BaseColor(131, 110, 110));
        			table.addCell(cell);

        			
        			// looping the table cell for adding definition
        			VariablesGlobales.associatesByDepartment.forEach(associate -> {
        				PdfPCell cell1 ;
        				Phrase ph;
        				cell1 = new PdfPCell();
        				ph = new Phrase(associate.getName() + " " + associate.getSurname());
        				cell1.addElement(ph);
        				table.addCell(cell1);

        				cell1 = new PdfPCell();
        				ph = new Phrase("  "+ associate.getPosition());
        				cell1.addElement(ph);
        				table.addCell(cell1);

        				cell1 = new PdfPCell();
            			ph = new Phrase("  "+associate.getFixeNumber());
            			cell1.addElement(ph);
            			table.addCell(cell1);

        				cell1 = new PdfPCell();
            			ph = new Phrase("        "+associate.getPhoneNumber());
            			cell1.addElement(ph);
            			table.addCell(cell1);
						
        				
        			}) ;
        			
        			// write the all into a file and save it.
        			PdfWriter.getInstance(document, new FileOutputStream(chemin));
        			document.open();
        			// document.add(tableBanner);
        			document.add(table);
        			document.close();
        			System.out.println("Successfull.");
        		} catch (DocumentException e) {
        			e.printStackTrace();
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
            
	/**************** FIN Creation Fichier PDF ****************************/
		
		VariablesGlobales.sendObjects(model) ;
		return "associate-list-one-department" ;
	
	}
	
	@GetMapping(path = "/ExportAllAssociateList")
	public String ExportAllAssociateList(Model model) {
		VariablesGlobales.allDepartments = departmentRepository.findAll() ;
		VariablesGlobales.allAssociates = associateRepository.findAll() ;
		
	/**************** Creation Fichier PDF ****************************/
	
	    	String chemin = "C:\\HR-Docs\\HR-All-Associates-list.pdf"  ;
            	try {
        			// creation of the document with a certain size and certain margins
        			Document document = new Document(PageSize.A4);

                    
        			// creating table and set the column width
        			PdfPTable table = new PdfPTable(4);
        			float widths[] = { 7, 4, 2, 4 };
        			table.setWidths(widths);
        			table.setHeaderRows(2);

        			// add cell of table - header cell
        			PdfPCell cell = new PdfPCell(new Phrase("Name"));
        			cell.setBackgroundColor(new BaseColor(131, 110, 110));
        			table.addCell(cell);

        			cell = new PdfPCell(new Phrase("Position"));
        			cell.setBackgroundColor(new BaseColor(131, 110, 110));
        			table.addCell(cell);

        			cell = new PdfPCell(new Phrase("Tel/ext    "));
        			cell.setBackgroundColor(new BaseColor(131, 110, 110));
        			table.addCell(cell);
        			
        			cell = new PdfPCell(new Phrase("Personal Mobile"));
        			cell.setBackgroundColor(new BaseColor(131, 110, 110));
        			table.addCell(cell);

        			
        			// looping the table cell for adding definition
        			VariablesGlobales.allDepartments.forEach(department -> {
        				PdfPCell cell1 ;
        				Phrase ph;



        				cell1 = new PdfPCell();
        				ph = new Phrase("                                               " + department.getName().toUpperCase());
        				cell1.setColspan(4) ;
        				cell1.addElement(ph);
        				table.addCell(cell1);


            			
            			associateRepository.findByDepartment(department.getName()).forEach(associate ->{
            				PdfPCell cell2 ;
            				Phrase ph2;
            				cell2 = new PdfPCell();
            				ph2 = new Phrase(associate.getName() + " " + associate.getSurname());
            				cell2.addElement(ph2);
            				table.addCell(cell2);
            				
            				cell2 = new PdfPCell();
            				ph2 = new Phrase(associate.getPosition());
            				cell2.addElement(ph2);
            				table.addCell(cell2);

                			if (associate.getPhoneNumber().isEmpty()) {
                				cell2 = new PdfPCell();
	                			ph2 = new Phrase("  "+associate.getFixeNumber());
	                			cell2.addElement(ph2);
	                			table.addCell(cell2);
	                			
	                			cell2 = new PdfPCell();
                    			ph2 = new Phrase("");
                    			cell2.disableBorderSide(0) ;
                    			cell2.disableBorderSide(1) ;
                    			cell2.disableBorderSide(2) ;
                    			cell2.disableBorderSide(3) ;
                    			cell2.addElement(ph2);
                    			table.addCell(cell2);
							} else {
								cell2 = new PdfPCell();
	                			ph2 = new Phrase("  "+associate.getFixeNumber());
	                			cell2.addElement(ph2);
	                			table.addCell(cell2);
	                			
	                			cell2 = new PdfPCell();
                    			ph2 = new Phrase("        "+associate.getPhoneNumber());
                    			cell2.addElement(ph2);
                    			table.addCell(cell2);
							}

				
            				
            			}) ;
        			}) ;
        			
        			// write the all into a file and save it.
        			PdfWriter.getInstance(document, new FileOutputStream(chemin));
        			document.open();
        			document.add(table);
        			document.close();
        			System.out.println("Successfull.");
        		} catch (DocumentException e) {
        			e.printStackTrace();
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
	
		
	/**************** Creation Fichier PDF ****************************/
		
		VariablesGlobales.sendObjects(model) ;
		return "associate-list" ;
	
}
	
	@GetMapping(path = "/ExportOneDepartmentListVacation")
	public String ExportOneDepartmentListVacation(Model model, @RequestParam String departmentName) {
		VariablesGlobales.departmentDetail = departmentRepository.findByName(departmentName) ;
		VariablesGlobales.associatesByDepartment = associateRepository.findByDepartment(departmentName) ;
		
	/**************** Creation Fichier PDF ****************************/
	
	    	String chemin = "C:\\HR-Docs\\"  + VariablesGlobales.departmentDetail.getName() + "-Vacations.pdf"  ;
            	try {
        			// creation of the document with a certain size and certain margins
        			Document document = new Document(PageSize.A4);

                    
        			// creating table and set the column width
        			PdfPTable table = new PdfPTable(5);
        			float widths[] = { 7, 4, 2, 4, 5 };
        			table.setWidths(widths);
        			table.setHeaderRows(2);

        			// add cell of table - header cell
        			PdfPCell cell = new PdfPCell(new Phrase("Name"));
        			cell.setBackgroundColor(new BaseColor(131, 110, 110));
        			table.addCell(cell);

        			cell = new PdfPCell(new Phrase("Position"));
        			cell.setBackgroundColor(new BaseColor(131, 110, 110));
        			table.addCell(cell);

        			cell = new PdfPCell(new Phrase("Tel/ext    "));
        			cell.setBackgroundColor(new BaseColor(131, 110, 110));
        			table.addCell(cell);
        			
        			cell = new PdfPCell(new Phrase("Personal Mobile"));
        			cell.setBackgroundColor(new BaseColor(131, 110, 110));
        			table.addCell(cell);
        			
        			cell = new PdfPCell(new Phrase("Vacantion's Day Remainig"));
        			cell.setBackgroundColor(new BaseColor(131, 110, 110));
        			table.addCell(cell);

        			
        			// looping the table cell for adding definition
        			VariablesGlobales.associatesByDepartment.forEach(associate -> {
        				PdfPCell cell1 ;
        				Phrase ph;
        				cell1 = new PdfPCell();
        				ph = new Phrase(associate.getName() + " " + associate.getSurname());
        				cell1.addElement(ph);
        				table.addCell(cell1);

        				cell1 = new PdfPCell();
        				ph = new Phrase("  "+ associate.getPosition());
        				cell1.addElement(ph);
        				table.addCell(cell1);

        				cell1 = new PdfPCell();
            			ph = new Phrase("  "+associate.getFixeNumber());
            			cell1.addElement(ph);
            			table.addCell(cell1);

        				cell1 = new PdfPCell();
            			ph = new Phrase("        "+associate.getPhoneNumber());
            			cell1.addElement(ph);
            			table.addCell(cell1);
            			
            			cell1 = new PdfPCell();
            			ph = new Phrase(( (int) associate.getVacationsRemaining() ) + " Days");
            			cell1.addElement(ph);
            			table.addCell(cell1);
						
        				
        			}) ;
        			
        			// write the all into a file and save it.
        			PdfWriter.getInstance(document, new FileOutputStream(chemin));
        			document.open();
        			// document.add(tableBanner);
        			document.add(table);
        			document.close();
        			System.out.println("Successfull.");
        		} catch (DocumentException e) {
        			e.printStackTrace();
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
            
	/**************** FIN Creation Fichier PDF ****************************/
		
		VariablesGlobales.sendObjects(model) ;
		return "associate-list-one-department-for-vacation" ;
	
	}
	
	@GetMapping(path = "/ExportAllAssociateListVacation")
	public String ExportAllAssociateListVacation(Model model) {
		VariablesGlobales.allDepartments = departmentRepository.findAll() ;
		VariablesGlobales.allAssociates = associateRepository.findAll() ;
		
	/**************** Creation Fichier PDF ****************************/
	
	    	String chemin = "C:\\HR-Docs\\HR-All-Associates-list-Vacation.pdf"  ;
            	try {
        			// creation of the document with a certain size and certain margins
        			Document document = new Document(PageSize.A4);

                    
        			// creating table and set the column width
        			PdfPTable table = new PdfPTable(5);
        			float widths[] = { 7, 4, 2, 4, 4 };
        			table.setWidths(widths);
        			table.setHeaderRows(2);

        			// add cell of table - header cell
        			PdfPCell cell = new PdfPCell(new Phrase("Name"));
        			cell.setBackgroundColor(new BaseColor(131, 110, 110));
        			table.addCell(cell);

        			cell = new PdfPCell(new Phrase("Position"));
        			cell.setBackgroundColor(new BaseColor(131, 110, 110));
        			table.addCell(cell);

        			cell = new PdfPCell(new Phrase("Tel/ext    "));
        			cell.setBackgroundColor(new BaseColor(131, 110, 110));
        			table.addCell(cell);
        			
        			cell = new PdfPCell(new Phrase("Personal Mobile"));
        			cell.setBackgroundColor(new BaseColor(131, 110, 110));
        			table.addCell(cell);
        			
        			cell = new PdfPCell(new Phrase("Vacation's Days Remaining"));
        			cell.setBackgroundColor(new BaseColor(131, 110, 110));
        			table.addCell(cell);

        			
        			// looping the table cell for adding definition
        			VariablesGlobales.allDepartments.forEach(department -> {
        				PdfPCell cell1 ;
        				Phrase ph;



        				cell1 = new PdfPCell();
        				ph = new Phrase("                                               " + department.getName().toUpperCase());
        				cell1.setColspan(5) ;
        				cell1.addElement(ph);
        				table.addCell(cell1);


            			
            			associateRepository.findByDepartment(department.getName()).forEach(associate ->{
            				PdfPCell cell2 ;
            				Phrase ph2;
            				cell2 = new PdfPCell();
            				ph2 = new Phrase(associate.getName() + " " + associate.getSurname());
            				cell2.addElement(ph2);
            				table.addCell(cell2);
            				
            				cell2 = new PdfPCell();
            				ph2 = new Phrase(associate.getPosition());
            				cell2.addElement(ph2);
            				table.addCell(cell2);

                			if (associate.getPhoneNumber().isEmpty()) {
                				cell2 = new PdfPCell();
	                			ph2 = new Phrase("  "+associate.getFixeNumber());
	                			cell2.addElement(ph2);
	                			table.addCell(cell2);
	                			
	                			cell2 = new PdfPCell();
                    			ph2 = new Phrase("");
                    			cell2.disableBorderSide(0) ;
                    			cell2.disableBorderSide(1) ;
                    			cell2.disableBorderSide(2) ;
                    			cell2.disableBorderSide(3) ;
                    			cell2.addElement(ph2);
                    			table.addCell(cell2);
							} else {
								cell2 = new PdfPCell();
	                			ph2 = new Phrase("  "+associate.getFixeNumber());
	                			cell2.addElement(ph2);
	                			table.addCell(cell2);
	                			
	                			cell2 = new PdfPCell();
                    			ph2 = new Phrase("        "+associate.getPhoneNumber());
                    			cell2.addElement(ph2);
                    			table.addCell(cell2);
							}
                			
                			cell2 = new PdfPCell();
            				ph2 = new Phrase(( (int) associate.getVacationsRemaining() ) + " Days");
            				cell2.addElement(ph2);
            				table.addCell(cell2);

				
            				
            			}) ;
        			}) ;
        			
        			// write the all into a file and save it.
        			PdfWriter.getInstance(document, new FileOutputStream(chemin));
        			document.open();
        			document.add(table);
        			document.close();
        			System.out.println("Successfull.");
        		} catch (DocumentException e) {
        			e.printStackTrace();
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
	
		
	/**************** FIN Creation Fichier PDF ****************************/
		
		VariablesGlobales.sendObjects(model) ;
		return "vacation-department-list" ;
	
}
	
	@GetMapping(path = "/ExportOneAssociateVacationDetail") 
	public String ExportOneAssociateVacationDetail(Model model) {
		
	/**************** Creation Fichier PDF ****************************/
	
	    	String chemin = "C:\\HR-Docs\\Vacation Details - " + VariablesGlobales.associateDetail.getName() + " " + VariablesGlobales.associateDetail.getSurname() + ".pdf"  ;
            	try {
        			// creation of the document with a certain size and certain margins
        			Document document = new Document(PageSize.A4);

        			
                    String espace = "                          " ;
                    PdfWriter.getInstance(document, new FileOutputStream(chemin));
                    //ouverture du document
                    document.open();
                    //insertion de l'entete
                    Paragraph p = new Paragraph("**** Informations de " + VariablesGlobales.associateDetail.getName() + " " + VariablesGlobales.associateDetail.getSurname() + " ****");
                    p.setAlignment(Element.ALIGN_CENTER);
                    p.setSpacingAfter(20f);
                    p.setLeading(20f);
                    document.add(p);
                    
                    String radisson = "                                            Radisson Collection" ;
                    PdfPTable tab = new PdfPTable(1);
                    PdfPCell c1 = new PdfPCell(new Phrase(radisson));
                    tab.addCell(c1);
                    document.add(tab);
                    
                    // Insertion des Paragraph
                    
                    Paragraph pName = new Paragraph(espace + "Name   :   " + VariablesGlobales.associateDetail.getName());
                    document.add(pName);
                    
                    Paragraph pSurname = new Paragraph(espace + "Surname   :   " + VariablesGlobales.associateDetail.getSurname() );
                    document.add(pSurname);
                    
                    Paragraph pPiece = new Paragraph(espace + "Position   :   " + VariablesGlobales.associateDetail.getPosition() );
                    document.add(pPiece);
                    
                    Paragraph pNumeroPiece = new Paragraph(espace + "Tel / Fixe   :   " + VariablesGlobales.associateDetail.getFixeNumber() );
                    document.add(pNumeroPiece);
                    
                    Paragraph pDepartement = new Paragraph(espace + "Mobile Personnel   :   " + VariablesGlobales.associateDetail.getPhoneNumber() );
                    document.add(pDepartement);
                    
                    Paragraph pPersonneVisite = new Paragraph(espace + "Start Day   :   " + VariablesGlobales.associateDetail.getStartDay() );
                    document.add(pPersonneVisite);
                    
                    Paragraph pBadge = new Paragraph(espace + "Vacation's Day Since Started   :   " + VariablesGlobales.associateDetail.getVacationsSinceStarted() + " Days" );
                    document.add(pBadge);
                    
                    Paragraph telParagraph = new Paragraph(espace + "Vacation's Day Remaining   :   " + VariablesGlobales.associateDetail.getVacationsRemaining() + " Days" );
                    document.add(telParagraph);
                    
                    Paragraph dateParagraph = new Paragraph(espace);
                    document.add(dateParagraph);
                    
                    Paragraph entreeParagraph = new Paragraph(espace + espace + espace +  "DETAILS :   ");
                    document.add(entreeParagraph);
                    
                    document.add(dateParagraph);
                    
                    if (VariablesGlobales.vacationsByAssociate.isEmpty()) {
                    	Paragraph sortieParagraph = new Paragraph(espace + "      - Pas de Congés Pris ");
                        document.add(sortieParagraph);
					} else {
						Paragraph sortieParagraph = new Paragraph(espace + VariablesGlobales.associateDetail.getName() + " " + VariablesGlobales.associateDetail.getSurname() + " a pris les Congés Suivants : " );
	                    document.add(sortieParagraph);
	                    VariablesGlobales.vacationsByAssociate.forEach(vacationTaked -> {
	                    	Paragraph vacationParagraph = new Paragraph(espace + "       - Du    " + vacationTaked.getStartDay() + "   au   " + vacationTaked.getEndDay() + "   pour   " + vacationTaked.getNumberOfDay() + " jours ");
	                        try {
								document.add(vacationParagraph);
							} catch (DocumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	                    });
					}
                    
                   
                    
                    Paragraph espaceParagraph = new Paragraph(" "
                    		+ " ");
                    document.add(espaceParagraph);
                    
                    PdfPTable tab2 = new PdfPTable(1);
                    PdfPCell c2 = new PdfPCell(new Phrase(radisson));
                    tab2.addCell(c2);
                    document.add(tab2);
                    //fermetture du document
                   
        			
    			
    			// write the all into a file and save it.
    			document.close();
    			System.out.println("Successfull.");
    		} catch (DocumentException e) {
    			e.printStackTrace();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}

	
/**************** Creation Fichier PDF ****************************/
		
		VariablesGlobales.sendObjects(model);
		return "vacation-edit" ;
	}
	
	@GetMapping(path = "/ExportOneAnniveraryList")
	public String ExportOneAnniveraryList(Model model) {
		
		
	/**************** Creation Fichier PDF ****************************/
	
	    	String chemin = "C:\\HR-Docs\\Anniversary-"  + VariablesGlobales.anniversaryDetail.getMonthName() + ".pdf"  ;
            	try {
        			// creation of the document with a certain size and certain margins
        			Document document = new Document(PageSize.A4);

                    
        			// creating table and set the column width
        			PdfPTable table = new PdfPTable(4);
        			float widths[] = { 1, 4, 2, 4 };
        			table.setWidths(widths);
        			table.setHeaderRows(2);

        			// add cell of table - header cell
        			
        			PdfPCell cell = new PdfPCell(new Phrase("N°"));
        			cell.setBackgroundColor(new BaseColor(0, 128, 255));
        			table.addCell(cell);

        			cell = new PdfPCell(new Phrase("Name/Surname"));
        			cell.setBackgroundColor(new BaseColor(0, 128, 255));
        			table.addCell(cell);

        			cell = new PdfPCell(new Phrase("Date de Naissance"));
        			cell.setBackgroundColor(new BaseColor(0, 128, 255));
        			table.addCell(cell);
        			
        			cell = new PdfPCell(new Phrase("Departement"));
        			cell.setBackgroundColor(new BaseColor(0, 128, 255));
        			table.addCell(cell);

        			
        			// looping the table cell for adding definition
        			VariablesGlobales.associatesByAnniversaryMonth.forEach(associate -> {
        				PdfPCell cell1 ;
        				Phrase ph;
        				cell1 = new PdfPCell();
        				ph = new Phrase(""+(VariablesGlobales.associatesByAnniversaryMonth.indexOf(associate) + 1 ));
        				cell1.addElement(ph);
        				table.addCell(cell1);
        				
        				cell1 = new PdfPCell();
        				ph = new Phrase(associate.getName() + " " + associate.getSurname());
        				cell1.addElement(ph);
        				table.addCell(cell1);

        				cell1 = new PdfPCell();
            			ph = new Phrase(associate.getBirthDayOneTheMonth() + " " + VariablesGlobales.anniversaryDetail.getMonthName());
            			cell1.addElement(ph);
            			table.addCell(cell1);

        				cell1 = new PdfPCell();
            			ph = new Phrase(associate.getDepartment());
            			cell1.addElement(ph);
            			table.addCell(cell1);
						
        				
        			}) ;
        			
        			// write the all into a file and save it.
        			PdfWriter.getInstance(document, new FileOutputStream(chemin));
        			document.open();
        			// document.add(tableBanner);
        			 Paragraph p = new Paragraph("****  Associate's Anniversary ****");
                     p.setAlignment(Element.ALIGN_CENTER);
                     p.setSpacingAfter(20f);
                     p.setLeading(20f);
                     document.add(p);
                     
                     Paragraph p2 = new Paragraph("      Radisson Collection");
                     p2.setAlignment(Element.ALIGN_CENTER);
                     p2.setSpacingAfter(20f);
                     p2.setLeading(20f);
                     document.add(p2);
        			document.add(table);
        			document.close();
        			System.out.println("Successfull.");
        		} catch (DocumentException e) {
        			e.printStackTrace();
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
            
	/**************** FIN Creation Fichier PDF ****************************/
		
		VariablesGlobales.sendObjects(model) ;
		return "anniversary-associate-list-one-month" ;
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
