package com.aston.service.dto.request;

import com.aston.dao.entity.Address;
import com.aston.dao.entity.Pub;
import lombok.Builder;

import java.io.Serializable;

/**
 * DTO для {@link Pub}
 *
 * @see Address
 */
public record PubRequest(

        String name,
        String description,
        Address pubAddress
) implements Serializable {
    @Builder public PubRequest {}
}
