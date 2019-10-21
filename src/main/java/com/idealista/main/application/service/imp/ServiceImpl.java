package com.idealista.main.application.service.imp;

import com.idealista.main.application.service.ServiceApi;
import com.idealista.main.application.service.converter.AdConverter;
import com.idealista.main.application.web.ads.PublicAd;
import com.idealista.main.application.web.ads.QualityAd;
import com.idealista.main.persistence.InMemoryPersistence;
import com.idealista.main.persistence.vo.AdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Esta ese la clase encargada de implementar la API que se ha diseñado: {@link ServiceApi}.</p>
 *
 * <p>En los tres métodos de esta clase se utilizan las lambdas de Java 1.8 para hacer uso de la programación funcional.</p>
 */
@Service
public class ServiceImpl implements ServiceApi {

    @Autowired
    private InMemoryPersistence imp;

    @Autowired
    private AdConverter adConverter;


    /**
     * <p>Si la lista que hay en la memoria está vacía, devuelve una lista vacía. Si no, se devuelve la lista con la conversión de cada anuncio hecha.</p>
     * @return lista de anuncios, es una lista de objetos {@link QualityAd}
     */
    @Override
    public List<QualityAd> listAllQualityAds() {
        List<AdVO> adVOS = imp.getAds();
        if (adVOS != null && !adVOS.isEmpty()) {
            return adVOS.stream()
                    .map(adVO -> adConverter.adVOToQualityAd.apply(adVO))
                    .collect(Collectors.toList());
        }
        else {
            return Collections.emptyList();
        }
    }


    /**
     * <p>Si la lista que hay en memoria está vacía, devuelve una lista vacía. Si no, se devuelve una lista con la conversión de cada anuncio hecha</p>
     * @return Lista de anuncios, es una lista de objetos {@link PublicAd}
     */
    @Override
    public List<PublicAd> listAllPublicAds() {
        List<AdVO> adVOS = imp.getAds();
        if(adVOS != null && !adVOS.isEmpty()) {
            return adVOS.stream()
                    .filter(ad -> ad.getScore() >= 40)
                    .sorted(Comparator.comparing(AdVO::getScore).reversed())
                    .map(adVO -> adConverter.adVOToPublicAd.apply(adVO))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * <p>Si la lista que hay en memoria está vacía, devuelve un 0. Si no, se devuelve la suma de los valores de las puntuaciones de todos los anuncios.</p>
     * @return Devuelve un Integer con la suma de los valores de las puntuaciones sde todos los anuncios.
     */
    @Override
    public Integer calculateScoreAllAds() {
        List<AdVO> adVOS = imp.getAds();
        if(adVOS != null && !adVOS.isEmpty()) {
            return adVOS.stream()
                    .mapToInt(AdVO::getScore)
                    .sum();
        } else {
            return Integer.valueOf(0);
        }
    }
}
