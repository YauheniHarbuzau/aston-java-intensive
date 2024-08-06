package com.aston.util;

import com.aston.controller.BeerController;
import com.aston.controller.PubController;
import com.aston.exception.RequestValidationException;
import com.aston.service.dto.request.BeerRequest;
import com.aston.service.dto.request.PubRequest;
import lombok.experimental.UtilityClass;

/**
 * Утилитарный класс для валидации {@link PubRequest}, {@link BeerRequest}
 *
 * @see PubController
 * @see BeerController
 * @see RequestValidationException
 */
@UtilityClass
public class RequestValidationUtil {

    public void isValid(Object objectRequest) {
        if (!requestIsValid(objectRequest)) {
            throw RequestValidationException.of(objectRequest);
        }
    }

    private boolean requestIsValid(Object objectRequest) {
        return objectRequest instanceof PubRequest ?
                pubRequestIsValid((PubRequest) objectRequest) :
                objectRequest instanceof BeerRequest ?
                        beerRequestIsValid((BeerRequest) objectRequest) :
                        false;
    }

    private boolean pubRequestIsValid(PubRequest pubRequest) {
        var pubAddress = pubRequest.pubAddress();

        return !(pubRequest.name().isBlank() ||
                 pubRequest.description().isBlank() ||
                 pubAddress.getCountry().isBlank() ||
                 pubAddress.getRegion().isBlank() ||
                 pubAddress.getCity().isBlank() ||
                 pubAddress.getStreet().isBlank() ||
                 pubAddress.getNumber().isBlank());
    }

    private boolean beerRequestIsValid(BeerRequest beerRequest) {
        var factoryAddress = beerRequest.factoryAddress();

        return !(beerRequest.name().isBlank() ||
                 beerRequest.description().isBlank() ||
                 beerRequest.factory().isBlank() ||
                 factoryAddress.getCountry().isBlank() ||
                 factoryAddress.getRegion().isBlank() ||
                 factoryAddress.getCity().isBlank() ||
                 factoryAddress.getStreet().isBlank() ||
                 factoryAddress.getNumber().isBlank() ||
                 beerRequest.ABV() == 0.0 ||
                 beerRequest.OG() == 0.0 ||
                 beerRequest.IBU() == 0);
    }
}
