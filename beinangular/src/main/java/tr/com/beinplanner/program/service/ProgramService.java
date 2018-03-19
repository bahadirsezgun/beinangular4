package tr.com.beinplanner.program.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.program.dao.ProgramClass;
import tr.com.beinplanner.program.dao.ProgramMembership;
import tr.com.beinplanner.program.dao.ProgramMembershipDetail;
import tr.com.beinplanner.program.dao.ProgramPersonal;
import tr.com.beinplanner.program.repository.ProgramClassRepository;
import tr.com.beinplanner.program.repository.ProgramMembershipDetailRepository;
import tr.com.beinplanner.program.repository.ProgramMembershipRepository;
import tr.com.beinplanner.program.repository.ProgramPersonalRepository;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.util.ResultStatuObj;
/**
 * 
 * @author Bahadır Sezgun
 * @comment This service all about Programs. 
 *    There are three type of program 
 *    <br> 1- Program Personal - This program is used to booking personals as Single Class, Couple Class etc..
 *    <br> 2- Program Class - This program is used to booking group classes as Spining, Yoga etc.. 
 *    <br> 3- Program Membership - This program is used to booking membership classes as Montly, Daily, Yearly unlimeted memberships etc.. 
 *    
 */
@Service
@Qualifier("programService")
public class ProgramService {

	@Autowired
	ProgramClassRepository programClassRepository;
	
	@Autowired
	ProgramPersonalRepository programPersonalRepository;

	@Autowired
	ProgramMembershipRepository programMembershipRepository;

	@Autowired
	ProgramMembershipDetailRepository programMembershipDetailRepository;

	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*PROGRAM PERSONAL*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * @author Bahadir Sezgun
	 * @param progId
	 * @return {@link ProgramPersonal}
	 * @comment Find personal program by primary key (progId)
	 */
	public ProgramPersonal findProgramPersonalById(long progId) {
		return programPersonalRepository.findOne(progId);
	}
	
	
	
	/**
	 * @author Bahadir Sezgun
	 * @param progId
	 * @return {@link ProgramPersonal}
	 * @comment Find personal program to member that participate to the class by time plan id (schtId).<br>
	 *          This method is called from Bonus Calculation processes.
	 */
	public ProgramPersonal findProgramPersonalByTimePlan(long schtId) {
		return programPersonalRepository.findProgramPersonalByTimePlan(schtId);
	}
	
	/**
	 * @author Bahadir Sezgun
	 * @param firmId
	 * @return {@link List<ProgramPersonal>}
	 *         Also related firm information bind to ProgramPersonal. 
	 *         <br> There is a join on ProgramPersonal Entity 
	 *         JoinColumn(name="FIRM_ID",foreignKey=@ForeignKey(foreignKeyDefinition="PP_TO_DF_FK"),insertable=false,updatable=false)
	 * @comment Find all personal programs by firm (firmId)
	 */
	public List<ProgramPersonal> findAllProgramPersonal(int firmId) {
		return programPersonalRepository.findByFirmId(firmId);
	}
	
	/**
	 * @author Bahadir Sezgun
	 * @param ProgramPersonal
	 * @return {@link HmiResultObj}
	 * 		   Also relate firm information bind to ProgramPersonal. 
	 *         <br> There is a join on ProgramPersonal Entity 
	 *         JoinColumn(name="FIRM_ID",foreignKey=@ForeignKey(foreignKeyDefinition="PP_TO_DF_FK"),insertable=false,updatable=false)
	 * @comment Create personal program. This program is going to be use booking of member.
	 */
	public HmiResultObj createProgramPersonal(ProgramPersonal programPersonal) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		programPersonalRepository.save(programPersonal);
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultObj(programPersonal);
		hmiResultObj.setResultStatu(""+ResultStatuObj.RESULT_STATU_SUCCESS);
		return hmiResultObj;
	}
	
	/**
	 * @author Bahadir Sezgun
	 * @param ProgramPersonal
	 * @return {@link HmiResultObj}
	 * @comment Delete personal program. If program has sale than you can not delete it. This is control at database by PSP_TO_PP_FK foreign Key constraint.
	 * You can check packet_sale_personal table Foreign Key PSP_TO_PP_FK.
	 */
	public HmiResultObj deletePersonalProgram(long progId) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		try {
			programPersonalRepository.delete(progId);
			hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
			hmiResultObj.setResultStatu(""+ResultStatuObj.RESULT_STATU_SUCCESS);
		} catch (Exception e) {
			hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_FAIL_STR);
			hmiResultObj.setResultStatu(""+ResultStatuObj.RESULT_STATU_FAIL);
		}
		return hmiResultObj;
	}
	
	
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*PROGRAM CLASS*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * @author Bahadir Sezgun
	 * @param progId
	 * @return {@link ProgramClass}
	 * @comment Find personal program by primary key (progId)
	 */
	public ProgramClass findProgramClassById(long progId) {
		return programClassRepository.findOne(progId);
	}
	
	
	
	/**
	 * @author Bahadir Sezgun
	 * @param progId
	 * @return {@link ProgramPersonal}
	 * @comment Find class program to member that participate to the class by time plan id (schtId).<br>
	 *          This method is called from Bonus Calculation processes.
	 */
	public ProgramClass findProgramClassByTimePlan(long schtId) {
		return programClassRepository.findProgramClassByTimePlan(schtId);
	}
	
	/**
	 * @author Bahadir Sezgun
	 * @param firmId
	 * @return {@link List<ProgramClass>}
	 *         Also related firm information bind to ProgramClass. 
	 *         <br> There is a join on ProgramClass Entity 
	 *         JoinColumn(name="FIRM_ID",foreignKey=@ForeignKey(foreignKeyDefinition="PP_TO_DF_FK"),insertable=false,updatable=false)
	 * @comment Find all Class programs by firm (firmId)
	 */
	public List<ProgramClass> findAllProgramClass(int firmId) {
		return programClassRepository.findByFirmId(firmId);
	}
	
	/**
	 * @author Bahadir Sezgun
	 * @param ProgramClass
	 * @return {@link HmiResultObj}
	 * 		   Also relate firm information bind to ProgramClass. 
	 *         <br> There is a join on ProgramClass Entity 
	 *         JoinColumn(name="FIRM_ID",foreignKey=@ForeignKey(foreignKeyDefinition="PP_TO_DF_FK"),insertable=false,updatable=false)
	 * @comment Create Class program. This program is going to be use booking of member.
	 */
	public HmiResultObj createProgramClass(ProgramClass programClass) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		programClassRepository.save(programClass);
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultObj(programClass);
		hmiResultObj.setResultStatu(""+ResultStatuObj.RESULT_STATU_SUCCESS);
		return hmiResultObj;
	}
	
	/**
	 * @author Bahadir Sezgun
	 * @param ProgramClass
	 * @return {@link HmiResultObj}
	 * @comment Delete Class program. If program has sale than you can not delete it. This is control at database by PSP_TO_PP_FK foreign Key constraint.
	 * You can check packet_sale_Class table Foreign Key PSP_TO_PP_FK.
	 */
	public HmiResultObj deleteClassProgram(long progId) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		try {
			programClassRepository.delete(progId);
			hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
			hmiResultObj.setResultStatu(""+ResultStatuObj.RESULT_STATU_SUCCESS);
		} catch (Exception e) {
			hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_FAIL_STR);
			hmiResultObj.setResultStatu(""+ResultStatuObj.RESULT_STATU_FAIL);
		}
		return hmiResultObj;
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*PROGRAM MEMBERSHIP*/
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * @author Bahadir Sezgun
	 * @param progId
	 * @return {@link ProgramMembership}
	 * @comment Find personal program by primary key (progId)
	 */
	public ProgramMembership findProgramMembershipById(long progId) {
		return programMembershipRepository.findOne(progId);
	}
	/**
	 * @author Bahadir Sezgun
	 * @param firmId
	 * @return {@link List<ProgramMembership>}
	 *         Also related firm information bind to ProgramMembership. 
	 *         <br> There is a join on ProgramMembership Entity 
	 *         JoinColumn(name="FIRM_ID",foreignKey=@ForeignKey(foreignKeyDefinition="PP_TO_DF_FK"),insertable=false,updatable=false)
	 * @comment Find all Membership programs by firm (firmId)
	 */
	public List<ProgramMembership> findAllProgramMembership(int firmId) {
		return programMembershipRepository.findByFirmId(firmId);
	}
	
	/**
	 * @author Bahadir Sezgun
	 * @param ProgramMembership
	 * @return {@link HmiResultObj}
	 * 		   Also relate firm information bind to ProgramMembership. 
	 *         <br> There is a join on ProgramMembership Entity 
	 *         JoinColumn(name="FIRM_ID",foreignKey=@ForeignKey(foreignKeyDefinition="PP_TO_DF_FK"),insertable=false,updatable=false)
	 * @comment Create Membership program. This program is going to be use booking of member.
	 */
	public HmiResultObj createProgramMembership(ProgramMembership programMembership) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		programMembershipRepository.save(programMembership);
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultObj(programMembership);
		hmiResultObj.setResultStatu(""+ResultStatuObj.RESULT_STATU_SUCCESS);
		return hmiResultObj;
	}
	
	/**
	 * @author Bahadir Sezgun
	 * @param List<ProgramMembershipDetails>
	 * @return {@link List<ProgramMembershipDetail>}
	 * 		   Also relate firm information bind to ProgramMembership. 
	 *         <br> There is a join on ProgramMembership Entity 
	 *         JoinColumn(name="FIRM_ID",foreignKey=@ForeignKey(foreignKeyDefinition="PP_TO_DF_FK"),insertable=false,updatable=false)
	 * @comment Create Membership program. This program is going to be use booking of member.
	 */
	public List<ProgramMembershipDetail> createProgramMembershipDetails(List<ProgramMembershipDetail> programMembershipDetails,long progId) {
		try {
			List<ProgramMembershipDetail> pmds=programMembershipDetailRepository.findByProgId(progId);
			pmds.forEach(pd->{
				programMembershipDetailRepository.delete(pd);
			});
		} catch (Exception e) {
		
		}
		
		programMembershipDetails.forEach(pmd->{
			programMembershipDetailRepository.save(pmd);
		});
		
		return programMembershipDetails;
	}
	
	/**
	 * @author Bahadir Sezgun
	 * @param ProgramMembership
	 * @return {@link HmiResultObj}
	 * @comment Delete Membership program. If program has sale than you can not delete it. This is control at database by PSP_TO_PP_FK foreign Key constraint.
	 * You can check packet_sale_Membership table Foreign Key PSP_TO_PP_FK.
	 */
	public HmiResultObj deleteMembershipProgram(long progId) {
		HmiResultObj hmiResultObj=new HmiResultObj();
		try {
			programMembershipRepository.delete(progId);
			hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
			hmiResultObj.setResultStatu(""+ResultStatuObj.RESULT_STATU_SUCCESS);
		} catch (Exception e) {
			hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_FAIL_STR);
			hmiResultObj.setResultStatu(""+ResultStatuObj.RESULT_STATU_FAIL);
		}
		return hmiResultObj;
	}
	
	
	
	
	
}
