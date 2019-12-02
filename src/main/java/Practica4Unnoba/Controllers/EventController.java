package Practica4Unnoba.Controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import Practica4Unnoba.Entities.Event;
import Practica4Unnoba.Entities.Registration;
import Practica4Unnoba.Entities.Usuario;
import Practica4Unnoba.Services.EventService;
import Practica4Unnoba.Services.RegistrationService;
import Practica4Unnoba.Services.UserService;

@Controller
public class EventController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private RegistrationService registrationService;
	
	@GetMapping("/myevents")
	public String findAllMyEvents(Model model) {
		/*
		 * Obtengo el usuario logueado, para recuperar los eventos creados por él.
		 */
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUser = (UserDetails) principal;
		String username = loggedUser.getUsername();
		Usuario user = userService.findUserByUsername(username);
		
		List<Event> events = eventService.findAllEventByOwnerID(user.getId());
		model.addAttribute("events", events);
		
		return "my-events";
	}
	
	@GetMapping("/addevent")
	public String showAddEventForm(Event event) {
		return "add-event";
	}
	
	
	@GetMapping("/events/")
	public String findAllEvents(Model model){
		List<Event> events = eventService.findAllEvents();
		
		model.addAttribute("events", events);
		
		return "events";
	}
	
	@GetMapping("/events/info/{id}")
	public String getEvent(@PathVariable Long id, Model model) {
		Event event = eventService.getEvent(id);
		List<Registration> registrations = registrationService.findAllRegistrationsByEventID(id);
		int numberOfRegistrations = registrationService.quantityOfRegistrationByEvent(id);
		int spaceAvailable = (event.getCapacity() - numberOfRegistrations);
		
		model.addAttribute("event", event);
		model.addAttribute("registrations", registrations);
		model.addAttribute("spaceAvailable", spaceAvailable);
		
		return "info";
	}
	
	@GetMapping("/events/edit/{id}")
	public String editEvent(@PathVariable Long id, Model model) {
		model.addAttribute("event", eventService.getEvent(id));
		
		return "edit";
	}
	
	@PostMapping("/events/")
	public String addEvent(@Valid Event event, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-event";
	    }
		/*
		 * Obtengo el usuario logueado, para recuperar los eventos creados por él.
		 */
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUser = (UserDetails) principal;
		String username = loggedUser.getUsername();
		Usuario user = userService.findUserByUsername(username);
		
		event.setOwner(user);
		eventService.addEvent(event);
		
		return "home";
	}
	
	@PostMapping("/events/{id}")
	public String deleteEvent(@PathVariable Long id, Model model) {
		/*
		 * Obtengo el usuario logueado, para recuperar los eventos creados por él.
		 */
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUser = (UserDetails) principal;
		String username = loggedUser.getUsername();
		Usuario user = userService.findUserByUsername(username);
		
		if(eventService.getEvent(id).getOwner().getId() == user.getId()) {
				eventService.deleteEvent(id);
				
				List<Event> events = eventService.findAllEventByOwnerID(user.getId());
				model.addAttribute("events", events);
				
				return "my-events";
		}
		
		List<Event> events = eventService.findAllEvents();
		model.addAttribute("events", events);
		
		return "events";
	}
	
	@GetMapping("/events/{id}")	
	public String replaceEvent(@Valid Event event, BindingResult result, @PathVariable Long id, Model model) {
		if (result.hasErrors()) {
			return "edit";
	    }
		/*
		 * Obtengo el usuario logueado, para recuperar los eventos creados por él.
		 */
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUser = (UserDetails) principal;
		String username = loggedUser.getUsername();
		Usuario user = userService.findUserByUsername(username);
		
		if(eventService.getEvent(id).getOwner().getId() == user.getId()) {
			eventService.updateEvent(event , id);
			
			List<Event> events = eventService.findAllEventByOwnerID(user.getId());
			model.addAttribute("events", events);
			
			return "my-events";
		}
		
		List<Event> events = eventService.findAllEvents();
		model.addAttribute("events", events);
		
		return "events";
	}
		
}
