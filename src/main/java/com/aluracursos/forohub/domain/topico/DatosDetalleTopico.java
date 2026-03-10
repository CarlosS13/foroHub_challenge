package com.aluracursos.forohub.domain.topico;

import java.time.LocalDateTime;
import java.util.List;

public record DatosDetalleTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        String status,
        String autor,
        String curso,
        List<DatosResumenRespuesta> respuestas) {

    public DatosDetalleTopico(Topico topico) {
        this(   topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor().getLogin(),
                topico.getCurso() != null ? topico.getCurso().getNombre(): "Sin Curso",
                topico.getRespuestas().stream().map(DatosResumenRespuesta::new).toList());
    }
}
