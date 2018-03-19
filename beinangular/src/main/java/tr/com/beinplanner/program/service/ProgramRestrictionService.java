package tr.com.beinplanner.program.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tr.com.beinplanner.program.dao.ProgramClass;
import tr.com.beinplanner.program.dao.ProgramMembership;
import tr.com.beinplanner.program.dao.ProgramPersonal;
import tr.com.beinplanner.program.repository.ProgramClassRepository;
import tr.com.beinplanner.program.repository.ProgramMembershipRepository;
import tr.com.beinplanner.program.repository.ProgramPersonalRepository;
import tr.com.beinplanner.settings.dao.PacketRestriction;
import tr.com.beinplanner.util.RestrictionUtil;

@Service
@Qualifier("programRestrictionService")
public class ProgramRestrictionService {

	@Autowired
	ProgramPersonalRepository programPersonalRepository;
	
	@Autowired
	ProgramClassRepository programClassRepository;
	
	@Autowired
	ProgramMembershipRepository programMembershipRepository;
	
	
	
	public PacketRestriction findPacketRestriction(int firmId) {
		PacketRestriction packetRestriction=new PacketRestriction();
		
		List<ProgramPersonal> programPersonals= programPersonalRepository.findByFirmId(firmId);
		List<ProgramClass> programClasses= programClassRepository.findByFirmId(firmId);
		List<ProgramMembership> programMemberships= programMembershipRepository.findByFirmId(firmId);
		int restrictionCount=0;
		
		if(programPersonals.isEmpty()) 
			packetRestriction.setPersonalRestriction(RestrictionUtil.RESTIRICTION_FLAG_NO);
		else {
			packetRestriction.setPersonalRestriction(RestrictionUtil.RESTIRICTION_FLAG_YES);
			restrictionCount++;
		}	
		if(programClasses.isEmpty()) 
			packetRestriction.setGroupRestriction(RestrictionUtil.RESTIRICTION_FLAG_NO);
		else {
			packetRestriction.setGroupRestriction(RestrictionUtil.RESTIRICTION_FLAG_YES);
			restrictionCount++;
		}
		if(programMemberships.isEmpty()) 
			packetRestriction.setMembershipRestriction(RestrictionUtil.RESTIRICTION_FLAG_NO);
		else {
			packetRestriction.setMembershipRestriction(RestrictionUtil.RESTIRICTION_FLAG_YES);
			restrictionCount++;
		}
		
		
		if(restrictionCount>1) {
			packetRestriction.setMultiRestriction(RestrictionUtil.RESTIRICTION_FLAG_YES);
		}else {
			packetRestriction.setMultiRestriction(RestrictionUtil.RESTIRICTION_FLAG_NO);
		}
		
		
		return packetRestriction;
	}
	
}
