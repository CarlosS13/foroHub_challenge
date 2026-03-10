package com.aluracursos.forohub.domain.topico;

import com.aluracursos.forohub.domain.curso.Curso;
import com.aluracursos.forohub.domain.respuesta.Respuesta;
import com.aluracursos.forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private String status;
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario autor;
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Respuesta> respuestas = new ArrayList<>();

    public Topico(DatosRegistroTopico datos, Usuario autor, Curso curso) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.status = "ACTIVO";
        this.fechaCreacion = LocalDateTime.now();
        this.curso = curso;
        this.autor = autor;
    }

    public void actualizarInformacion(DatosActualizarTopico datos) {
        if (datos.titulo() != null) {
            this.titulo = datos.titulo();
        }
        if (datos.mensaje() != null) {
            this.mensaje = datos.mensaje();
        }
    }

    public void ocultarTopico(){
        this.status = "INACTIVO";
    }

    public void marcarComoResuelto() {
        this.status = "RESUELTO";
    }
}
