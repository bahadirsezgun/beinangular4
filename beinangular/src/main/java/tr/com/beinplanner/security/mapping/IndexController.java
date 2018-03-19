package tr.com.beinplanner.security.mapping;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

//@RestController
public class IndexController implements ErrorController{

    private static final String PATH = "/error";

    
    @RequestMapping(value = PATH)
	public ModelAndView error(){
		ModelAndView model = new ModelAndView();
		model.setViewName("lock");
		return model;
	}
	/*
    
    @RequestMapping(value = PATH)
    public String error() {
        
    	
    	
    	
    	
    	
    	
    	
    	
    	return  "The Company May Not Approved Yet ...";
    }*/

    @Override
    public String getErrorPath() {
        return PATH;
    }
    
    
}
