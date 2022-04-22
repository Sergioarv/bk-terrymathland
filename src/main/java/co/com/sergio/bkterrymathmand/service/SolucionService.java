package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Solucion;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 22/04/2022 14:52
 **/

public interface SolucionService {

  public List<Solucion> getAllSolucion();
}
