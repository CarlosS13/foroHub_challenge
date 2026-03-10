
INSERT INTO usuarios (login, clave)
VALUES ('profe.alura', '$2a$12$JTuezyMyL6Lrcnr3NqwJxeWdX3hnPcYyuRHFZBSnVhETEJLuM40Nu');

INSERT INTO usuarios_perfiles (usuario_id, perfil_id)
VALUES ((SELECT id FROM usuarios WHERE login = 'profe.alura'), 1);