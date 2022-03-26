//package mainPackage.web;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//import mainPackage.entities.ColonneOfRow0;
//import mainPackage.repositories.AssociateRepository;
//import mainPackage.repositories.DepartmentRepository;
//
// 
//
//@Controller
//public class ImportController {
//	@Autowired
//	AssociateRepository associateRepository ;
//	@Autowired
//	DepartmentRepository departmentRepository ;
//
//	
//	
//	@PostMapping("/import")
//	public String mapReapExcelDatatoDB(Model model, @RequestParam("file") MultipartFile reapExcelDataFile) throws Exception {
//	    
//	    // List<Test> tempStudentList = new ArrayList<Test>();
//	    XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
//	    XSSFSheet worksheet = workbook.getSheetAt(0);
//	    XSSFRow row0 = worksheet.getRow(0);
//        
//        short minColIx = row0.getFirstCellNum();
//        short maxColIx = row0.getLastCellNum();
//        for(short colIx=minColIx; colIx<maxColIx; colIx++) {
//          XSSFCell cell = row0.getCell(colIx);
//          System.out.println(cell.toString());
//          VBImport.colonneOfRow0sArrayList.add(new ColonneOfRow0(colIx , cell)) ;
//        }
//        
//        VBImport.colonneOfRow0sArrayList.forEach( col -> {
//        	System.out.println(col.toString());
//        }) ;
////	    for(int i=0;i<worksheet.getPhysicalNumberOfRows() ;i++) {
////	        Test tempStudent = new Test();
////	            
////	        XSSFRow row = worksheet.getRow(i);
////	        
////	        short minColIx = row.getFirstCellNum();
////	        short maxColIx = row.getLastCellNum();
////	        for(short colIx=minColIx; colIx<maxColIx; colIx++) {
////	          XSSFCell cell = row.getCell(colIx);
////	          System.out.println(cell.toString()); ;
////	        }
////	        tempStudent.setId(row.getCell(0).getStringCellValue());
////	        tempStudent.setContent(row.getCell(1).getStringCellValue());
////	        tempStudentList.add(tempStudent);   
//	        
////	    }
//	    
////	        tempStudentList.forEach( elt -> {
////	        	System.out.println(elt.toString());
////	    });
//	    
//	    workbook.close() ;
//	    
//	    VariablesGlobales.allDepartments = departmentRepository.findAll() ;
//		VariablesGlobales.sendObjects(model) ;
//		return "index" ;
//	}
//}
//	