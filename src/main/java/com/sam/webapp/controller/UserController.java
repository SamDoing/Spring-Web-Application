package com.sam.webapp.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sam.webapp.model.StatusDoPedido;
import com.sam.webapp.repository.PedidoRepository;

@Controller
@RequestMapping("usuario")
public class UserController {
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@GetMapping("home")
	public String home(Model model, Principal principal) {
		model.addAttribute("pedidos", pedidoRepository.findAllByUserUsername(principal.getName()));
		return "usuario/home";
	}
	
	@GetMapping("pedido/{status}")
	public String filter(@PathVariable("status") String status, Model model, Principal principal) {
		model.addAttribute("pedidos", 
				pedidoRepository.findAllByStatusAndUserUsername(StatusDoPedido.valueOf(status.toUpperCase()),
																principal.getName()));
		model.addAttribute("status", status);
		return "usuario/home";
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public String onError() {
		return "redirect:/usuario/pedido";
	}
}
