package com.aston.controller;

import com.aston.dao.entity.Address;
import com.aston.dao.entity.Beer;
import com.aston.service.BeerService;
import com.aston.service.dto.request.BeerRequest;
import com.aston.service.impl.BeerServiceImpl;
import com.aston.util.RequestValidationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

/**
 * Контроллер для работы с {@link Beer}
 *
 * @see BeerService
 * @see BeerServiceImpl
 * @see RequestValidationUtil
 */
@WebServlet
public class BeerController extends HttpServlet {

    private BeerService beerService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.beerService = new BeerServiceImpl();
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var uuid = req.getParameter("uuid");

        if (uuid != null) {
            getByUuid(resp, UUID.fromString(uuid));
        } else {
            getAll(resp);
        }
    }

    private void getByUuid(HttpServletResponse resp, UUID uuid) throws IOException {
        var request = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(beerService.getByUuid(uuid));

        getResponseParams(resp, HttpServletResponse.SC_OK, request);
    }

    private void getAll(HttpServletResponse resp) throws IOException {
        var request = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(beerService.getAll());

        getResponseParams(resp, HttpServletResponse.SC_OK, request);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var beerToCreate = BeerRequest.builder()
                .name(req.getParameter("name"))
                .description(req.getParameter("description"))
                .factory(req.getParameter("factory"))
                .factoryAddress(Address.builder()
                        .country(req.getParameter("factoryAddressCountry"))
                        .region(req.getParameter("factoryAddressRegion"))
                        .city(req.getParameter("factoryAddressCity"))
                        .street(req.getParameter("factoryAddressStreet"))
                        .number(req.getParameter("factoryAddressNumber"))
                        .build())
                .ABV(Double.valueOf(req.getParameter("abv")))
                .OG(Double.valueOf(req.getParameter("og")))
                .IBU(Integer.valueOf(req.getParameter("ibu")))
                .build();

        RequestValidationUtil.isValid(beerToCreate);

        var request = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(beerService.create(beerToCreate));

        getResponseParams(resp, HttpServletResponse.SC_CREATED, request);
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var uuidToUpdate = UUID.fromString(req.getParameter("uuid"));
        var beerToUpdate = BeerRequest.builder()
                .name(req.getParameter("name"))
                .description(req.getParameter("description"))
                .factory(req.getParameter("factory"))
                .factoryAddress(Address.builder()
                        .country(req.getParameter("factoryAddressCountry"))
                        .region(req.getParameter("factoryAddressRegion"))
                        .city(req.getParameter("factoryAddressCity"))
                        .street(req.getParameter("factoryAddressStreet"))
                        .number(req.getParameter("factoryAddressNumber"))
                        .build())
                .ABV(Double.valueOf(req.getParameter("abv")))
                .OG(Double.valueOf(req.getParameter("og")))
                .IBU(Integer.valueOf(req.getParameter("ibu")))
                .build();

        RequestValidationUtil.isValid(beerToUpdate);

        var request = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(beerService.update(uuidToUpdate, beerToUpdate));

        getResponseParams(resp, HttpServletResponse.SC_OK, request);
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var uuidToDelete = UUID.fromString(req.getParameter("uuid"));

        beerService.deleteByUuid(uuidToDelete);

        getResponseParams(resp, HttpServletResponse.SC_NO_CONTENT, "Delete by UUID " + uuidToDelete + " successfully");
    }

    private void getResponseParams(HttpServletResponse resp, int status, String req) throws IOException {
        resp.setStatus(status);
        resp.setHeader("Content-Type", "application/json");
        resp.getOutputStream().println(req);
    }
}
