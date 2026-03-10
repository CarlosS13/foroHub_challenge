CREATE TABLE perfiles (
         id BIGINT NOT NULL AUTO_INCREMENT,
         nombre VARCHAR(100) NOT NULL,

         PRIMARY KEY (id)
);

CREATE TABLE usuarios_perfiles (
          usuario_id BIGINT NOT NULL,
          perfil_id BIGINT NOT NULL,

          PRIMARY KEY (usuario_id, perfil_id),
          CONSTRAINT fk_usuarios_perfiles_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
          CONSTRAINT fk_usuarios_perfiles_perfil FOREIGN KEY (perfil_id) REFERENCES perfiles(id)
);

INSERT INTO perfiles (id, nombre) VALUES (1, 'ROLE_USER');
INSERT INTO perfiles (id, nombre) VALUES (2, 'ROLE_ADMIN');

INSERT INTO usuarios_perfiles (usuario_id, perfil_id) VALUES (1, 1);