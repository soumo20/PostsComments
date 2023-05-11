package fr.postscomments.pdf.controllers;

import fr.postscomments.pdf.models.PdfGenerator;
import fr.postscomments.posts.models.Post;
import fr.postscomments.posts.services.PostServices;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/api/v1/posts")
public class PdfController {

    private final PostServices postServices;

    public PdfController(PostServices postServices) {
        this.postServices = postServices;
    }

    @GetMapping("/{id}/export-pdf")
    public void generatePdfFile(HttpServletResponse response, @PathVariable Long id) {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD:HH:MM:SS");
        String currentDateTime = dateFormat.format(new Date());
        String headerkey = "Content-Disposition";
        String headervalue = "attachment; filename=student" + currentDateTime + ".pdf";
        response.setHeader(headerkey, headervalue);
        Post post = postServices.findPostById(id);
        PdfGenerator generator = new PdfGenerator();
        generator.generatorPdf(post, response);
    }

}
