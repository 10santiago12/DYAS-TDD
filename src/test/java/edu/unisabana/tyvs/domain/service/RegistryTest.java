package edu.unisabana.tyvs.domain.service;

import edu.unisabana.tyvs.domain.model.Gender;
import edu.unisabana.tyvs.domain.model.Person;
import edu.unisabana.tyvs.domain.model.RegisterResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RegistryTest {

    private Registry registry;

    @Before
    public void setUp() {
        registry = new Registry();
    }

    @Test
    public void shouldRegisterValidPerson() {
        Person person = new Person("Ana", 1, 30, Gender.FEMALE, true);

        RegisterResult result = registry.registerVoter(person);

        Assert.assertEquals(RegisterResult.VALID, result);
    }

    @Test
    public void shouldRejectDeadPerson() {
        Person deadPerson = new Person("Carlos", 2, 40, Gender.MALE, false);

        RegisterResult result = registry.registerVoter(deadPerson);

        Assert.assertEquals(RegisterResult.DEAD, result);
    }

    @Test
    public void shouldReturnInvalidWhenPersonIsNull() {
        Person person = null;

        RegisterResult result = registry.registerVoter(person);

        Assert.assertEquals(RegisterResult.INVALID, result);
    }

    @Test
    public void shouldRejectUnderageAt17() {
        Person underagePerson = new Person("Joven", 3, 17, Gender.MALE, true);

        RegisterResult result = registry.registerVoter(underagePerson);

        Assert.assertEquals(RegisterResult.UNDERAGE, result);
    }

    @Test
    public void shouldAcceptAdultAt18() {
        Person adultPerson = new Person("Adulto", 4, 18, Gender.FEMALE, true);

        RegisterResult result = registry.registerVoter(adultPerson);

        Assert.assertEquals(RegisterResult.VALID, result);
    }

    @Test
    public void shouldRejectInvalidAgeNegative() {
        Person invalidAgePerson = new Person("Invalido", 5, -1, Gender.UNIDENTIFIED, true);

        RegisterResult result = registry.registerVoter(invalidAgePerson);

        Assert.assertEquals(RegisterResult.INVALID_AGE, result);
    }

    @Test
    public void shouldAcceptMaxAge120() {
        Person oldPerson = new Person("Centenario", 6, 120, Gender.MALE, true);

        RegisterResult result = registry.registerVoter(oldPerson);

        Assert.assertEquals(RegisterResult.VALID, result);
    }

    @Test
    public void shouldRejectInvalidAgeOver120() {
        Person tooOldPerson = new Person("Matusalén", 7, 121, Gender.FEMALE, true);

        RegisterResult result = registry.registerVoter(tooOldPerson);

        Assert.assertEquals(RegisterResult.INVALID_AGE, result);
    }

    @Test
    public void shouldRejectWhenIdIsZero() {
        Person invalidIdPerson = new Person("Sin ID", 0, 25, Gender.MALE, true);

        RegisterResult result = registry.registerVoter(invalidIdPerson);

        Assert.assertEquals(RegisterResult.INVALID, result);
    }

    @Test
    public void shouldRejectWhenIdIsNegative() {
        Person negativeIdPerson = new Person("ID Negativo", -5, 30, Gender.FEMALE, true);

        RegisterResult result = registry.registerVoter(negativeIdPerson);

        Assert.assertEquals(RegisterResult.INVALID, result);
    }

    @Test
    public void shouldRejectDuplicateId() {
        Person person1 = new Person("Pedro", 100, 25, Gender.MALE, true);
        Person person2 = new Person("Pablo", 100, 30, Gender.MALE, true);

        RegisterResult result1 = registry.registerVoter(person1);
        RegisterResult result2 = registry.registerVoter(person2);

        Assert.assertEquals(RegisterResult.VALID, result1);
        Assert.assertEquals(RegisterResult.DUPLICATED, result2);
    }
}
