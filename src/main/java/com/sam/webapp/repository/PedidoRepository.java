package com.sam.webapp.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sam.webapp.model.Pedido;
import com.sam.webapp.model.StatusDoPedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	
	@Cacheable("pedidos")	
	public List<Pedido> findAllByStatus(StatusDoPedido statusDoPedido, Pageable sort);
	
	public List<Pedido> findAllByUserUsername(String username);
	
	@Query("select p from Pedido p where user_username = :username and p.status = :status")
	public List<Pedido> findAllByStatusAndUserUsername(@Param("status")StatusDoPedido status, @Param("username")String username);
}
