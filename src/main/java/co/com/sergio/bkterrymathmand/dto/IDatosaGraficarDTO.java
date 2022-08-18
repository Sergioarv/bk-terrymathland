package co.com.sergio.bkterrymathmand.dto;

import java.util.List;

/**
 * @Project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 30/07/2022 15:08
 **/
public class IDatosaGraficarDTO {

    public List<IDatosPromedioNotas> listaPromedioNotas;
    public List<IDatosPromedioEstudiante> listaPromedioEstudiantes;

    public List<IDatosPromedioNotas> getListaPromedioNotas() {
        return listaPromedioNotas;
    }

    public void setListaPromedioNotas(List<IDatosPromedioNotas> listaPromedioNotas) {
        this.listaPromedioNotas = listaPromedioNotas;
    }

    public List<IDatosPromedioEstudiante> getListaPromedioEstudiantes() {
        return listaPromedioEstudiantes;
    }

    public void setListaPromedioEstudiantes(List<IDatosPromedioEstudiante> listaPromedioEstudiantes) {
        this.listaPromedioEstudiantes = listaPromedioEstudiantes;
    }
}
