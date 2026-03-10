package com.aluracursos.forohub.controller;


import com.aluracursos.forohub.domain.curso.Curso;
import com.aluracursos.forohub.domain.curso.CursoRepository;
import com.aluracursos.forohub.domain.topico.DatosActualizarTopico;
import com.aluracursos.forohub.domain.topico.DatosDetalleTopico;
import com.aluracursos.forohub.domain.topico.Topico;
import com.aluracursos.forohub.domain.topico.TopicoRepository;
import com.aluracursos.forohub.domain.topico.DatosRegistroTopico;
import com.aluracursos.forohub.domain.usuario.Usuario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetalleTopico> registrarTopico(
            @RequestBody @Valid DatosRegistroTopico datos,
            UriComponentsBuilder uriBuilder,
            @AuthenticationPrincipal Usuario autorLogueado
    ) {
        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new ValidationException("Ya existe un tópico con el mismo título y mensaje.");
        }

        Curso curso = cursoRepository.findById(datos.idCurso())
                .orElseThrow(()-> new ValidationException("El curso indicado no existe"));

        Topico topico = topicoRepository.save(new Topico(datos, autorLogueado, curso));

        DatosDetalleTopico datosDetalleTopico = new DatosDetalleTopico(topico);
        URI url = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(url).body(datosDetalleTopico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleTopico>> listarTopicos(
            @PageableDefault(size = 10, sort = {"fechaCreacion"}, direction = Sort.Direction.ASC) Pageable paginacion) {
        var pagina = topicoRepository.findAllByStatus("ACTIVO", paginacion).map(DatosDetalleTopico::new);

        return ResponseEntity.ok(pagina);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosDetalleTopico> actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid DatosActualizarTopico datos,
            @AuthenticationPrincipal Usuario autorLogueado) {
        var topico = topicoRepository.getReferenceById(id);

        if (!topico.getAutor().getId().equals(autorLogueado.getId())) {
            throw new ValidationException("No tienes permisos para modificar este tópico, no eres el autor.");
        }
        topico.actualizarInformacion(datos);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @PutMapping("/{id}/resolver")
    @Transactional
    public ResponseEntity marcarTopicoComoResuelto(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);

        topico.marcarComoResuelto();

        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> obtenerUnTopico(@PathVariable Long id) {
        var topico =  topicoRepository.findById(id);
        if (topico.isPresent()) {
            return ResponseEntity.ok(new DatosDetalleTopico(topico.get()));
        } else  {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosDetalleTopico> eliminarTopico(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario autorLogueado) {
        var topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isPresent()) {
            var topico = topicoOptional.get();

            if (!topico.getAutor().getId().equals(autorLogueado.getId())) {
                throw new ValidationException("No tienes permisos para eliminar este tópico, no eres el autor.");
            }
            topico.ocultarTopico();
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
