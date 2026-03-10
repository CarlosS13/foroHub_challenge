package com.aluracursos.forohub.domain.respuesta;

import java.time.LocalDateTime;

public record DatosDetalleRespuesta(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        String autor,
        Long idTopico
) {
    public DatosDetalleRespuesta(Respuesta respuesta) {
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getFechaCreacion(),
                respuesta.getAutor().getLogin(), respuesta.getTopico().getId());
    }
}
