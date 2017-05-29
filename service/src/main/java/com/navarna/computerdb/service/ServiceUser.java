package com.navarna.computerdb.service;

import java.util.Optional;

import com.navarna.computerdb.model.User;

public interface ServiceUser {

    /**
     * Ecris une requête à la base de donnée afin d'insérer un computer.
     * @param user : élément à inserer
     * @return boolean : si oui ou non la base de donnée à été modifié par la
     *         requête
     */
    boolean insert(User user);

    /**
     * Ecris une requête à la base de donnée afin de supprimer un computer.
     * @param name : nom de l'élément à supprimer
     * @return boolean : si oui ou non la base de donnée à été modifié par la
     *         requête
     */
    boolean delete(String name);

    /**
     * Ecris une requête à la base de donnée afin d'avoir les détails de user.
     * @param name : nom du computer
     * @return Optional<user> : l user si il existe
     */
    Optional<User> findByName(String name);
}
