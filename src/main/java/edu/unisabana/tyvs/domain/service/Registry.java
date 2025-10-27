package edu.unisabana.tyvs.domain.service;

import edu.unisabana.tyvs.domain.model.Person;
import edu.unisabana.tyvs.domain.model.RegisterResult;
import java.util.HashSet;
import java.util.Set;

public class Registry {
    
    private static final int MIN_VOTING_AGE = 18;
    private static final int MAX_VALID_AGE = 120;
    
    private Set<Integer> registeredIds;
    
    public Registry() {
        this.registeredIds = new HashSet<>();
    }

    public RegisterResult registerVoter(Person p) {
        //Persona null
        if (p == null) {
            return RegisterResult.INVALID;
        }
        
        //ID inválido (0 o negativo)
        if (p.getId() <= 0) {
            return RegisterResult.INVALID;
        }
        
        //Edad inválida (negativa o mayor a 120)
        if (p.getAge() < 0 || p.getAge() > MAX_VALID_AGE) {
            return RegisterResult.INVALID_AGE;
        }
        
        //Persona muerta
        if (!p.isAlive()) {
            return RegisterResult.DEAD;
        }
        
        //Menor de edad
        if (p.getAge() < MIN_VOTING_AGE) {
            return RegisterResult.UNDERAGE;
        }
        
        //ID duplicado
        if (registeredIds.contains(p.getId())) {
            return RegisterResult.DUPLICATED;
        }
        
        registeredIds.add(p.getId());
        return RegisterResult.VALID;
    }
}
