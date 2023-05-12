package fr.postscomments.authentification.validationmail.email;

public interface EmailSender {

    void sendEmail(String to, String email, String subject);
}
