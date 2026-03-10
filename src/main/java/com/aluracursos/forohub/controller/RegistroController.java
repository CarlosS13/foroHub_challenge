package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.domain.perfil.PerfilRepository;
import com.aluracursos.forohub.domain.usuario.DatosRegistroUsuario;
import com.aluracursos.forohub.domain.usuario.Usuario;
import com.aluracursos.forohub.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroUsuario datos) {
        String passwordEncriptada = passwordEncoder.encode(datos.clave());

        var usuario = new Usuario(datos.login(), passwordEncriptada);

        var perfilBase = perfilRepository.findByNombre("ROLE_USER");
        if (perfilBase != null) {
            usuario.getPerfiles().add(perfilBase);
        }
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuario registrado exitosamente. Ya puedes iniciar sesión.");
    }
}
