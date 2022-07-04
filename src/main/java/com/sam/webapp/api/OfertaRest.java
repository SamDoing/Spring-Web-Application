package com.sam.webapp.api;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.webapp.dto.RequisicaoNovoOferta;
import com.sam.webapp.model.Oferta;
import com.sam.webapp.model.Pedido;
import com.sam.webapp.repository.PedidoRepository;

@RestController
@RequestMapping("api/ofertas")
public class OfertaRest {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@PostMapping("nova")
	public boolean postOferta(@Valid @RequestBody RequisicaoNovoOferta requisicao) {
		
		Optional<Pedido> oPedido = pedidoRepository.findById(requisicao.getPedidoId());
		if(oPedido.isEmpty())
			return false;
		
		Pedido pedido = oPedido.get();
		
		Oferta oferta = requisicao.toOferta();
		oferta.setPedido(pedido);
		
		pedido.getOfertas().add(oferta);

		pedidoRepository.save( pedido );
		return true;
	}
}
