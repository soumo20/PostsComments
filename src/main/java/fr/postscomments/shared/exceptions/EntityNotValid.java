package fr.postscomments.shared.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityNotValid extends RuntimeException {
    public EntityNotValid(String message) {
        super(message);
    }

}
