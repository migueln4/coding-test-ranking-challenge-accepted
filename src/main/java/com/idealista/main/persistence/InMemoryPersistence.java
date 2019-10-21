package com.idealista.main.persistence;

import com.idealista.main.persistence.enums.AdTypology;
import com.idealista.main.persistence.enums.PictureQuality;
import com.idealista.main.persistence.vo.AdVO;
import com.idealista.main.persistence.vo.PictureVO;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * <p>El constructor de esta clase se invoca al arrancar la aplicación y guarda en memoria la lista de anuncios y fotografías que se van a utilizar en toda la prueba. A continuación, se ejecuta el método <b>init()</b> que sirve de puente para llamar al cálculo de los puntos de cada uno de los anuncios, de acuerdo con los requisitos especificados.</p>
 *
 * @author Miguel Negrillo
 */
@Repository
@Data
public class InMemoryPersistence {

    private List<AdVO> ads;
    private List<PictureVO> pictures;

    private final String REGEXP = "\\s+|\\n";

    private final String[] MAIN_WORDS = {"Luminoso", "Nuevo", "Céntrico", "Reformado", "Ático"};

    public InMemoryPersistence() {

        ads = new ArrayList<AdVO>();
        ads.add(new AdVO(1, AdTypology.CHALET, "Este piso es una ganga, compra, compra, COMPRA!!!!!", Collections.<Integer>emptyList(), 300, null, null, null));
        ads.add(new AdVO(2, AdTypology.FLAT, "Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo", Arrays.asList(4), 300, null, null, null));
        ads.add(new AdVO(3, AdTypology.CHALET, "", Arrays.asList(2), 300, null, null, null));
        ads.add(new AdVO(4, AdTypology.FLAT, "Ático céntrico muy luminoso y recién reformado, parece nuevo", Arrays.asList(5), 300, null, null, null));
        ads.add(new AdVO(5, AdTypology.FLAT, "Pisazo,", Arrays.asList(3, 8), 300, null, null, null));
        ads.add(new AdVO(6, AdTypology.GARAGE, "", Arrays.asList(6), 300, null, null, null));
        ads.add(new AdVO(7, AdTypology.GARAGE, "Garaje en el centro de Albacete", Collections.<Integer>emptyList(), 300, null, null, null));
        ads.add(new AdVO(8, AdTypology.CHALET, "Maravilloso chalet situado en lAs afueras de un pequeño pueblo rural. El entorno es espectacular, las vistas magníficas. ¡Cómprelo ahora!", Arrays.asList(1, 7), 300, null, null, null));

        pictures = new ArrayList<PictureVO>();
        pictures.add(new PictureVO(1, "http://www.idealista.com/pictures/1", PictureQuality.SD));
        pictures.add(new PictureVO(2, "http://www.idealista.com/pictures/2", PictureQuality.HD));
        pictures.add(new PictureVO(3, "http://www.idealista.com/pictures/3", PictureQuality.SD));
        pictures.add(new PictureVO(4, "http://www.idealista.com/pictures/4", PictureQuality.HD));
        pictures.add(new PictureVO(5, "http://www.idealista.com/pictures/5", PictureQuality.SD));
        pictures.add(new PictureVO(6, "http://www.idealista.com/pictures/6", PictureQuality.SD));
        pictures.add(new PictureVO(7, "http://www.idealista.com/pictures/7", PictureQuality.SD));
        pictures.add(new PictureVO(8, "http://www.idealista.com/pictures/8", PictureQuality.HD));
    }

    /**
     * <p>Método que se llama en el arranque de la aplicación para calcular el único dato común que falta en la memoria con respecto a los anuncios: los puntos.</p>
     *
     * <p>Además, se encarga de setear uno de los elementos que también faltan en algunos casos. Si la puntuación del anuncio es menor que 40, se debe guardar la fecha desde que este es irrelevante.</p>
     */
    public void init() {
        for(AdVO ad : ads) {
            Integer score = calculateScore(ad);
            ad.setScore(score);
            if(score < 40) {
                ad.setIrrelevantSince(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            }

        }
    }


    /**
     * <p>Este método devuelve un Optional del anuncio con el formato con el que está guardado en memoria: {@link AdVO}</p>
     *
     * @param id es el identificador del anuncio, no la posición en el array.
     * @return Devuelve un optional con con el anuncio que se requiere. Si no existe, devuelve un Optional vacío.
     */
    public Optional<AdVO> getAdVOById(Integer id) {
        return ads.stream().filter(ad -> ad.getId().equals(id)).findFirst();
    }

    /**
     * <p>Este método devuelve una fotografía como un objeto del tipo {@link PictureVO}, en función del identificador que se le pase.</p>
     * @param id es el identificador de la fotografía, no la posición en el array.
     * @return Devuelve un optional con la fotografía que se requiere. Si no existe, devuelve un Optional vacío.
     */
    public Optional<PictureVO> getPictureVOById(Integer id) {
        try {
            return Optional.of(pictures.get(id-1));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }

    }

    /**
     * <p>Este método devuelve la lista de fotografías asociadas a un anuncio en concreto. Es una lista de opcionales de {@link PictureVO}</p>
     * @param adId es el identificador del anuncio del que se quieren las fotografías, no la posición en el array.
     * @return Devuelve una lista de Optionals de fotografías.
     */
    public List<Optional<PictureVO>> getPicturesFromAd(Integer adId) {
        List<Optional<PictureVO>> toReturn = new ArrayList<>();
        Optional<AdVO> adVOOptional = getAdVOById(adId);
        if (adVOOptional.isPresent()) {
            AdVO ad = adVOOptional.get();
            if (!ad.getPictures().isEmpty()) {
                ad.getPictures().stream().filter(Objects::nonNull).forEach(idPicture -> toReturn.add(getPictureVOById(idPicture)));
            }
            return toReturn;
        } else {
            return Collections.singletonList(Optional.empty());
        }
    }

    /**
     * <p>En este método se controla la suma de los puntos de cada uno de los anuncios que existen en la lista guardada en memoria.</p>
     *
     * <p>Una vez que se han calculado todos, se hacen las correcciones necesaria para que coincida con los requisitos:</p>
     *
     * <ul>
     *     <li>El valor de puntuación de cada anuncio debe estar entre 0 y 100.</li>
     *     <li>Si se trata de un anuncio considerado "completo", este obtendrá +40 puntos.</li>
     * </ul>
     */
    public Integer calculateScore(AdVO ad) {
            Integer score = scoreForDescription(ad);
            score += scoreForPictures(ad);
            score += (!fullAd(ad)) ? Integer.valueOf(0) : Integer.valueOf(40);
            if(score < Integer.valueOf(0)) {
                return Integer.valueOf(0);
            } else if (score > Integer.valueOf(100)) {
                return Integer.valueOf(100);
            } else {
                return score;
            }
    }

    /**
     * <p>Este método se encarga de calcular la puntuación de un anuncio en función de su descripción. Estos son los requisitos especificados:</p>
     *
     * <ul>
     *     <li>Si existe descripción: +5 puntos.</li>
     *     <li>Si se trata de un piso, si la descripción tiene entre 20 y 49 palabras, +10 puntos; y si tiene 50 o más, +30 puntos.</li>
     *     <li>Si se trata de un chalet, si la descripción tiene más de 50 palabras, +20 puntos.</li>
     *     <li>Si el texto de la descripción contiene alguna de las palabras clave "Luminoso", "Nuevo", "Céntrico", "Reformado" o "Ático", puntúa +5 puntos por cada una (solo se cuentan una vez, aunque puedan aparecer más).</li>
     * </ul>
     * @param ad Es del tipo {@link AdVO} para obtener la información del anuncio.
     * @return La suma de los puntos para ese anuncio en concreto, dependiendo de la descripción.
     */
    private Integer scoreForDescription(AdVO ad) {
        Integer score = Integer.valueOf(0);
        String description = ad.getDescription().toUpperCase();
        if (description != null && !description.isEmpty()) {
            score += Integer.valueOf(5);
            String[] descriptionByWords = description.split(REGEXP);
            switch (ad.getTypology()) {
                case FLAT:
                    if (descriptionByWords.length > 19) {
                        score += (description.length() <= 49) ? Integer.valueOf(10) : Integer.valueOf(30);
                    }
                    break;
                case CHALET:
                    if (descriptionByWords.length > 50) {
                        score += Integer.valueOf(20);
                    }
                    break;
                default:
                    break;
            }
            long coincidences = Arrays.stream(MAIN_WORDS).filter(word -> description.contains(word.toUpperCase())).count();
            score += Integer.valueOf(5 * (int) coincidences);
        }
        return score;
    }

    /**
     * <p>Este método calcula los puntos de un anuncio en concreto, dependiendo de las fotografías que tenga asignadas. Para este cálculo, se han establecido las siguientes condiciones, especificadas en la descripción de la prueba:</p>
     * <ul>
     *     <li>Si no tiene ninguna foto, -10 puntos.</li>
     *     <li>Por cada fotografía en HD, +20 puntos.</li>
     *     <li>Por cada fotografía en SD, +10 puntos.</li>
     * </ul>
     * @param ad Es del tipo {@link AdVO} para obtener la información del anuncio.
     * @return La suma de los puntos para ese anuncio en concreto, dependiendo de las fotografías asociadas a este.
     */
    private Integer scoreForPictures(AdVO ad) {
        Integer score = Integer.valueOf(0);
        List<Optional<PictureVO>> picsOptionalList = getPicturesFromAd(ad.getId());
        if(picsOptionalList != null && !picsOptionalList.isEmpty()) {
            for(Optional<PictureVO> picOptional : picsOptionalList) {
                if(picOptional.isPresent()) {
                    PictureVO pic = picOptional.get();
                    switch (pic.getQuality()) {
                        case SD:
                            score += Integer.valueOf(10);
                            break;
                        case HD:
                            score += Integer.valueOf(20);
                            break;
                        default:
                            break;
                    }
                }
            }
        } else {
            score += Integer.valueOf(-10);
        }
        return score;
    }

    /**
     * <p>Este método comprueba si un anuncio se considera completo, o no. Un anuncio será completo si:</p>
     * <ul>
     *     <li>Tiene, al menos, una fotografía.</li>
     *     <li>Es un piso o un chalet y tiene descripción y el tamaño de la vivienda.</li>
     *     <li>Es un chalet y también incluye el tamaño del jardín.</li>
     * </ul>
     * @param ad Es del tipo {@link AdVO} para obtener la información del anuncio.
     * @return Devuelve un valor lógico. TRUE si el anuncio es completo; FALSE si no lo es.
     */
    private boolean fullAd(AdVO ad) {
        if(!ad.getPictures().isEmpty()) {
            switch(ad.getTypology()) {
                case FLAT:
                    return (ad.getDescription() != null && !ad.getDescription().isEmpty() && ad.getHouseSize() != null && ad.getHouseSize() > 0);
                case CHALET:
                    return(ad.getDescription() != null && !ad.getDescription().isEmpty() && ad.getHouseSize() != null && ad.getHouseSize() > 0 && ad.getGardenSize() != null && ad.getGardenSize() > 0);
                default:
                    return true;
            }
        } else {
            return false;
        }
    }

}
