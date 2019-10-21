package com.idealista.main.persistence.vo;

import com.idealista.main.persistence.enums.PictureQuality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>Clase que representa las fotografías que se han guardado en memoria.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PictureVO {

    private Integer id;
    private String url;
    private PictureQuality quality;
}
