package com.ejemplo.clientesapi.controller;

import com.ejemplo.clientesapi.model.Usuario;
import com.ejemplo.clientesapi.model.TipoPlan;
import com.ejemplo.clientesapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody Map<String, String> datos) {
        try {
            Usuario usuario = authService.registrarUsuario(
                datos.get("email"),
                datos.get("password"),
                datos.get("nombres"),
                datos.get("apellidos")
            );
            return ResponseEntity.ok(Map.of("mensaje", "Usuario registrado exitosamente", "id", usuario.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        try {
            Usuario usuario = authService.autenticar(
                credenciales.get("email"),
                credenciales.get("password")
            );
            return ResponseEntity.ok(Map.of(
                "mensaje", "Login exitoso",
                "usuario", Map.of(
                    "id", usuario.getId(),
                    "email", usuario.getEmail(),
                    "nombres", usuario.getNombres(),
                    "plan", usuario.getPlan(),
                    "busquedasRestantes", usuario.getBusquedasRestantes()
                )
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // Nuevo endpoint para actualizar plan (solo para pruebas)
    @PutMapping("/actualizar-plan/{usuarioId}")
    public ResponseEntity<?> actualizarPlan(@PathVariable Long usuarioId, @RequestBody Map<String, String> datos) {
        try {
            String planStr = datos.get("plan");
            TipoPlan nuevoPlan = TipoPlan.valueOf(planStr.toUpperCase());
            
            Usuario usuario = authService.actualizarPlan(usuarioId, nuevoPlan);
            
            return ResponseEntity.ok(Map.of(
                "mensaje", "Plan actualizado exitosamente",
                "usuario", Map.of(
                    "id", usuario.getId(),
                    "email", usuario.getEmail(),
                    "nombres", usuario.getNombres(),
                    "plan", usuario.getPlan(),
                    "busquedasRestantes", usuario.getBusquedasRestantes()
                )
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}