package com.ejemplo.clientesapi.service;

import com.ejemplo.clientesapi.model.Usuario;
import com.ejemplo.clientesapi.model.TipoPlan;
import com.ejemplo.clientesapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Usuario registrarUsuario(String email, String password, String nombres, String apellidos) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("El email ya está registrado");
        }
        
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setNombres(nombres);
        usuario.setApellidos(apellidos);
        
        return usuarioRepository.save(usuario);
    }
    
    public Usuario autenticar(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }
        
        return usuario;
    }
    
    // Nuevo método para actualizar plan
    public Usuario actualizarPlan(Long usuarioId, TipoPlan nuevoPlan) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        usuario.setPlan(nuevoPlan);
        
        // Actualizar búsquedas según el plan usando el método getBusquedasIncluidas()
        int busquedasIncluidas = nuevoPlan.getBusquedasIncluidas();
        if (busquedasIncluidas == -1) {
            // Plan ilimitado
            usuario.setBusquedasRestantes(999999);
        } else {
            usuario.setBusquedasRestantes(busquedasIncluidas);
        }
        
        return usuarioRepository.save(usuario);
    }
}