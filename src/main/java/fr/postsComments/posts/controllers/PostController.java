package fr.postsComments.posts.controllers;

import fr.postsComments.posts.models.Post;
import fr.postsComments.posts.services.IPostServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final IPostServices postServices;


    public PostController(IPostServices postServices) {
        this.postServices = postServices;
    }


    @GetMapping("")
    public ResponseEntity<List<Post>> getPosts() {
        return new ResponseEntity<>(postServices.findAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Post> getPostsById(@PathVariable() Long id) {
        return new ResponseEntity<>(postServices.findPostById(id), HttpStatus.OK);
    }

    @PostMapping("/createPost")
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post postToCreate) {
        return new ResponseEntity<>(postServices.addPost(postToCreate), HttpStatus.CREATED);
    }

    @PutMapping("/updatePost")
    public ResponseEntity<Post> updatePost(@Valid @RequestBody Post postUpdated) {
        return new ResponseEntity<>(postServices.updatePost(postUpdated), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable() Long id) {
        postServices.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}