package edu.javacourse.city.web;

import edu.javacourse.city.dao.PersonCheckDao;
import edu.javacourse.city.dao.PoolConnectionBuilder;
import edu.javacourse.city.domain.PersonRequest;
import edu.javacourse.city.domain.PersonResponse;
import edu.javacourse.city.exception.PersonCheckException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "CheckPersonServlet", urlPatterns = {"/checkPerson"})
public class CheckPersonServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(CheckPersonServlet.class);
    private PersonCheckDao checkDao;

    @Override
    public void init() throws ServletException {
        logger.info("Servlet is created.");
        checkDao = new PersonCheckDao();
        checkDao.setConnectionBuilder(new PoolConnectionBuilder());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        req.setCharacterEncoding("UTF-8");

        PersonRequest personRequest = new PersonRequest();
        personRequest.setSurName(req.getParameter("surname"));
        personRequest.setGivenName(req.getParameter("givenname"));
        personRequest.setPatronymic(req.getParameter("patronymic"));
        LocalDate dateOfBirth = LocalDate.parse(req.getParameter("dateOfBirth"), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        personRequest.setDateOfBirth(dateOfBirth);
        personRequest.setStreetCode(Integer.parseInt(req.getParameter("streetCode")));
        personRequest.setBuilding(req.getParameter("building"));
        personRequest.setExtension(req.getParameter("extension"));
        personRequest.setApartment(req.getParameter("apartment"));


        try {
            PersonResponse personResponse = checkDao.checkPerson(personRequest);
            if(personResponse.isRegistered()){
                resp.getWriter().write("Registered.");
            } else {
                resp.getWriter().write("Not registered.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
