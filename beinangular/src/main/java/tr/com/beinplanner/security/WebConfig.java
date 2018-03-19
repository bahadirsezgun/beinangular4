package tr.com.beinplanner.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	/*
	 @Override
	    public void addViewControllers(ViewControllerRegistry registry) {
	        registry.addViewController("/login");//.setViewName("login");
	        registry.addViewController("/");//.setViewName("login");
	        registry.addViewController("/beincloud");//.setViewName("beincloud");
	        registry.addViewController("/error");//.setViewName("error");
	        registry.addViewController("/logout");//.setViewName("logout");
	        
	    }
*/
	 @Bean
	 public InternalResourceViewResolver viewResolver() {
	  InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	  resolver.setPrefix("/");
	  resolver.setSuffix(".html");
	  return resolver;
	 }
	
	 /*
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public String login(){
		return "index.html";
	}
	
	@RequestMapping(value={ "/beincloud"}, method = RequestMethod.GET)
	public String beincloud(){
		return "/bein/index.html";
	}
	@RequestMapping(value={ "/error"}, method = RequestMethod.GET)
	public String error(){
		return "/lock.html";
	}
	@RequestMapping(value={ "/logout"}, method = RequestMethod.GET)
	public String logout(){
		return "/lock.html";
	}
	*/
	 
	 @Override
	    public void configureDefaultServletHandling(
	            DefaultServletHandlerConfigurer configurer) {
	        configurer.enable();
	    }
}
