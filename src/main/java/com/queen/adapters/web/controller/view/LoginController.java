package com.queen.adapters.web.controller.view;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
	private final ClientRegistrationRepository clientRegistrationRepository;

	public LoginController(ClientRegistrationRepository clientRegistrationRepository) {
		this.clientRegistrationRepository = clientRegistrationRepository;
	}

	@GetMapping("/login")
	public String login(Model model, HttpServletRequest request) {
		return "redirect:/oauth2/authorization/fella-webui";
//		// expose any flags you want to show messages for
//		model.addAttribute("error", request.getParameter("error") != null);
//		model.addAttribute("logout", request.getParameter("logout") != null);
//
//		// collect available OAuth clients so the page can render buttons/links
//		List<ClientRegistration> clients = new ArrayList<>();
//		if (clientRegistrationRepository instanceof Iterable<?> iterable) {
//			for (Object it : iterable) {
//				clients.add((ClientRegistration) it);
//			}
//		}
//		model.addAttribute("clients", clients);
//		return "login";
	}
}
