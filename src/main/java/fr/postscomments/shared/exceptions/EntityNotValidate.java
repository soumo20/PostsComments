package fr.postscomments.shared.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityNotValidate extends RuntimeException {
    public EntityNotValidate(String message) {
        super(message);
    }
}
