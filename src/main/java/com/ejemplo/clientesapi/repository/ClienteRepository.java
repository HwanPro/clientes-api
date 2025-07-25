package com.ejemplo.clientesapi.repository;

import com.ejemplo.clientesapi.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    // Buscar cliente por DNI
    Optional<Cliente> findByDni(String dni);
    
    // Buscar clientes por usuario
    java.util.List<Cliente> findByUsuarioId(Long usuarioId);
    
    // Verificar si existe un DNI
    boolean existsByDni(String dni);
}
