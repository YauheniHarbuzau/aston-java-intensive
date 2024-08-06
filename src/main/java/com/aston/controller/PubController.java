package com.aston.controller;

import com.aston.dao.entity.Address;
import com.aston.dao.entity.Pub;
import com.aston.service.PubService;
import com.aston.service.dto.request.PubRequest;
import com.aston.service.impl.PubServiceImpl;
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
 * Контроллер для работы с {@link Pub}
 *
 * @see PubService
 * @see PubServiceImpl
 * @see RequestValidationUtil
 */
@WebServlet
public class PubController extends HttpServlet {

    private PubService pubService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.pubService = new PubServiceImpl();
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
                .writeValueAsString(pubService.getByUuid(uuid));

        getResponseParams(resp, HttpServletResponse.SC_OK, request);
    }

    private void getAll(HttpServletResponse resp) throws IOException {
        var request = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(pubService.getAll());

        getResponseParams(resp, HttpServletResponse.SC_OK, request);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var pubToCreate = PubRequest.builder()
                .name(req.getParameter("name"))
                .description(req.getParameter("description"))
                .pubAddress(Address.builder()
                        .country(req.getParameter("pubAddressCountry"))
                        .region(req.getParameter("pubAddressRegion"))
                        .city(req.getParameter("pubAddressCity"))
                        .street(req.getParameter("pubAddressStreet"))
                        .number(req.getParameter("pubAddressNumber"))
                        .build())
                .build();

        RequestValidationUtil.isValid(pubToCreate);

        var request = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(pubService.create(pubToCreate));

        getResponseParams(resp, HttpServletResponse.SC_CREATED, request);
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var uuidToUpdate = UUID.fromString(req.getParameter("uuid"));
        var pubToUpdate = PubRequest.builder()
                .name(req.getParameter("name"))
                .description(req.getParameter("description"))
                .pubAddress(Address.builder()
                        .country(req.getParameter("pubAddressCountry"))
                        .region(req.getParameter("pubAddressRegion"))
                        .city(req.getParameter("pubAddressCity"))
                        .street(req.getParameter("pubAddressStreet"))
                        .number(req.getParameter("pubAddressNumber"))
                        .build())
                .build();

        RequestValidationUtil.isValid(pubToUpdate);

        var request = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(pubService.update(uuidToUpdate, pubToUpdate));

        getResponseParams(resp, HttpServletResponse.SC_OK, request);
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var uuidToDelete = UUID.fromString(req.getParameter("uuid"));

        pubService.deleteByUuid(uuidToDelete);

        getResponseParams(resp, HttpServletResponse.SC_NO_CONTENT, "Delete by UUID " + uuidToDelete + " successfully");
    }

    private void getResponseParams(HttpServletResponse resp, int status, String req) throws IOException {
        resp.setStatus(status);
        resp.setHeader("Content-Type", "application/json");
        resp.getOutputStream().println(req);
    }
}
