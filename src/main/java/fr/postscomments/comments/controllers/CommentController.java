package fr.postscomments.comments.controllers;

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

    @GetMapping("")
    public ResponseEntity<List<Comment>> getPosts() {
        return new ResponseEntity<>(commentServices.findAllComment(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Comment> getPostsById(@PathVariable() Long id) {
        return new ResponseEntity<>(commentServices.findCommentById(id), HttpStatus.OK);
    }

    @PostMapping("/addComment")
    public ResponseEntity<Comment> createPost(@Valid @RequestBody Comment commentToAdd) {
        return new ResponseEntity<>(commentServices.addComment(commentToAdd), HttpStatus.CREATED);
    }

    @PutMapping("/updateComment")
    public ResponseEntity<Comment> updatePost(@Valid @RequestBody Comment commentUpdated) {
        return new ResponseEntity<>(commentServices.updateComment(commentUpdated), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable() Long id) {
        commentServices.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
