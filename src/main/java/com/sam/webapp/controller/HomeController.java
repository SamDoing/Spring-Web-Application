package com.sam.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sam.webapp.model.StatusDoPedido;
import com.sam.webapp.repository.PedidoRepository;

@Controller
@RequestMapping("home")
public class HomeController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@GetMapping("")
	public String filter(Model model) {
		
		PageRequest page = PageRequest.of(0, 5, Sort.by("dataEntrega").descending()); 
		model.addAttribute("pedidos", pedidoRepository.findAllByStatus(StatusDoPedido.ENTREGUE, (Pageable) page));
		return "home";
	}
}
