package edu.unisabana.tyvs.domain.service;

import edu.unisabana.tyvs.domain.model.Gender;
import edu.unisabana.tyvs.domain.model.Person;
import edu.unisabana.tyvs.domain.model.RegisterResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas unitarias para Registry siguiendo TDD (Red-Green-Refactor)
 * y el patrón AAA (Arrange-Act-Assert)
 */
public class RegistryTest {

    private Registry registry;

    @Before
    public void setUp() {
        registry = new Registry();
    }

    // ==================== PRUEBA 1: Camino feliz ====================
    // Given: Una persona válida (30 años, viva, id único)
    // When: Intento registrarla
    // Then: El resultado debe ser VALID
    @Test
    public void shouldRegisterValidPerson() {
        // Arrange: preparar los datos y el objeto a probar
        Person person = new Person("Ana", 1, 30, Gender.FEMALE, true);

        // Act: ejecutar la acción que queremos probar
        RegisterResult result = registry.registerVoter(person);

        // Assert: verificar el resultado esperado
        Assert.assertEquals(RegisterResult.VALID, result);
    }

    // ==================== PRUEBA 2: Persona muerta ====================
    // Given: Una persona muerta
    // When: Intento registrarla
    // Then: El resultado debe ser DEAD
    @Test
    public void shouldRejectDeadPerson() {
        // Arrange: preparar los datos y el objeto a probar
        Person deadPerson = new Person("Carlos", 2, 40, Gender.MALE, false);

        // Act: ejecutar la acción que queremos probar
        RegisterResult result = registry.registerVoter(deadPerson);

        // Assert: verificar el resultado esperado
        Assert.assertEquals(RegisterResult.DEAD, result);
    }

    // ==================== PRUEBA 3: Persona null ====================
    // Given: La persona es null
    // When: Intento registrarla
    // Then: El resultado debe ser INVALID
    @Test
    public void shouldReturnInvalidWhenPersonIsNull() {
        // Arrange: preparar persona null
        Person person = null;

        // Act: ejecutar la acción que queremos probar
        RegisterResult result = registry.registerVoter(person);

        // Assert: verificar el resultado esperado
        Assert.assertEquals(RegisterResult.INVALID, result);
    }

    // ==================== PRUEBA 4: Edad menor de 18 (límite 17) ====================
    // Given: Una persona de 17 años, viva y con id válido
    // When: Intento registrarla
    // Then: El resultado debe ser UNDERAGE
    @Test
    public void shouldRejectUnderageAt17() {
        // Arrange: preparar persona menor de edad
        Person underagePerson = new Person("Joven", 3, 17, Gender.MALE, true);

        // Act: ejecutar la acción que queremos probar
        RegisterResult result = registry.registerVoter(underagePerson);

        // Assert: verificar el resultado esperado
        Assert.assertEquals(RegisterResult.UNDERAGE, result);
    }

    // ==================== PRUEBA 5: Edad exactamente 18 (límite inferior válido) ====================
    // Given: Una persona de exactamente 18 años, viva y con id válido
    // When: Intento registrarla
    // Then: El resultado debe ser VALID
    @Test
    public void shouldAcceptAdultAt18() {
        // Arrange: preparar persona en el límite válido
        Person adultPerson = new Person("Adulto", 4, 18, Gender.FEMALE, true);

        // Act: ejecutar la acción que queremos probar
        RegisterResult result = registry.registerVoter(adultPerson);

        // Assert: verificar el resultado esperado
        Assert.assertEquals(RegisterResult.VALID, result);
    }

    // ==================== PRUEBA 6: Edad negativa ====================
    // Given: Una persona con edad negativa (-1)
    // When: Intento registrarla
    // Then: El resultado debe ser INVALID_AGE
    @Test
    public void shouldRejectInvalidAgeNegative() {
        // Arrange: preparar persona con edad inválida
        Person invalidAgePerson = new Person("Invalido", 5, -1, Gender.UNIDENTIFIED, true);

        // Act: ejecutar la acción que queremos probar
        RegisterResult result = registry.registerVoter(invalidAgePerson);

        // Assert: verificar el resultado esperado
        Assert.assertEquals(RegisterResult.INVALID_AGE, result);
    }

    // ==================== PRUEBA 7: Edad máxima válida 120 ====================
    // Given: Una persona de 120 años, viva y con id válido
    // When: Intento registrarla
    // Then: El resultado debe ser VALID
    @Test
    public void shouldAcceptMaxAge120() {
        // Arrange: preparar persona en el límite máximo válido
        Person oldPerson = new Person("Centenario", 6, 120, Gender.MALE, true);

        // Act: ejecutar la acción que queremos probar
        RegisterResult result = registry.registerVoter(oldPerson);

        // Assert: verificar el resultado esperado
        Assert.assertEquals(RegisterResult.VALID, result);
    }

    // ==================== PRUEBA 8: Edad mayor a 120 ====================
    // Given: Una persona con edad mayor a 120 años (121)
    // When: Intento registrarla
    // Then: El resultado debe ser INVALID_AGE
    @Test
    public void shouldRejectInvalidAgeOver120() {
        // Arrange: preparar persona con edad sobre el límite
        Person tooOldPerson = new Person("Matusalén", 7, 121, Gender.FEMALE, true);

        // Act: ejecutar la acción que queremos probar
        RegisterResult result = registry.registerVoter(tooOldPerson);

        // Assert: verificar el resultado esperado
        Assert.assertEquals(RegisterResult.INVALID_AGE, result);
    }

    // ==================== PRUEBA 9: ID inválido (0 o negativo) ====================
    // Given: Una persona con id = 0, edad válida y viva
    // When: Intento registrarla
    // Then: El resultado debe ser INVALID
    @Test
    public void shouldRejectWhenIdIsZero() {
        // Arrange: preparar persona con id inválido
        Person invalidIdPerson = new Person("Sin ID", 0, 25, Gender.MALE, true);

        // Act: ejecutar la acción que queremos probar
        RegisterResult result = registry.registerVoter(invalidIdPerson);

        // Assert: verificar el resultado esperado
        Assert.assertEquals(RegisterResult.INVALID, result);
    }

    // ==================== PRUEBA 10: ID negativo ====================
    // Given: Una persona con id negativo, edad válida y viva
    // When: Intento registrarla
    // Then: El resultado debe ser INVALID
    @Test
    public void shouldRejectWhenIdIsNegative() {
        // Arrange: preparar persona con id negativo
        Person negativeIdPerson = new Person("ID Negativo", -5, 30, Gender.FEMALE, true);

        // Act: ejecutar la acción que queremos probar
        RegisterResult result = registry.registerVoter(negativeIdPerson);

        // Assert: verificar el resultado esperado
        Assert.assertEquals(RegisterResult.INVALID, result);
    }

    // ==================== PRUEBA 11: Registro duplicado ====================
    // Given: Una persona ya registrada con un id específico
    // When: Intento registrar otra persona con el mismo id
    // Then: El resultado debe ser DUPLICATED para la segunda persona
    @Test
    public void shouldRejectDuplicateId() {
        // Arrange: preparar dos personas con el mismo id
        Person person1 = new Person("Pedro", 100, 25, Gender.MALE, true);
        Person person2 = new Person("Pablo", 100, 30, Gender.MALE, true);

        // Act: registrar la primera persona (debe ser válido)
        RegisterResult result1 = registry.registerVoter(person1);
        // Intentar registrar la segunda persona con el mismo id
        RegisterResult result2 = registry.registerVoter(person2);

        // Assert: verificar que la primera es válida y la segunda es duplicada
        Assert.assertEquals(RegisterResult.VALID, result1);
        Assert.assertEquals(RegisterResult.DUPLICATED, result2);
    }
}
