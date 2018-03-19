package tr.com.beinplanner.definition.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.definition.dao.DefBonus;
import tr.com.beinplanner.definition.dao.DefCalendarTimes;
import tr.com.beinplanner.definition.dao.DefFirm;
import tr.com.beinplanner.definition.repository.DefBonusRepository;
import tr.com.beinplanner.definition.repository.DefCalendarTimesRepository;
import tr.com.beinplanner.definition.repository.DefFirmRepository;
import tr.com.beinplanner.program.dao.ProgramFactory;
import tr.com.beinplanner.program.service.ProgramService;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.util.BonusTypes;
import tr.com.beinplanner.util.ResultStatuObj;

@Service
@Qualifier("definitionService")
public class DefinitionService {

	@Autowired
	DefBonusRepository defBonusRepository;
	
	@Autowired
	DefCalendarTimesRepository defCalendarTimesRepository;
	
	@Autowired
	DefFirmRepository defFirmRepository;
	
	
	@Autowired
	ProgramService programService;
	
	
	public DefFirm findFirm(int firmId){
		return defFirmRepository.findOne(firmId);
	}
	
	public DefFirm findFirmByEMail(String email){
		return defFirmRepository.findByFirmEmail(email);
	}
		
	public DefFirm createFirm(DefFirm defFirm){
		return defFirmRepository.save(defFirm);
	}
	
	
	
	
	
	public List<DefBonus> findByUserIdAndBonusTypeAndBonusIsType(long userId,int bonusType,int bonusIsType){
		List<DefBonus> defBonuses=defBonusRepository.findByUserIdAndBonusTypeAndBonusIsType(userId, bonusType, bonusIsType);
		defBonuses.forEach(defb->{
			ProgramFactory programFactory= null;
			if(defb.getBonusType()==BonusTypes.BONUS_TYPE_PERSONAL) {
			   programFactory= programService.findProgramPersonalById(defb.getBonusProgId());
			}else {
				programFactory= programService.findProgramClassById(defb.getBonusProgId());
			}
			defb.setProgramFactory(programFactory);
		});
		return defBonuses;
	}
	
	public HmiResultObj createDefBonus(DefBonus defBonus){
		
		List<DefBonus> defBonuses=findByUserIdAndBonusTypeAndBonusIsType(defBonus.getUserId(), defBonus.getBonusType(), defBonus.getBonusIsType());
		
		boolean sameDefinitionOfBonus=false;
		
		
		for (DefBonus defb : defBonuses) {
			if(defb.getBonusProgId()==defBonus.getBonusProgId() && defb.getBonusCount()==defBonus.getBonusCount()) {
				sameDefinitionOfBonus=true;
				break;
			}
		}
		
		HmiResultObj hmiResultObj=new HmiResultObj();
		
		if(sameDefinitionOfBonus) {
			hmiResultObj.setResultMessage("sameDefinitionOfBonus");
			hmiResultObj.setResultObj(defBonus);
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_FAIL_STR);
		}else {
			defBonus=defBonusRepository.save(defBonus);
			hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
			hmiResultObj.setResultObj(defBonus);
			hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		}
		
		
		
		
		return hmiResultObj;
	}
	
	public HmiResultObj deleteDefBonus(DefBonus defBonus){
		defBonusRepository.delete(defBonus);
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultMessage(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		hmiResultObj.setResultStatu(ResultStatuObj.RESULT_STATU_SUCCESS_STR);
		return hmiResultObj;
	}
	
	
	
	public DefCalendarTimes findCalendarTimes(int firmId) {
		
		DefCalendarTimes defCalendarTimes=null;
		try {
			defCalendarTimes = defCalendarTimesRepository.findDCTByFirmId(firmId);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return defCalendarTimes;
	}

	public DefCalendarTimes createDefCalendarTimes(DefCalendarTimes defCalendarTimes) {
		DefCalendarTimes defct=defCalendarTimesRepository.findDCTByFirmId(defCalendarTimes.getFirmId());
		if(defct!=null)
		  defCalendarTimesRepository.delete(defct);		
		
		return defCalendarTimesRepository.save(defCalendarTimes);
	}
	
}
