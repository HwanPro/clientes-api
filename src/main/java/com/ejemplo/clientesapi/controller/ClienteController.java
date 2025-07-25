package com.ejemplo.clientesapi.controller;

import com.ejemplo.clientesapi.model.Cliente;
import com.ejemplo.clientesapi.repository.ClienteRepository;
import com.ejemplo.clientesapi.service.ApiPeruDevService;
import com.ejemplo.clientesapi.service.ExcelExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {
    
    @Autowired
    private ApiPeruDevService apiPeruDevService;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ExcelExportService excelExportService;
    
    // Endpoint principal para consultar DNI
    @PostMapping("/consultar-dni")
    public ResponseEntity<?> consultarDni(@RequestBody Map<String, Object> request) {
        try {
            String dni = (String) request.get("dni");
            Long usuarioId = Long.valueOf(request.get("usuarioId").toString());
            
            Map<String, Object> resultado = apiPeruDevService.consultarDni(dni, usuarioId);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // CRUD - Obtener todos los clientes
    @GetMapping
    public ResponseEntity<?> obtenerTodosLosClientes() {
        try {
            List<Cliente> clientes = clienteRepository.findAll();
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // CRUD - Obtener todos los clientes de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerClientesPorUsuario(@PathVariable Long usuarioId) {
        try {
            System.out.println("Buscando clientes para usuario ID: " + usuarioId); // Log para debugging
            List<Cliente> clientes = clienteRepository.findByUsuarioId(usuarioId);
            System.out.println("Clientes encontrados: " + clientes.size()); // Log para debugging
            return ResponseEntity.ok(Map.of("clientes", clientes));
        } catch (Exception e) {
            System.err.println("Error al obtener clientes: " + e.getMessage()); // Log para debugging
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // CRUD - Obtener cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCliente(@PathVariable Long id) {
        try {
            Optional<Cliente> cliente = clienteRepository.findById(id);
            if (cliente.isPresent()) {
                return ResponseEntity.ok(cliente.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // CRUD - Actualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteActualizado) {
        try {
            System.out.println("Actualizando cliente ID: " + id); // Log para debugging
            Optional<Cliente> clienteExistente = clienteRepository.findById(id);
            if (clienteExistente.isPresent()) {
                Cliente cliente = clienteExistente.get();
                cliente.setNombres(clienteActualizado.getNombres());
                cliente.setApellidoPaterno(clienteActualizado.getApellidoPaterno());
                cliente.setApellidoMaterno(clienteActualizado.getApellidoMaterno());
                
                Cliente clienteGuardado = clienteRepository.save(cliente);
                return ResponseEntity.ok(Map.of("mensaje", "Cliente actualizado exitosamente", "cliente", clienteGuardado));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage()); // Log para debugging
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // CRUD - Eliminar cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        try {
            System.out.println("Eliminando cliente ID: " + id); // Log para debugging
            if (clienteRepository.existsById(id)) {
                clienteRepository.deleteById(id);
                return ResponseEntity.ok(Map.of("mensaje", "Cliente eliminado exitosamente"));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage()); // Log para debugging
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // Exportar a Excel
    @GetMapping("/exportar-excel/{usuarioId}")
    public ResponseEntity<InputStreamResource> exportarExcel(@PathVariable Long usuarioId) {
        try {
            System.out.println("Exportando Excel para usuario ID: " + usuarioId); // Log para debugging
            List<Cliente> clientes = clienteRepository.findByUsuarioId(usuarioId);
            System.out.println("Clientes a exportar: " + clientes.size()); // Log para debugging
            ByteArrayInputStream excelStream = excelExportService.exportarClientesAExcel(clientes);
            
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=clientes.xlsx");
            
            return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(excelStream));
                
        } catch (Exception e) {
            System.err.println("Error al exportar Excel: " + e.getMessage()); // Log para debugging
            return ResponseEntity.badRequest().build();
        }
    }
}
