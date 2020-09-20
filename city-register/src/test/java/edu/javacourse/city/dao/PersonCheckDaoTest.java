package edu.javacourse.city.dao;

import edu.javacourse.city.domain.PersonRequest;
import edu.javacourse.city.domain.PersonResponse;
import edu.javacourse.city.exception.PersonCheckException;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class PersonCheckDaoTest {

    @Test
    public void checkPerson() throws PersonCheckException {
        PersonRequest request = new PersonRequest();
        request.setSurName("Петров");
        request.setGivenName("Андрей");
        request.setPatronymic("Сергеевич");
        request.setDateOfBirth(LocalDate.of(1990, 5, 25));
        request.setStreetCode(1);
        request.setBuilding("10");
        request.setExtension("2");
        request.setApartment("121");

        PersonCheckDao checkDao = new PersonCheckDao();
        checkDao.setConnectionBuilder(new DirectConnectionBuilder());
        PersonResponse response = checkDao.checkPerson(request);
        Assert.assertTrue(response.isRegistered());
        Assert.assertFalse(response.isTemporal());
    }

    @Test
    public void checkPerson2() throws PersonCheckException {
        PersonRequest request = new PersonRequest();
        request.setSurName("Петрова");
        request.setGivenName("Ирина");
        request.setPatronymic("Викторовна");
        request.setDateOfBirth(LocalDate.of(1998, 6, 12));
        request.setStreetCode(1);
        request.setBuilding("274");
        request.setApartment("4");

        PersonCheckDao checkDao = new PersonCheckDao();
        checkDao.setConnectionBuilder(new DirectConnectionBuilder());
        PersonResponse response = checkDao.checkPerson(request);
        Assert.assertTrue(response.isRegistered());
        Assert.assertFalse(response.isTemporal());
    }
}