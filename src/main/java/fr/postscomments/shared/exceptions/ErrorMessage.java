package fr.postscomments.shared.exceptions;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ErrorMessage {
    String message;
    Date timestamp;
    Integer code;
}
