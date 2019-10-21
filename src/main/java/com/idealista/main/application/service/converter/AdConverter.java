package com.idealista.main.application.service.converter;

import com.idealista.main.application.web.ads.PublicAd;
import com.idealista.main.application.web.ads.QualityAd;
import com.idealista.main.persistence.InMemoryPersistence;
import com.idealista.main.persistence.vo.AdVO;
import com.idealista.main.persistence.vo.PictureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>Esta clase contiene los dos conversores necesarios para devolver al cliente la información tal y como se ha dado especificada originalmente en el proyecto original.</p>
 *
 * <p>Aquí se puede encontrar el conversor de {@link AdVO} a {@link QualityAd} y de {@link AdVO} a {@link PublicAd}.</p>
 *
 * @author Miguel Negrillo
 */
@Component
public class AdConverter {

    @Autowired
    private InMemoryPersistence imp;

    /**
     * <p>Esta función recibe un anuncio con el formato tal y como se ha guardado en memoria y devuelve la conversión en el formato que desea ver el encargado de calidad.</p>
     * <p>advo es del tipo {@link AdVO}</p>
     * <p>Se devuelve la conversión del objeto del tipo AdVO en uno del tipo {@link QualityAd}</p>
     */
    public Function<AdVO,QualityAd> adVOToQualityAd = advo -> {
        QualityAd qualityAd = null;
        if(advo != null) {
            List<String> pictureUrls = getPictureUrlList(advo);
            qualityAd = new QualityAd(
                    advo.getId(),
                    advo.getTypology().name(),
                    advo.getDescription(),
                    pictureUrls,
                    advo.getHouseSize(),
                    advo.getGardenSize(),
                    advo.getScore(),
                    advo.getIrrelevantSince()
            );
        }
        return qualityAd;
    };

    /**
     * <p>Esta función recibe un anuncio con el formato tal y como se ha guardado en memoria y devuelve la conversión en el formato óptimo para que sea mostrado al público.</p>
     * <p>advo es del tipo {@link AdVO}</p>
     * <p>Se devuelve la conversión del objeto del tipo AdVO en uno del tipo {@link PublicAd}</p>
     */
    public Function<AdVO, PublicAd> adVOToPublicAd = advo -> {
      PublicAd publicAd = null;
      if(advo != null) {
          List<String> pictureUrls = getPictureUrlList(advo);
          publicAd = new PublicAd(
                  advo.getId(),
                  advo.getTypology().name(),
                  advo.getDescription(),
                  pictureUrls,
                  advo.getHouseSize(),
                  advo.getGardenSize()
          );
      }
      return publicAd;
    };

    /**
     * <p>Este método se encarga de recorrer la lista de enteros que tiene guardado cada anuncio guardado en memoria y recuperar la url relacionada con el identificador de dicho anuncio en la otra lista de fotografías que existe en memoria (objetos del tipo {@link PictureVO}).</p>
     * @param advo es el anuncio que está guardado en memoria, del tipo {@link AdVO}
     * @return Se devuelve una lista de Strings con las URL de cada imagen. En caso de que las imágenes que tengan asociadas dichos anuncios no tengan una URL, se devuvelve una lista vacía.
     */
    private List<String> getPictureUrlList(AdVO advo) {
        List<String> urls = new ArrayList<>();
        List<Integer> idPics = advo.getPictures();
        if(idPics != null && !idPics.isEmpty()) {
            urls = idPics.stream().filter(Objects::nonNull).map(picId -> imp.getPictureVOById(picId)).filter(Optional::isPresent).map(Optional::get).map(PictureVO::getUrl).filter(Objects::nonNull).collect(Collectors.toList());
        }
        return urls;
    }

}
