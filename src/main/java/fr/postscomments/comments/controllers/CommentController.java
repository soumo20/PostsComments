package fr.postscomments.comments.controllers;

import fr.postscomments.comments.dto.CommentDto;
import fr.postscomments.comments.models.Comment;
import fr.postscomments.comments.services.ICommentServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final ICommentServices commentServices;

    public CommentController(ICommentServices commentServices) {
        this.commentServices = commentServices;
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<List<Comment>> getCommentsOfSpecPost(@PathVariable() Long idPost) {
        return new ResponseEntity<>(commentServices.findAllCommentsOfOnePost(idPost), HttpStatus.FOUND);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable() Long id) {
        return new ResponseEntity<>(commentServices.findCommentById(id), HttpStatus.OK);
    }

    @PostMapping("/addComment/{id}")
    public ResponseEntity<Comment> addCommentToPost(@Valid @RequestBody CommentDto comment, @PathVariable Long id) {
        System.out.println(id);
        return new ResponseEntity<>(commentServices.addComment(comment, id), HttpStatus.CREATED);
    }

    @PutMapping("/updateComment")
    public ResponseEntity<Comment> updatePost(@Valid @RequestBody Comment commentUpdated) {
        return new ResponseEntity<>(commentServices.updateComment(commentUpdated), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable() Long id) {
        commentServices.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
