package com.aston.util;

import com.aston.Main;
import com.aston.constant.Constant;
import com.aston.controller.BeerController;
import com.aston.controller.PubController;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

import static com.aston.constant.Constant.BEER_CONTROLLER_NAME;
import static com.aston.constant.Constant.BEER_SERVLET_PATTERN;
import static com.aston.constant.Constant.PUB_CONTROLLER_NAME;
import static com.aston.constant.Constant.PUB_SERVLET_PATTERN;

/**
 * Утилитарный класс для запуска сервера Apache Tomcat Embed
 *
 * @see Main
 * @see PubController
 * @see BeerController
 * @see Constant
 */
@UtilityClass
public class ApacheTomcatEmbedUtil {

    @SneakyThrows
    public void start() {
        var tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        var context = getContext(tomcat);

        Tomcat.addServlet(context, PUB_CONTROLLER_NAME, new PubController());
        context.addServletMappingDecoded(PUB_SERVLET_PATTERN, PUB_CONTROLLER_NAME);

        Tomcat.addServlet(context, BEER_CONTROLLER_NAME, new BeerController());
        context.addServletMappingDecoded(BEER_SERVLET_PATTERN, BEER_CONTROLLER_NAME);

        tomcat.start();
        tomcat.getServer().await();
    }

    private Context getContext(Tomcat tomcat) {
        return tomcat.addContext("", new File(".").getAbsolutePath());
    }
}
