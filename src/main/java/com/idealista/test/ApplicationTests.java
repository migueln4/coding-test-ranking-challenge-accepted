package com.idealista.test;

import com.idealista.main.application.service.ServiceApi;
import com.idealista.main.persistence.InMemoryPersistence;
import com.idealista.main.persistence.enums.AdTypology;
import com.idealista.main.persistence.vo.AdVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ApplicationTests {

    @Mock
    private InMemoryPersistence imp;

    @Mock
    private ServiceApi service;


    @Test
    public void testScoreNoEsMenorQueCero() {
        AdVO adVO = new AdVO(1000, AdTypology.CHALET,null,Collections.emptyList(),null,null,null,null);
        when(imp.calculateScore(adVO)).thenReturn(0);
    }

    @Test
    public void testScoreNoEsMayorQueCien() {
        AdVO adVO = new AdVO(1000, AdTypology.CHALET, "Luminoso chalet muy céntrico y reformado a pocos metros del museo. El suelo está nuevo y tiene un ático muy amplio. Además, cuenta con cocina y baño completamente amueblados. Mejor ver para aseguraros de que es un chollo y comprobar que este anuncio tiene la suficiente longitud para poder hacer el test.", Arrays.asList(2,4),100,20,null,null);
        when(imp.calculateScore(adVO)).thenReturn(100);
    }

    @Test
    public void testScoreExactamenteVeinte() {
        AdVO adVO = new AdVO(100,AdTypology.GARAGE,null,Arrays.asList(1,3),null,null,null,null);
        when(imp.calculateScore(adVO)).thenReturn(20);
    }


    @Test
    public void testServiceSumaTotal() {
        when(service.calculateScoreAllAds()).thenReturn(335);
    }


}
