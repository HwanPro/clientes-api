package com.ejemplo.clientesapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Email
    @Column(unique = true)
    private String email;
    
    @NotBlank
    private String password;
    
    @NotBlank
    private String nombres;
    
    @NotBlank
    private String apellidos;
    
    @Enumerated(EnumType.STRING)
    private TipoPlan plan = TipoPlan.GRATUITO;
    
    private Integer busquedasRestantes = 5;
    
    private LocalDateTime fechaRegistro = LocalDateTime.now();
    
    private Boolean activo = true;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Cliente> clientes;
    
    // Constructors
    public Usuario() {}
    
    public Usuario(String email, String password, String nombres, String apellidos) {
        this.email = email;
        this.password = password;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    
    public TipoPlan getPlan() { return plan; }
    public void setPlan(TipoPlan plan) { this.plan = plan; }
    
    public Integer getBusquedasRestantes() { return busquedasRestantes; }
    public void setBusquedasRestantes(Integer busquedasRestantes) { this.busquedasRestantes = busquedasRestantes; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    public List<Cliente> getClientes() { return clientes; }
    public void setClientes(List<Cliente> clientes) { this.clientes = clientes; }
    
    // Removed the inner enum TipoPlan since we're using the separate class
}