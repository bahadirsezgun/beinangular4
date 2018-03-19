package tr.com.beinplanner.controllers.programs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.program.dao.ProgramClass;
import tr.com.beinplanner.program.dao.ProgramFactory;
import tr.com.beinplanner.program.dao.ProgramMembership;
import tr.com.beinplanner.program.dao.ProgramPersonal;
import tr.com.beinplanner.program.service.ProgramService;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.util.ProgramTypes;

@RestController
@RequestMapping("/bein/program")
public class ProgramController {

	@Autowired
	ProgramService programService;
	
	@Autowired
	LoginSession loginSession;
	
	
	
	
	@PostMapping(value="/findPrograms/{progType}")
	public  @ResponseBody HmiResultObj findPrograms(@PathVariable String progType ) {
		List<ProgramFactory> programFactories=new ArrayList<ProgramFactory>();
		if(progType.equals(ProgramTypes.PROGRAM_PERSONAL_STR)) {
			List<ProgramPersonal> programPersonal=programService.findAllProgramPersonal(loginSession.getUser().getFirmId());
			programFactories.addAll(programPersonal);
		}else if(progType.equals(ProgramTypes.PROGRAM_CLASS_STR)) {
			List<ProgramClass> programClass=programService.findAllProgramClass(loginSession.getUser().getFirmId());
			programFactories.addAll(programClass);
		}else if(progType.equals(ProgramTypes.PROGRAM_MEMBERSHIP_STR)) {
			List<ProgramMembership> programMemberhip=programService.findAllProgramMembership(loginSession.getUser().getFirmId());
			programFactories.addAll(programMemberhip);
		}
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultObj(programFactories);
		return hmiResultObj;
	}
	
	@PostMapping(value="/findProgramById/{progType}/{progId}")
	public  @ResponseBody HmiResultObj findProgramById(@PathVariable String progType,@PathVariable long progId ) {
		ProgramFactory programFactory=null;
		
		if(progType.equals(ProgramTypes.PROGRAM_PERSONAL_STR)) {
			programFactory=programService.findProgramPersonalById(progId);
		}else if(progType.equals(ProgramTypes.PROGRAM_CLASS_STR)) {
			programFactory=programService.findProgramClassById(progId);
		}else if(progType.equals(ProgramTypes.PROGRAM_MEMBERSHIP_STR)) {
			programFactory=programService.findProgramMembershipById(progId);
		}
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultObj(programFactory);
		return hmiResultObj;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*PROGRAM PERSONAL*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@PostMapping(value="/findPersonalPrograms")
	public  @ResponseBody HmiResultObj findPersonalPrograms( ) {
		List<ProgramPersonal> programFactories=programService.findAllProgramPersonal(loginSession.getUser().getFirmId());
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultObj(programFactories);
		return hmiResultObj;
	}
	
	@PostMapping(value="/findPersonalProgramById/{progId}")
	public  @ResponseBody HmiResultObj findPersonalProgramById(@PathVariable("progId") long progId ) {
		ProgramPersonal programFactories=programService.findProgramPersonalById(progId);
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultObj(programFactories);
		return hmiResultObj;
	}
	
	@PostMapping(value="/createPersonalProgram")
	public  @ResponseBody HmiResultObj createPersonalProgram(@RequestBody ProgramPersonal programPersonal ) {
		programPersonal.setFirmId(loginSession.getUser().getFirmId());
		return programService.createProgramPersonal(programPersonal);
	}
	
	@PostMapping(value="/deletePersonalProgram/{progId}")
	public  @ResponseBody HmiResultObj deletePersonalProgram(@PathVariable("progId") long progId) {
		return programService.deletePersonalProgram(progId);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*PROGRAM CLASS*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	@PostMapping(value="/findClassPrograms")
	public  @ResponseBody HmiResultObj findClassPrograms( ) {
		List<ProgramClass> programFactories=programService.findAllProgramClass(loginSession.getUser().getFirmId());
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultObj(programFactories);
		return hmiResultObj;
	}
	
	@PostMapping(value="/findClassProgramById/{progId}")
	public  @ResponseBody HmiResultObj findClassProgramById(@PathVariable("progId") long progId ) {
		ProgramClass programFactories=programService.findProgramClassById(progId);
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultObj(programFactories);
		return hmiResultObj;
	}
	
	@PostMapping(value="/createClassProgram")
	public  @ResponseBody HmiResultObj createClassProgram(@RequestBody ProgramClass programClass ) {
		programClass.setFirmId(loginSession.getUser().getFirmId());
		return programService.createProgramClass(programClass);
	}
	
	@PostMapping(value="/deleteClassProgram/{progId}")
	public  @ResponseBody HmiResultObj deleteClassProgram(@PathVariable("progId") long progId) {
		return programService.deleteClassProgram(progId);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*PROGRAM MEMBERSHIP*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	@PostMapping(value="/findMembershipPrograms")
	public  @ResponseBody HmiResultObj findMembershipPrograms( ) {
	List<ProgramMembership> programFactories=programService.findAllProgramMembership(loginSession.getUser().getFirmId());
	HmiResultObj hmiResultObj=new HmiResultObj();
	hmiResultObj.setResultObj(programFactories);
	return hmiResultObj;
	}
	
	@PostMapping(value="/findMembershipProgramById/{progId}")
	public  @ResponseBody HmiResultObj findMembershipProgramById(@PathVariable("progId") long progId ) {
	ProgramMembership programFactories=programService.findProgramMembershipById(progId);
	HmiResultObj hmiResultObj=new HmiResultObj();
	hmiResultObj.setResultObj(programFactories);
	return hmiResultObj;
	}
	
	@PostMapping(value="/createMembershipProgram")
	public  @ResponseBody HmiResultObj createMembershipProgram(@RequestBody ProgramMembership programMembership ) {
		programMembership.setFirmId(loginSession.getUser().getFirmId());
		HmiResultObj hmiResultObj=programService.createProgramMembership(programMembership);
		ProgramMembership pm=(ProgramMembership)hmiResultObj.getResultObj();
		
		pm.getProgramMembershipDetails().forEach(pmd->{
			pmd.setProgId(pm.getProgId());
		});
		
		programService.createProgramMembershipDetails(pm.getProgramMembershipDetails(), pm.getProgId());
		
	  return hmiResultObj;
	}
	
	@PostMapping(value="/deleteMembershipProgram/{progId}")
	public  @ResponseBody HmiResultObj deleteMembershipProgram(@PathVariable("progId") long progId) {
	return programService.deleteMembershipProgram(progId);
	}
}
