package tr.com.beinplanner.security.mapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MappingController {

	
	@RequestMapping(value="/login")
	public  ModelAndView login()  {
		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		return model;
	} 
	
	@RequestMapping(value="/")
	public ModelAndView loginRoot()  {
		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		return model;
	}
	
	@RequestMapping(value="/appplans")
	public ModelAndView appplan()  {
		ModelAndView model = new ModelAndView();
		model.setViewName("appplan");
		return model;
	}
	
	@RequestMapping(value="/register")
	public  ModelAndView register()  {
		ModelAndView model = new ModelAndView();
		model.setViewName("firmRegister");
		
		return model;
	} 
	
	
	@RequestMapping(value="/firmCreatedBeforeException")
	public  ModelAndView firmCreatedBeforeException()  {
		ModelAndView model = new ModelAndView();
		model.setViewName("stripe/firmcreatedbeforeException");
		return model;
	} 
	
	@RequestMapping(value={ "/beincloud"}, method = RequestMethod.GET)
	public  ModelAndView beincloud(){
		ModelAndView model = new ModelAndView();
		model.setViewName("bein/index");
		return model;
	}
	
	@RequestMapping(value={ "/error"}, method = RequestMethod.GET)
	public ModelAndView error(){
		ModelAndView model = new ModelAndView();
		model.setViewName("lock");
		return model;
	}
	
	@RequestMapping(value= "/logout")
	public ModelAndView logout(){
		ModelAndView model = new ModelAndView();
		model.setViewName("lockit");
		return model;
	}
	
	@RequestMapping(value= "/lock")
	public ModelAndView lock(){
		ModelAndView model = new ModelAndView();
		model.setViewName("lockit");
		return model;
	}
}
