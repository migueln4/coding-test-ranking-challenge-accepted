package com.idealista.main.application.web.rest;

import java.util.List;

import com.idealista.main.application.service.ServiceApi;
import com.idealista.main.application.web.ads.PublicAd;
import com.idealista.main.application.web.ads.QualityAd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Esta es la clase que se encarga del RestController, con los tres endpoints a los que se debe llamar para la resolución de la prueba.</p>
 *
 * @author Miguel Negrillo
 */
@RestController
public class AdsController {

    @Autowired
    private ServiceApi service;

    /**
     * <p>Método al que se llama al hacer una llamada GET al siguiente endpoint: http://localhost:8080/quality/all. Devuelve todos los anuncios que existen en la memoria con el formato que necesita el encargado de calidad.</p>
     * @return Lista de {@link QualityAd}.
     */
    @RequestMapping(method= RequestMethod.GET,path="/quality/all")
    public ResponseEntity<List<QualityAd>> qualityListing() {
        List<QualityAd> qualityAds = service.listAllQualityAds();
        if(qualityAds != null && !qualityAds.isEmpty()) {
            return new ResponseEntity<>(qualityAds, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * <p>Método al que se llama al hacer una llamada GET al siguiente endpoint: http://localhost:8080/public/all. Devuelve todos los anuncios válidos en la memoria con el formato que se ha decidido que tengan para el usuario público.</p>
     * @return Lista de {@link PublicAd}.
     */
    @RequestMapping(method= RequestMethod.GET,path="/public/all")
    public ResponseEntity<List<PublicAd>> publicListing() {
        List<PublicAd> publicAds = service.listAllPublicAds();
        if(publicAds != null && !publicAds.isEmpty()) {
            return new ResponseEntity<>(publicAds,HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * <p>Método al que se llama la hacer una llamada GET al siguiente endpoint: http://localhost:8080/quality/score. Sirve para obtener el valor total de la suma de todos los anuncios que existen en la memoria.</p>
     * @return Valor de todos los anuncios en la memoria en forma de Integer.
     */
    @RequestMapping(method= RequestMethod.GET,path="/quality/score")
    public ResponseEntity<Integer> calculateScore() {
        Integer totalScore = service.calculateScoreAllAds();
        if(totalScore != null) {
            return new ResponseEntity<>(totalScore,HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
}
