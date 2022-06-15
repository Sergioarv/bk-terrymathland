package co.com.sergio.bkterrymathmand.dto;

import co.com.sergio.bkterrymathmand.entity.Respuesta;

import java.util.List;

public class IDatosaGraficarDTO {

    public List<Respuesta> respuestas;
    public List<IRespuestaProyeccion> respuestaFecha;

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public List<IRespuestaProyeccion> getRespuestaFecha() {
        return respuestaFecha;
    }

    public void setRespuestaFecha(List<IRespuestaProyeccion> respuestaFecha) {
        this.respuestaFecha = respuestaFecha;
    }
}
