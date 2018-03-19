package tr.com.beinplanner.controllers.member;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.user.dao.User;
import tr.com.beinplanner.user.service.UserService;
import tr.com.beinplanner.util.LangUtil;
import tr.com.beinplanner.util.OhbeUtil;
import tr.com.beinplanner.util.ResultStatuObj;
import tr.com.beinplanner.util.SessionUtil;
import tr.com.beinplanner.util.UserTypes;
@RestController
@RequestMapping("/bein/upload")
public class UploadController {

	@Autowired
	UserService userService;
	
	@Autowired
	LoginSession loginSession;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/uploadProfileFile", method = RequestMethod.POST,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE) 
	public  void uploadProfileFile(@RequestParam("uploadedFile") MultipartFile multipartFile,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
			User sessionUser=(User)request.getSession().getAttribute(SessionUtil.SESSION_USER);
			if(sessionUser!=null){
			HttpSession session = request.getSession(true);
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> uploadedItems = null;
			FileItem fileItem = null;
			
			long userId=Long.parseLong(request.getParameter("userId"));
			
			User user=userService.findUserById(userId);
			deleteExistProfileFile(user.getProfileUrl());
			try {
				
				
				
				String fileName = multipartFile.getOriginalFilename();
				byte[] source = multipartFile.getBytes();
				
				ByteArrayInputStream content = new ByteArrayInputStream(source);
				
				String myFullFileName = fileName;
				String myFileNameArr[] = myFullFileName.split("\\.");
				String extension = myFileNameArr[myFileNameArr.length-1];// Windows
				String myFileName=userId+"."+extension;
				
				user.setProfileUrl(myFileName);
				user.setUrlType(1);
				userService.create(user);
        		createFileInAmazon(request,content, myFileName);
        		
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
			
		}
	
	private void deleteExistProfileFile(String fileName){
		
		File file=new File(OhbeUtil.ROOT_FIRM_FOLDER+"/"+fileName);
		if(file.exists()){
			file.delete();
		}
		
	}
	
	

	
	@RequestMapping(value="/downloadExcelTemplate", method = RequestMethod.POST) 
	public void downloadExcelTemplate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	
		
		
		XSSFWorkbook wb = downloadExcelTemplate(loginSession.getUser().getFirmId());
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		wb.write(bo);
		ServletOutputStream outStream = response.getOutputStream();

		String mimetype = "application/octet-stream";
		// }
		response.setContentType(mimetype);
		response.setContentLength(bo.size());
		String fileName = "UserList.xlsx";

		// sets HTTP header
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		outStream.write(bo.toByteArray());

		outStream.flush();
		outStream.close();
	}
	
	@RequestMapping(value="/progressListener", method = RequestMethod.POST) 
	public @ResponseBody HmiResultObj progressListener(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		HmiResultObj hmiResultObj=(HmiResultObj)session.getAttribute(ResultStatuObj.EXCEL_STATU);
		return (HmiResultObj)session.getAttribute(ResultStatuObj.EXCEL_STATU);
	}
	
	
	
	@PostMapping(value="/uploadMembers/{fileId}") 
	public void uploadMembers(@RequestParam("userfile") MultipartFile multipartFile,@PathVariable("fileId") String fileId,HttpServletRequest request  ) throws ServletException, IOException{
		 
		HttpSession session = request.getSession(true);
		
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultStatu("pleaseWait");
		hmiResultObj.setResultMessage("pleaseWait");
		hmiResultObj.setLoadedValue(20);
		session.setAttribute(ResultStatuObj.EXCEL_STATU,hmiResultObj);
	
		try {
			String fileName = multipartFile.getOriginalFilename();
			byte[] source = multipartFile.getBytes();
			ByteArrayInputStream content = new ByteArrayInputStream(source);
			
			File file= createFileInAmazon(request,content, fileName);
			
			hmiResultObj.setResultStatu("pleaseWait");
			hmiResultObj.setResultMessage("pleaseWait");
			session.setAttribute(ResultStatuObj.EXCEL_STATU,hmiResultObj);
			String result=loginListesiniExcelFileIleYukle(file, session);
			
			file.delete();
			
			/*
			hmiResultObj=(HmiResultObj)session.getAttribute(ResultStatuObj.EXCEL_STATU);
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
			hmiResultObj.setResultMessage("DATA YÜKLEME BAŞLADI. LÜTFEN BEKLEYİNİZ.........");
			hmiResultObj.setLoadedValue(100);
			
			
			session.setAttribute(ResultStatuObj.EXCEL_STATU,hmiResultObj);*/
			
		} catch (Exception e) {
			e.printStackTrace();
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
			hmiResultObj.setResultMessage("error_file_upload");
		}
		
	}
	
	private File createFileInAmazon(HttpServletRequest request,ByteArrayInputStream filecontent,String newFileName) throws IOException{
		
		File dir=new File(OhbeUtil.ROOT_FIRM_FOLDER);
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		File file=new File(OhbeUtil.ROOT_FIRM_FOLDER+newFileName);
		FileOutputStream out = new FileOutputStream(file);
		//InputStream    filecontent = itemFile.getInputStream();

	        int read = 0;
	        final byte[] bytes = new byte[1024];

	        while ((read = filecontent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
		
	        if (out != null) {
	            out.close();
	        }
	        if (filecontent != null) {
	            filecontent.close();
	        }
		return file;
		
	}
	
	
	
	
	public String loginListesiniExcelFileIleYukle(File excelFile,HttpSession session) {
	       
		HmiResultObj excelSessionObj=(HmiResultObj)session.getAttribute(ResultStatuObj.EXCEL_STATU);
    	
    	List<User> list = new ArrayList<User>();
		String result="";
		XSSFWorkbook wb=null;
        try {
	        wb = new XSSFWorkbook(new FileInputStream(excelFile));
        } catch (FileNotFoundException e1) {
	        e1.printStackTrace();
        } catch (IOException e1) {
	        e1.printStackTrace();
        }
		XSSFSheet sheet = wb.getSheetAt(0);
		int satirSayisi=sheet.getLastRowNum();
		int satir = 0;
		int kolon = 0;

		excelSessionObj.setLoadedValue(20);
		
		Row row1 = sheet.getRow(satir++);
		
		
		for (  Row row:sheet) {
			if(row.getRowNum()==0)continue;
			
			
			 if(row.getCell(0)!=null && row.getCell(1)!=null){
        			String usrAd=row.getCell(0).getStringCellValue();
        			
        			String usrSoyad=row.getCell(1).getStringCellValue();
        			String usrGsm="";
        			
        			if(usrAd.equals("") || usrAd.length()<2){
        				
        			}else{
        			
        			String userName=(usrAd.substring(0,1)).toLowerCase()+usrSoyad.toLowerCase();
        		
        			userName=userName.replaceAll("ö", "o");
        			userName=userName.replaceAll("ı", "i");
        			userName=userName.replaceAll("ü", "u");
        			userName=userName.replaceAll("ş", "s");
        			userName=userName.replaceAll("ğ", "g");
        			userName=userName.replaceAll("ç", "c");
        			
        			
        			userName=userName.replaceAll("Ö", "o");
        			userName=userName.replaceAll("İ", "i");
        			userName=userName.replaceAll("Ü", "u");
        			userName=userName.replaceAll("Ş", "s");
        			userName=userName.replaceAll("Ğ", "g");
        			userName=userName.replaceAll("Ç", "c");
        			
        			
        			if(row.getCell(2)!=null){
            			if(row.getCell(2).getCellType()==1)
            			 usrGsm=convertPhoneToLegal(row.getCell(2).getStringCellValue());
            			else if(row.getCell(2).getCellType()==0){
            				usrGsm=convertPhoneToLegal(new DecimalFormat("#").format(row.getCell(2).getNumericCellValue()));
            				
            			}
        			}
        			
        			String usrTel="";
        			if(row.getCell(3)!=null){
            			if(row.getCell(3).getCellType()==1)
            				usrTel=convertPhoneToLegal(row.getCell(3).getStringCellValue());
            			else if(row.getCell(3).getCellType()==0)
            				usrTel=convertPhoneToLegal(new DecimalFormat("#").format(row.getCell(3).getNumericCellValue()));
        			}
        			
        			String usrEmail="";
        			if(row.getCell(4)!=null){
            			if(row.getCell(4).getCellType()==1)
            				usrEmail=row.getCell(4).getStringCellValue();
        			}
        			
        			if(usrEmail.equals("")){
        				usrEmail=userName;
        			};
        			
        			String usrSsn="";
        			if(row.getCell(5)!=null){
            			if(row.getCell(5).getCellType()==1)
            				usrSsn=row.getCell(5).getStringCellValue();
            			else if(row.getCell(5).getCellType()==0)
            				usrSsn=new DecimalFormat("#").format(row.getCell(5).getNumericCellValue());
        			}
        			
        			Date dogumTarih=new Date();
        			if(row.getCell(6)!=null){
        				
        				if(row.getCell(6).getCellType()==1){
        					
        					String dogumTarihStr=row.getCell(6).getStringCellValue();
        					
        					dogumTarihStr=dogumTarihStr.replaceAll("[\\.]", "/");
        					dogumTarihStr=dogumTarihStr.replaceAll("-", "/");
        					
        					String[] dogumTarihiArr=dogumTarihStr.split("/");
        					
        					if(dogumTarihiArr.length>1){
        					   int gun=Integer.parseInt(dogumTarihiArr[0]);
        					   int ay=Integer.parseInt(dogumTarihiArr[1]);
        					   String yil=dogumTarihiArr[2];
        					   
        					   String gunStr=""+gun;
        					   if(gun<10)
        						   gunStr="0"+gun;
        						
        					   String ayStr=""+ay;
        					   if(ay<10)
        						   ayStr="0"+ay;
        						
        					   dogumTarihStr=gunStr+"/"+ayStr+"/"+yil;
        						
        					  
        					  dogumTarih=OhbeUtil.getThatDayFormatNotNull(dogumTarihStr, "dd/MM/yyyy");
        					}
        					
        				}else{
        				try {
	                        dogumTarih=row.getCell(6).getDateCellValue();
                        } catch (Exception e) {
                        	dogumTarih=new Date();
                        }
        				}
        			}
        			
        			Optional<User> memberInDb=userService.findUserByUserEmail(usrEmail);
        			if(!memberInDb.isPresent()){
        				User member=new User();
        				
        				
        				int randomPIN = (int)(Math.random()*9000)+1000;
            			String password = ""+randomPIN;
            			member.setPassword(password);
            			
            			
        				member.setUserType(UserTypes.USER_TYPE_MEMBER_INT);
        				
        				member.setUserAddress("");
        				if(dogumTarih==null)
            				dogumTarih=new Date();
        				
        				member.setUserBirthday(dogumTarih);
        				member.setCityId(0);
        				member.setStateId(0);
        				
        				member.setUserName(usrAd);
        				member.setUserSurname(usrSoyad);
            			//dogumTarih=dogumTarih.replaceAll("[\\.]", "/");
            			
            			
        				member.setFirmId(loginSession.getUser().getFirmId());
        				member.setUserSsn(usrSsn);
        				member.setUserGsm(usrGsm);
        				member.setUserPhone(usrTel);
        				member.setUserEmail(usrEmail);
            			
        				member.setUserComment("");
        				
        				
        				
        				userService.create(member);
        				
        			}
        			
        			
        			
        			int loadedValue=Math.round(((row.getRowNum()*100)/satirSayisi));
        			
        			
        			excelSessionObj.setLoadedValue(Integer.parseInt(""+loadedValue));
        			//session.removeAttribute(ResultStatuObj.EXCEL_STATU);
        			try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
        			session.setAttribute(ResultStatuObj.EXCEL_STATU,excelSessionObj);
        		}
			}
			
			
		}
		excelSessionObj.setLoadedValue(100);
		excelSessionObj.setResultStatu("finished");
		session.setAttribute(ResultStatuObj.EXCEL_STATU,excelSessionObj);
		result+="Yükleme Tamamlandı";
		//urunDao.urunListesiniKaydet(list);
	    return result;
		
    }
	
	
    private String convertPhoneToLegal(String phoneNumber){
    	String convertedPhoneNumber="";
    	
    	if(!phoneNumber.equals("")){
    		
    		phoneNumber=phoneNumber.replaceAll("[(]", "");
    		phoneNumber=phoneNumber.replaceAll("[)]", "");
    		phoneNumber=phoneNumber.replaceAll("[ ]", "");
    		phoneNumber=phoneNumber.replaceAll("[-]", "");
    		phoneNumber=phoneNumber.replaceAll("[\\.]", "");
    		phoneNumber=phoneNumber.trim();
    		if(phoneNumber.startsWith("0")){
    				if(phoneNumber.length()==11){
    				    String newPhone="("+phoneNumber.substring(1,4)+") ";
    				    newPhone+=phoneNumber.substring(4,7)+"-"+phoneNumber.substring(7,11);
    				    phoneNumber=newPhone;
    				}else{
    					phoneNumber="";
    				}
    		}else{
    			
    			if(phoneNumber.length()==10){
    				phoneNumber="0"+phoneNumber;
    				 String newPhone="("+phoneNumber.substring(1,4)+") ";
 				    newPhone+=phoneNumber.substring(4,7)+"-"+phoneNumber.substring(7,11);
 				    phoneNumber=newPhone;
    			}else{
    				
    				
    				phoneNumber="";
    			}
    			
    		}
    		
    	}
    	
    	convertedPhoneNumber=phoneNumber;
    	
    	
    	
    	return convertedPhoneNumber;
    }
	
    
    public synchronized XSSFWorkbook downloadExcelTemplate(int firmId) {
		List<User> users = userService.findAllByFirmIdAndUserType(firmId,UserTypes.USER_TYPE_MEMBER_INT);
		
		

		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
		XSSFSheet sheet = xssfWorkbook.createSheet("MEMBERS");
		
		Font font = xssfWorkbook.createFont();
	    font.setBoldweight(Font.BOLDWEIGHT_BOLD);
	    font.setColor(IndexedColors.WHITE.getIndex());
		CellStyle style = xssfWorkbook.createCellStyle();
	    style.setFont(font);
	    style.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
	
	    style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);

	    style.setBorderBottom(CellStyle.BORDER_THICK);
	    style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    style.setBorderLeft(CellStyle.BORDER_THIN);
	    style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
	    style.setBorderRight(CellStyle.BORDER_THIN);
	    style.setRightBorderColor(IndexedColors.WHITE.getIndex());
	    style.setBorderTop(CellStyle.BORDER_THIN);
	    style.setTopBorderColor(IndexedColors.WHITE.getIndex());
	    
		int satir=0;
		int kolon=0;
		Row row = sheet.createRow(satir++);
		row.createCell(kolon++).setCellValue(LangUtil.LANG_AD);row.getCell(kolon-1).setCellStyle(style);
		row.createCell(kolon++).setCellValue(LangUtil.LANG_SOYAD);row.getCell(kolon-1).setCellStyle(style);
		row.createCell(kolon++).setCellValue(LangUtil.LANG_CEP_TELEFON);row.getCell(kolon-1).setCellStyle(style);
		row.createCell(kolon++).setCellValue(LangUtil.LANG_IS_TELEFON);row.getCell(kolon-1).setCellStyle(style);
		row.createCell(kolon++).setCellValue(LangUtil.LANG_EMAIL);row.getCell(kolon-1).setCellStyle(style);
		row.createCell(kolon++).setCellValue(LangUtil.LANG_TC_KIMLIK);row.getCell(kolon-1).setCellStyle(style);
		row.createCell(kolon++).setCellValue(LangUtil.LANG_DOGUM_TARIH);row.getCell(kolon-1).setCellStyle(style);
		if(users!=null){
		for (User userTbl : users) {
			row = sheet.createRow(satir++);
			kolon=0;
			row.createCell(kolon++).setCellValue(userTbl.getUserName());
			row.createCell(kolon++).setCellValue(userTbl.getUserSurname());
			row.createCell(kolon++).setCellValue(userTbl.getUserGsm());
			row.createCell(kolon++).setCellValue(userTbl.getUserPhone());
			row.createCell(kolon++).setCellValue(userTbl.getUserEmail());
			row.createCell(kolon++).setCellValue(userTbl.getUserSsn());
			row.createCell(kolon++).setCellValue(OhbeUtil.getDateStrByFormat(userTbl.getUserBirthday(),loginSession.getPtGlobal().getPtScrDateFormat()));
		}
		}
		
		
		return xssfWorkbook;
	}
   
}
