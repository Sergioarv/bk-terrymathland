package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Cartilla;
import co.com.sergio.bkterrymathmand.entity.Pregunta;
import co.com.sergio.bkterrymathmand.repository.CartillaRepository;
import co.com.sergio.bkterrymathmand.repository.PreguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CartillaServiceImpl implements CartillaService {

    @Autowired
    private CartillaRepository cartillaRepository;

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Cartilla> obtenerCartillas() {
        return cartillaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pregunta> obtenerPreguntas(int idcartilla) {

        Cartilla cartilla = cartillaRepository.findById(idcartilla).orElse(null);

        if (cartilla != null) {
            return cartilla.getPreguntas();
        }

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pregunta> filtrarPregunta(String idcartilla) {

        List<Pregunta> listResult = null;

        if (idcartilla != null) {
            listResult = obtenerPreguntas(Integer.parseInt(idcartilla));
        } else {
            listResult = preguntaRepository.findAll();
        }
        return listResult;
    }

    @Override
    @Transactional
    public Cartilla crearCartilla(Cartilla cartilla) {

        Cartilla result = cartillaRepository.save(cartilla);
        cartilla.setIdcartilla(result.getIdcartilla());

        for (int j = 0; j < cartilla.getPreguntas().size(); j++) {
            Pregunta pcartilla = preguntaRepository.getById(cartilla.getPreguntas().get(j).getIdpregunta());
            cartilla.getPreguntas().get(j).agregarCartilla(result);
            List<Cartilla> listFinal = Stream.concat(cartilla.getPreguntas().get(j).obtenerCartillas().stream(), pcartilla.obtenerCartillas().stream())
                    .distinct()
                    .collect(Collectors.toList());
            cartilla.getPreguntas().get(j).setCartillas(listFinal);
        }

        if (preguntaRepository.saveAll(cartilla.getPreguntas()) == null) {
            return null;
        }

        return cartilla;
    }

    @Override
    @Transactional
    public Boolean eliminarCartilla(Cartilla cartilla) {

        Cartilla original = cartillaRepository.findById(cartilla.getIdcartilla()).orElse(null);

        if (original != null) {
            for (int j = 0; j < cartilla.getPreguntas().size(); j++) {
                Pregunta pcartilla = preguntaRepository.getById(cartilla.getPreguntas().get(j).getIdpregunta());
                pcartilla.removerCartilla(original);
                cartilla.getPreguntas().get(j).setCartillas(pcartilla.obtenerCartillas());
            }

            if (preguntaRepository.saveAll(cartilla.getPreguntas()) != null) {
                cartillaRepository.delete(cartilla);
            } else {
                return null;
            }
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean actualizarCartilla(Cartilla cartilla) {

        Cartilla original = cartillaRepository.findById(cartilla.getIdcartilla()).orElse(null);

        if (original != null) {
            List<Pregunta> sobrantes = new ArrayList<>();

            int tamPc = cartilla.getPreguntas().size();
            int tamPo = original.getPreguntas().size();

            for (int j = 0; j < tamPc; j++) {
                Pregunta pcartilla = preguntaRepository.getById(cartilla.getPreguntas().get(j).getIdpregunta());
                cartilla.getPreguntas().get(j).agregarCartilla(original);
                List<Cartilla> listFinal = Stream.concat(cartilla.getPreguntas().get(j).obtenerCartillas().stream(), pcartilla.obtenerCartillas().stream())
                        .distinct()
                        .collect(Collectors.toList());
                cartilla.getPreguntas().get(j).setCartillas(listFinal);
            }

            boolean isTrue = false;
            for (int i = 0; i < tamPo; i++) {
                isTrue = false;
                Pregunta p = original.getPreguntas().get(i);
                for (int j = 0; j < tamPc; j++) {
                    Pregunta pc = cartilla.getPreguntas().get(j);
                    if (pc.getIdpregunta() == p.getIdpregunta()) {
                        isTrue = true;
                        break;
                    }
                }
                if (!isTrue) {
                    sobrantes.add(p);
                }
            }

            for (Pregunta sobrante : sobrantes) {
                sobrante.removerCartilla(original);
            }

            preguntaRepository.saveAll(sobrantes);

            if (preguntaRepository.saveAll(cartilla.getPreguntas()) != null) {
                cartillaRepository.save(cartilla);
                return true;
            }
        }
        return false;

    }
//
//    private boolean guardarMayorIgualPreguntas(int tamPc, int tamPo, Cartilla cartilla, Cartilla original) {
//
//
//    }
//
//    private boolean guardarMenosPreguntas(int tamPc, int tamPo, Cartilla cartilla, Cartilla original) {
//
//        List<Pregunta> sobrantes = new ArrayList<>();
//
//        for (int j = 0; j < tamPc; j++) {
//            cartilla.getPreguntas().get(j).agregarCartilla(original);
//        }
//
//        boolean isTrue = false;
//        for (int i = 0; i < tamPo; i++) {
//            isTrue = false;
//            Pregunta p = original.getPreguntas().get(i);
//            for (int j = 0; j < tamPc; j++) {
//                Pregunta pc = cartilla.getPreguntas().get(j);
//                if (pc.getIdpregunta() == p.getIdpregunta()) {
//                    isTrue = true;
//                    break;
//                }
//            }
//            if (!isTrue) {
//                original.getPreguntas().get(i).removerCartilla(original);
//                tamPo = original.getPreguntas().size();
//            }
//        }
//        cartillaRepository.save(original);
//
//        if (preguntaRepository.saveAll(cartilla.getPreguntas()) != null) {
//            if (cartillaRepository.save(cartilla) != null) {
//                return true;
//            }
//        }
//        return false;
//    }
}
