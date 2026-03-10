package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.domain.perfil.DatosActualizarPerfil;
import com.aluracursos.forohub.domain.perfil.DatosRegistroPerfil;
import com.aluracursos.forohub.domain.perfil.Perfil;
import com.aluracursos.forohub.domain.perfil.PerfilRepository;
import com.aluracursos.forohub.infra.errors.ValidacionException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfiles")
@SecurityRequirement(name = "bearer-key")
public class PerfilController {

    @Autowired
    private PerfilRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroPerfil datos) {
        var perfil = new Perfil(null, datos.nombre());
        repository.save(perfil);
        return ResponseEntity.ok(perfil);
    }

    @GetMapping
    public ResponseEntity<List<Perfil>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizar(@RequestBody @Valid DatosActualizarPerfil datos) {
        var perfil = repository.getReferenceById(datos.id());
        perfil.actualizarInformacion(datos);
        return ResponseEntity.ok(perfil);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ValidacionException("El perfil no existe");
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
