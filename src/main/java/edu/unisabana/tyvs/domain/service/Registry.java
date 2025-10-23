package edu.unisabana.tyvs.domain.service;

import edu.unisabana.tyvs.domain.model.Person;
import edu.unisabana.tyvs.domain.model.RegisterResult;
import java.util.HashSet;
import java.util.Set;

public class Registry {
    
    // Constantes para edad válida
    private static final int MIN_VOTING_AGE = 18;
    private static final int MAX_VALID_AGE = 120;
    
    // Almacenar IDs ya registrados para detectar duplicados
    private Set<Integer> registeredIds;
    
    public Registry() {
        this.registeredIds = new HashSet<>();
    }

    public RegisterResult registerVoter(Person p) {
        // Validación 1: Persona null
        if (p == null) {
            return RegisterResult.INVALID;
        }
        
        // Validación 2: ID inválido (0 o negativo)
        if (p.getId() <= 0) {
            return RegisterResult.INVALID;
        }
        
        // Validación 3: Edad inválida (negativa o mayor a 120)
        if (p.getAge() < 0 || p.getAge() > MAX_VALID_AGE) {
            return RegisterResult.INVALID_AGE;
        }
        
        // Validación 4: Persona muerta
        if (!p.isAlive()) {
            return RegisterResult.DEAD;
        }
        
        // Validación 5: Menor de edad
        if (p.getAge() < MIN_VOTING_AGE) {
            return RegisterResult.UNDERAGE;
        }
        
        // Validación 6: ID duplicado
        if (registeredIds.contains(p.getId())) {
            return RegisterResult.DUPLICATED;
        }
        
        // Si todas las validaciones pasan, registrar el ID y retornar VALID
        registeredIds.add(p.getId());
        return RegisterResult.VALID;
    }
}
