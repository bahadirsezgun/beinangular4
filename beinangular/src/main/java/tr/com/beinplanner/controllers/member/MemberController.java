package tr.com.beinplanner.controllers.member;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.packetsale.business.IPacketSale;
import tr.com.beinplanner.packetsale.business.PacketSaleClassBusiness;
import tr.com.beinplanner.packetsale.business.PacketSalePersonalBusiness;
import tr.com.beinplanner.packetsale.dao.PacketSaleClass;
import tr.com.beinplanner.packetsale.dao.PacketSaleFactory;
import tr.com.beinplanner.packetsale.dao.PacketSalePersonal;
import tr.com.beinplanner.result.HmiResultObj;
import tr.com.beinplanner.schedule.dao.ScheduleFactory;
import tr.com.beinplanner.schedule.service.IScheduleService;
import tr.com.beinplanner.schedule.service.ScheduleClassService;
import tr.com.beinplanner.schedule.service.SchedulePersonalService;
import tr.com.beinplanner.user.dao.User;
import tr.com.beinplanner.user.service.UserService;
import tr.com.beinplanner.util.OhbeUtil;
import tr.com.beinplanner.util.ProgramTypes;
import tr.com.beinplanner.util.UserTypes;

@RestController
@RequestMapping("/bein/member")
public class MemberController {

	@Autowired
	UserService userService;
	
	@Autowired
	LoginSession loginSession;
	
	
	@Autowired
	PacketSalePersonalBusiness packetSalePersonalBusiness;
	
	@Autowired
	PacketSaleClassBusiness packetSaleClassBusiness;
	
	@Autowired
	SchedulePersonalService schedulePersonalService;
	
	@Autowired
	ScheduleClassService scheduleClassService;
	
	
	IPacketSale iPacketSale;
	
	IScheduleService iScheduleService;
	
	@PostMapping(value="/create")
	public  @ResponseBody HmiResultObj create(@RequestBody User user ) {
		user.setUserType(UserTypes.USER_TYPE_MEMBER_INT);
		user.setFirmId(loginSession.getUser().getFirmId());
		return userService.create(user);
	}
	
	@PostMapping(value="/delete/{userId}")
	public  @ResponseBody HmiResultObj delete(@PathVariable long userId ) {
		return userService.delete(userId);
	}
	
	@PostMapping(value="/findById/{userId}")
	public  @ResponseBody HmiResultObj doLogin(@PathVariable long userId ) {
		User user=userService.findUserById(userId);
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultObj(user);
		return hmiResultObj;
	}
	
	@PostMapping(value="/findAll")
	public  @ResponseBody HmiResultObj findAll( ) {
		List<User> user=userService.findAllByFirmId(loginSession.getUser().getFirmId());
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultObj(user);
		return hmiResultObj;
	}
	
	@PostMapping(value="/findUserForBookingBySale/{progId}/{progType}/{schId}")
	public  @ResponseBody HmiResultObj findUserForBookingBySale(@RequestBody User user,@PathVariable long progId,@PathVariable String progType,@PathVariable long schId) {
		List<User> users=userService.findByUsernameAndUsersurname(user.getUserName(), user.getUserSurname(), loginSession.getUser().getFirmId(),UserTypes.USER_TYPE_MEMBER_INT);
		List<User> availableUsers=new ArrayList<User>();
		
		System.out.println("SCHID "+schId);
		
		if(progType.equals(ProgramTypes.PROGRAM_PERSONAL_STR)) {
			iPacketSale=packetSalePersonalBusiness;
			iScheduleService=schedulePersonalService;
		}else {
			iPacketSale=packetSaleClassBusiness;
			iScheduleService=scheduleClassService;
		}
		
		List<ScheduleFactory> scheduleFactories=iScheduleService.findScheduleUsersPlanBySchId(schId);
		
		
		users.forEach(u->{
			
			for (ScheduleFactory sf : scheduleFactories) {
			   if(u.getUserId()==sf.getUser().getUserId()) {
					u.setSaleId(sf.getSaleId());
					break;
				}
			};
			
			if(u.getSaleId()==0) {
			 List<PacketSaleFactory> packetSalefactories=iPacketSale.findFreeSalesForUserByProgId(u.getUserId(), progId);
			 if(packetSalefactories.size()>0) {
				 if(iPacketSale instanceof PacketSalePersonalBusiness)
				     u.setSaleId(((PacketSalePersonal)packetSalefactories.get(0)).getSaleId());
				 else
					 u.setSaleId(((PacketSaleClass)packetSalefactories.get(0)).getSaleId());
			 }
			}
			 
			 
			 
			 availableUsers.add(u);
		});
		
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultObj(availableUsers);
		return hmiResultObj;
	}
	
	
	@PostMapping(value="/findByUsernameAndUsersurname")
	public  @ResponseBody HmiResultObj findByUsernameAndUsersurname(@RequestBody User user ) {
		List<User> users=userService.findByUsernameAndUsersurname(user.getUserName(), user.getUserSurname(), loginSession.getUser().getFirmId(),UserTypes.USER_TYPE_MEMBER_INT);
		
		users.forEach(u->{
			if(u.getProfileUrl()==null) {
				if(u.getUserGender()==UserTypes.GENDER_MALE) {
					u.setProfileUrl("profilem.png");
				}else {
					u.setProfileUrl("profile.png");
				}
			}
		});
		
		HmiResultObj hmiResultObj=new HmiResultObj();
		hmiResultObj.setResultObj(users);
		return hmiResultObj;
	}
	
	
	@RequestMapping(value = "/get/profile/{userId}/{random}", method=RequestMethod.GET)
	public ResponseEntity<byte[]> showImageOnId(@PathVariable("userId") long userId,@PathVariable("random") int random ) throws IOException {
		User member=userService.findUserById(userId);	
		String fileName=OhbeUtil.ROOT_FIRM_FOLDER+"/"+member.getProfileUrl();
		byte[] media = Files.readAllBytes(new File(fileName).toPath());
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<byte[]>  responseEntity = new ResponseEntity<byte[]>(media, headers, HttpStatus.OK);
		return responseEntity;
	}
	
}
