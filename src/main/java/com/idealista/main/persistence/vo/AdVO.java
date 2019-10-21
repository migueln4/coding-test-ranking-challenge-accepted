package com.idealista.main.persistence.vo;

import com.idealista.main.persistence.enums.AdTypology;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * <p>Clase que representa los anuncios que están guardados en memoria.</p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdVO {

    private Integer id;
    private AdTypology typology;
    private String description;
    private List<Integer> pictures;
    private Integer houseSize;
    private Integer gardenSize;
    private Integer score;
    private Date irrelevantSince;

}
