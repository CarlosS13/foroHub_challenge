CREATE TABLE cursos (
        id BIGINT NOT NULL AUTO_INCREMENT,
        nombre VARCHAR(100) NOT NULL,
        categoria VARCHAR(100) NOT NULL,

        PRIMARY KEY (id)
);

ALTER TABLE topicos ADD COLUMN curso_id BIGINT;
ALTER TABLE topicos ADD CONSTRAINT fk_topicos_curso FOREIGN KEY (curso_id) REFERENCES cursos(id);

INSERT INTO cursos (nombre, categoria) VALUES ('Spring Boot 3', 'Programación');
INSERT INTO cursos (nombre, categoria) VALUES ('Java Hibernate', 'Backend');