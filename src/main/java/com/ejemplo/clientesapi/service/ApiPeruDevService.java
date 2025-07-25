package com.ejemplo.clientesapi.service;

import com.ejemplo.clientesapi.model.Cliente;
import com.ejemplo.clientesapi.model.Usuario;
import com.ejemplo.clientesapi.repository.ClienteRepository;
import com.ejemplo.clientesapi.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ApiPeruDevService {
    
    @Value("${apiperu.base-url}")
    private String baseUrl;
    
    @Value("${apiperu.token}")
    private String token;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public Map<String, Object> consultarDni(String dni, Long usuarioId) {
        try {
            // Verificar límites del usuario
            Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            if (usuario.getBusquedasRestantes() <= 0) {
                throw new RuntimeException("Has agotado tus búsquedas disponibles. Actualiza tu plan.");
            }
            
            // Preparar la consulta a API Peru Dev
            String url = baseUrl + "/api/dni/" + dni;
            
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            // Realizar la consulta
            ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, String.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode jsonResponse = objectMapper.readTree(response.getBody());
                
                if (jsonResponse.get("success").asBoolean()) {
                    JsonNode data = jsonResponse.get("data");
                    
                    // Crear o actualizar cliente
                    Cliente cliente = clienteRepository.findByDni(dni)
                        .orElse(new Cliente());
                    
                    cliente.setDni(dni);
                    cliente.setNombres(data.get("nombres").asText());
                    cliente.setApellidoPaterno(data.get("apellido_paterno").asText());
                    cliente.setApellidoMaterno(data.get("apellido_materno").asText());
                    cliente.setCodigoVerificacion(data.get("codigo_verificacion").asText());
                    cliente.setFechaConsulta(LocalDateTime.now());
                    cliente.setUsuario(usuario);
                    
                    clienteRepository.save(cliente);
                    
                    // Reducir búsquedas restantes
                    usuario.setBusquedasRestantes(usuario.getBusquedasRestantes() - 1);
                    usuarioRepository.save(usuario);
                    
                    // Preparar respuesta
                    Map<String, Object> resultado = new HashMap<>();
                    resultado.put("success", true);
                    resultado.put("mensaje", "Consulta realizada exitosamente");
                    resultado.put("cliente", Map.of(
                        "dni", cliente.getDni(),
                        "nombres", cliente.getNombres(),
                        "apellidoPaterno", cliente.getApellidoPaterno(),
                        "apellidoMaterno", cliente.getApellidoMaterno(),
                        "codigoVerificacion", cliente.getCodigoVerificacion(),
                        "fechaConsulta", cliente.getFechaConsulta()
                    ));
                    resultado.put("busquedasRestantes", usuario.getBusquedasRestantes());
                    
                    return resultado;
                } else {
                    throw new RuntimeException("DNI no encontrado en RENIEC");
                }
            } else {
                throw new RuntimeException("Error en la consulta a API Peru Dev");
            }
            
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", e.getMessage());
            return error;
        }
    }
}