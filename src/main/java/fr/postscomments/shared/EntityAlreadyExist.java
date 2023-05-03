package fr.postscomments.shared;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityAlreadyExist extends RuntimeException {
    public EntityAlreadyExist(String message) {
        super(message);
    }
}
