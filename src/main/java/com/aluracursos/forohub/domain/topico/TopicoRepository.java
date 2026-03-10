package com.aluracursos.forohub.domain.topico;

import aj.org.objectweb.asm.commons.Remapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findAllByStatus(String status, Pageable paginacion);

    boolean existsByTituloAndMensaje(String titulo, String mensaje);
}
