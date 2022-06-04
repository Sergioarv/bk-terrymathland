package co.com.sergio.bkterrymathmand.controller;

import co.com.sergio.bkterrymathmand.entity.Solucion;
import co.com.sergio.bkterrymathmand.service.SolucionService;
import co.com.sergio.bkterrymathmand.utils.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 22/04/2022 15:08
 **/
@RestController
@RequestMapping("/solucion")
public class SolucionController {

    @Autowired
    private SolucionService solucionService;

    @GetMapping
    public ResponseEntity<GeneralResponse<List<Solucion>>> getAllSolucion(){

        GeneralResponse<List<Solucion>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.NOT_FOUND;
        List<Solucion> data;

        data = solucionService.getAllSolucion();

        if (data != null) {
            response.setData(data);
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(response, status);

    }
}
