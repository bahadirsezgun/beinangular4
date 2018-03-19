package tr.com.beinplanner.schedule.facade;

import tr.com.beinplanner.packetsale.dao.PacketSaleMembership;
import tr.com.beinplanner.program.dao.ProgramMembership;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.schedule.businessEntity.ScheduleMembershipFreezeObj;
import tr.com.beinplanner.schedule.dao.ScheduleMembershipPlan;
import tr.com.beinplanner.schedule.dao.ScheduleMembershipTimePlan;

public interface ScheduleMembershipFacadeService {

	
		public abstract HmiResultObj canScheduleCreate(ScheduleMembershipPlan scheduleFactory);


		public abstract HmiResultObj canScheduleFreeze(ScheduleMembershipFreezeObj smp,ScheduleMembershipPlan smpInDb,ProgramMembership pmf);

		public abstract HmiResultObj canScheduleUnFreeze(ScheduleMembershipPlan smp,ScheduleMembershipTimePlan scheduleMembershipTimePlan,ProgramMembership pmf);

		public abstract HmiResultObj canScheduleDelete(PacketSaleMembership packetSaleFactory);

		
	
}
