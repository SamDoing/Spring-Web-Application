package com.sam.webapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sam.webapp.dto.RequisicaoNovoPedido;
import com.sam.webapp.model.Pedido;
import com.sam.webapp.model.User;
import com.sam.webapp.repository.PedidoRepository;
import com.sam.webapp.repository.UserRepository;

@Controller
@RequestMapping("pedido")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/formulario")
	public String formulario(RequisicaoNovoPedido novoPedido) {
		return "pedido/formulario";
	}
	
	@PostMapping("/novo")
	public String novo(@Valid RequisicaoNovoPedido novoPedido, BindingResult result) {
		
		if( result.hasErrors() ) return "pedido/formulario";
		
		Pedido pedido = novoPedido.toPedido();
		
		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		pedido.setUser(user);
		pedidoRepository.save( pedido );
		
		return "redirect:/usuario/home";
	}
}