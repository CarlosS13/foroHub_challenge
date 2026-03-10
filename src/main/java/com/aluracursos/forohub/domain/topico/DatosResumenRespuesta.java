package com.aluracursos.forohub.domain.topico;

import com.aluracursos.forohub.domain.respuesta.Respuesta;

import java.time.LocalDateTime;

public record DatosResumenRespuesta(
        Long id,
        String mensaje,
        String autor,
        LocalDateTime fechaCreacion,
        Boolean solucion
) {
    public DatosResumenRespuesta(Respuesta respuesta) {
        this(respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getAutor().getLogin(),
                respuesta.getFechaCreacion(),
                respuesta.getSolucion());
    }
}
