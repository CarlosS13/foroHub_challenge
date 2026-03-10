package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.domain.respuesta.*;
import com.aluracursos.forohub.domain.topico.TopicoRepository;
import com.aluracursos.forohub.domain.usuario.Usuario;
import com.aluracursos.forohub.infra.errors.ValidacionException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroRespuesta datos,
                                    @AuthenticationPrincipal Usuario autor) {
        var topico = topicoRepository.findById(datos.idTopico())
                .orElseThrow(() -> new ValidacionException("El tópico indicado no existe"));

        var respuesta = new Respuesta(null, datos.mensaje(), LocalDateTime.now(), false, topico, autor);
        respuestaRepository.save(respuesta);

        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizar(@RequestBody @Valid DatosActualizarRespuesta datos) {
        var respuesta = respuestaRepository.getReferenceById(datos.id());

        respuesta.actualizarInformacion(datos);

        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        if (!respuestaRepository.existsById(id)) {
            throw new ValidacionException("La respuesta no existe");
        }
        respuestaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
