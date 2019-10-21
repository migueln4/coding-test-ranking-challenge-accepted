/**
 * <p>Este paquete contiene toda la lógica de la aplicación para la prueba solicitada.</p>
 *
 * <p>El equeleto aportado se ha ampliado, de forma que dentro de este paquete se han creado otros dos: {@link com.idealista.main.application.service} y {@link com.idealista.main.application.web}. En el primero, se ha incluido la lógica del servicio, con la API ({@link com.idealista.main.application.service.ServiceApi}) y la implementación ({@link com.idealista.main.application.service.imp.ServiceImpl}), así como un conversor ({@link com.idealista.main.application.service.converter.AdConverter}) que se utiliza para cambiar los objetos del tipo que se recuperan de memoria a lo que se debe devolver al cliente.</p>
 *
 * <p>En el segundo caso, se han dejado los objetos que se han proporcionado, {@link com.idealista.main.application.web.ads.PublicAd} y {@link com.idealista.main.application.web.ads.QualityAd}, que son los que se deben mostrar cuando se llama a los endpoints determinados. También, se ha colocadoel controlador {@link com.idealista.main.application.web.rest.AdsController} en esta parte del esqueleto.</p>
 *
 */
package com.idealista.main.application;

