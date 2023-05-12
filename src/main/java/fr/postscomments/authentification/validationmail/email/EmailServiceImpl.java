package fr.postscomments.authentification.validationmail.email;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public String buildEmail(String name, String link) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/templates/email-template.html"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            String html = sb.toString();
            // remplacer les variables dans le modèle HTML
            html = html.replace("$NAME", name).replace("$LINK", link);
            return html;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
