package admin.controller;

import org.apache.commons.lang3.StringUtils;
import admin.service.SendService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/city")
public class CityServlet extends HttpServlet {

    private SendService sendService;

    @Inject
    public CityServlet(SendService sendService) {
        this.sendService = sendService;
    }

    public CityServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/web/index.jsp")
                .forward(req, resp);
        String city = req.getParameter("city");
        if (StringUtils.isNoneBlank(city)) {
            sendService.send(city);
        }
    }
}
