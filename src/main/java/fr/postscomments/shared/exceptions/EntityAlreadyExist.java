package fr.postscomments.shared.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityAlreadyExist extends RuntimeException {
    public EntityAlreadyExist(String message) {
        super(message);
    }
}
