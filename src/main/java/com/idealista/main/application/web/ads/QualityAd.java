package com.idealista.main.application.web.ads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * <p>Objeto con todos los parámetros que son necesarios para la vista del encargado de calidad.</p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QualityAd {

    private Integer id;
    private String typology;
    private String description;
    private List<String> pictureUrls;
    private Integer houseSize;
    private Integer gardenSize;
    private Integer score;
    private Date irrelevantSince;
}
