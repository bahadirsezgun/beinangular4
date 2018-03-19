package tr.com.beinplanner.controllers.packetsale;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.beinplanner.login.session.LoginSession;
import tr.com.beinplanner.packetsale.dao.PacketSaleFactory;
import tr.com.beinplanner.packetsale.service.PacketSaleService;
import tr.com.beinplanner.result.HmiResultObj;

@RestController
@RequestMapping("/bein/packetsale")
public class PacketSaleController {

	
	@Autowired
	PacketSaleService packetSaleService;
	
	@Autowired
	LoginSession loginSession;
	
	@RequestMapping(value="/leftPayments", method = RequestMethod.POST) 
	public @ResponseBody List<PacketSaleFactory>	leftPayments(){
		return packetSaleService.findLeftPaymentsInChain(loginSession.getUser().getFirmId());
	}
	
	@RequestMapping(value="/findUserBoughtPackets/{userId}", method = RequestMethod.POST) 
	public @ResponseBody List<PacketSaleFactory> findUserBoughtPackets(@PathVariable("userId") long userId ){
		return packetSaleService.findUserBoughtPackets(userId);
	}
	
	@RequestMapping(value="/findUserBoughtPacketsForCalendar/{userId}", method = RequestMethod.POST) 
	public @ResponseBody List<PacketSaleFactory> findUserBoughtPacketsForCalendar(@PathVariable("userId") long userId ){
		return packetSaleService.findUserBoughtPacketsForCalendar(userId);
	}
	
	@RequestMapping(value="/sale", method = RequestMethod.POST) 
	public @ResponseBody HmiResultObj sale(@RequestBody PacketSaleFactory packetSaleFactory ){
		return packetSaleService.sale(packetSaleFactory);
	}
	
	@RequestMapping(value="/deletePacketSale", method = RequestMethod.POST) 
	public @ResponseBody HmiResultObj deletePacketSale(@RequestBody PacketSaleFactory packetSaleFactory ){
		return packetSaleService.deletePacketSale(packetSaleFactory);
	}
}
