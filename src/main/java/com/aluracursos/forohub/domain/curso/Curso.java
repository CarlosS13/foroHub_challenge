package com.aluracursos.forohub.domain.curso;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "cursos")
@Entity(name = "Curso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String categoria;
    private Boolean activo = true;

    public Curso(Long id, String nombre, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.activo = true;
    }

    public void actualizarInformacion(DatosActualizarCurso datos) {
        if (datos.nombre() != null) {
            this.nombre = datos.nombre();
        }
        if (datos.categoria() != null) {
            this.categoria = datos.categoria();
        }
    }

    public void eliminarLogico() {
        this.activo = false;
    }
}
