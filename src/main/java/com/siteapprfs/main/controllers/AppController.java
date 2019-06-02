package com.siteapprfs.main.controllers;

import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.siteapprfs.main.services.AppServices;
import com.siteapprfs.main.services.Servs;

@RestController
public class AppController {
    private AppServices appService;
    
	public AppController() {
		appService = new AppServices(null);
	}
	
	@RequestMapping("/")
	public String list() {
		return " API RESTFULL FEITA POR RAFAEL F.S ...";
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/servicos")
	public List<Servs> index() {
		return appService.readService();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/servicos/{id}")
	public List<Servs> listServ(@Valid @PathVariable Integer id) {
		return appService.readService(id);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/servicos/search/{nome}")
	public List<Servs> listServ(@Valid @PathVariable String nome) {
		return appService.readService(nome);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value = "/servicos")
	public void createServ(@Valid @RequestBody Servs srv) {
		appService.createService(srv);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value = "/servicos/{id}")
	public void deleteServ(@Valid @PathVariable Integer id) {
		appService.deleteService(id);
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/servicos")
	public void updateServ(@Valid @RequestBody Servs srv) {
		appService.updateService(srv);
	}
	
}
