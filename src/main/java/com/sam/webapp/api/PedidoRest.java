package com.sam.webapp.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sam.webapp.dto.RequisicaoNovoPedido;
import com.sam.webapp.model.Pedido;
import com.sam.webapp.model.StatusDoPedido;
import com.sam.webapp.model.User;
import com.sam.webapp.repository.PedidoRepository;
import com.sam.webapp.repository.UserRepository;

@RestController
@RequestMapping("api/pedidos")
public class PedidoRest {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public List<Pedido> getPedidos() {
		return pedidoRepository.findAll(); 
	}
	
	@GetMapping("aguardando")
	public List<Pedido> getPedidosAguardando() {
		PageRequest page = PageRequest.of(0, 5, Sort.by("id").descending()); 
		
		return pedidoRepository.findAllByStatus(StatusDoPedido.AGUARDANDO, (Pageable) page);
	}
	
	@GetMapping("entregues")
	public List<Pedido> getPedidosEntregues() {
		PageRequest page = PageRequest.of(0, 5, Sort.by("id").descending()); 
		
		return pedidoRepository.findAllByStatus(StatusDoPedido.ENTREGUE, (Pageable) page);
	}
	
	@GetMapping("aprovados")
	public List<Pedido> getPedidosAprovados() {
		PageRequest page = PageRequest.of(0, 5, Sort.by("id").descending()); 
		
		return pedidoRepository.findAllByStatus(StatusDoPedido.APROVADO, (Pageable) page);
	}
	
	@PostMapping("novo")
	public Pedido poostPedido(@Validated @RequestBody RequisicaoNovoPedido novoPedido, BindingResult result) {
		
		if( result.hasErrors() ) return null;
		
		Pedido pedido = novoPedido.toPedido();
		
		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		pedido.setUser(user);
		pedidoRepository.save( pedido );
		
		return pedido;
	}
}
