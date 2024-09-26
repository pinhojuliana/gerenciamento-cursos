package com.juliana.gerenciamento_cursos.entity.user;


import com.juliana.gerenciamento_cursos.exceptions.InexistentOptionException;

import java.util.ArrayList;
import java.util.List;

public abstract class UserService<T extends User> {
    protected List<T> users;

    public UserService(){
        this.users = new ArrayList<>();
    }

    public void createNewUser(T user) {
            users.add(user);
    }

    public T verifyExistenceOfUser(String email) throws InexistentOptionException {
        return users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new InexistentOptionException("Usuário não cadastrado"));
    }
}
