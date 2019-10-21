package com.idealista.main.application.service;

import com.idealista.main.application.web.ads.PublicAd;
import com.idealista.main.application.web.ads.QualityAd;

import java.util.List;

public interface ServiceApi {

    /**
     * <p>Este método devuelve una lista con todos los anuncios que puede ver el responsable de calidad.</p>
     *
     * @return Lista de objetos del tipo {@link QualityAd}
     */
    List<QualityAd> listAllQualityAds();

    /**
     * <p>Este método devuelve una lista con todos los anuncios que se pueden mostrar públicamente.</p>
     *
     * @return Lista de objetos del tipo {@link PublicAd}
     */
    List<PublicAd> listAllPublicAds();

    /**
     * <p>Este método devuelve el valor total de sumar todos los anuncios que hay cargados en memoria.</p>
     *
     * @return Integer con el total de la suma.
     */
    Integer calculateScoreAllAds();
}
