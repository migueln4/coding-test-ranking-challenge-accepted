package com.idealista.main.application.web.ads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>Objeto que representa un anuncio con los argumentos que pueden ser visibles para cualquier tipo de usuario.</p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PublicAd {

    private Integer id;
    private String typology;
    private String description;
    private List<String> pictureUrls;
    private Integer houseSize;
    private Integer gardenSize;

}
