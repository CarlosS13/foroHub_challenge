package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.domain.curso.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {

    @Autowired
    private CursoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroCurso datos) {
        var curso = new Curso(null, datos.nombre(), datos.categoria());
        repository.save(curso);
        return ResponseEntity.ok(curso);
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizar(@RequestBody @Valid DatosActualizarCurso datos) {
        var curso = repository.getReferenceById(datos.id());
        curso.actualizarInformacion(datos);
        return ResponseEntity.ok(new DatosDetalleCurso(curso));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        var curso = repository.getReferenceById(id);
        curso.eliminarLogico();
        return ResponseEntity.noContent().build();
    }
}
