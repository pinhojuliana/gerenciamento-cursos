package com.juliana.gerenciamento_cursos.entity.user;


import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public abstract class UserService<T extends User> {
//ver como coloco isso nas classes filhas
    public void createNewUser(T user) {

    }

    public void deleteUser(T user) {

    }

    /*
    public T verifyExistenceOfUser(String email) throws InexistentOptionException {

    }

    public T verifyExistenceOfUser(UUID id) throws InexistentOptionException {

    }
    */

}
