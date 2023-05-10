package fr.postscomments.authentification.validationmail.email;

public interface EmailService {

    public String buildEmail(String name, String link);

    public boolean test(String email);
}
