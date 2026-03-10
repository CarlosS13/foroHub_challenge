CREATE TABLE respuestas (
        id BIGINT NOT NULL AUTO_INCREMENT,
        mensaje TEXT NOT NULL,
        topico_id BIGINT NOT NULL,
        fecha_creacion DATETIME NOT NULL,
        usuario_id BIGINT NOT NULL,
        solucion TINYINT NOT NULL DEFAULT 0,

        PRIMARY KEY (id),
        CONSTRAINT fk_respuestas_topico FOREIGN KEY (topico_id) REFERENCES topicos(id),
        CONSTRAINT fk_respuestas_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);