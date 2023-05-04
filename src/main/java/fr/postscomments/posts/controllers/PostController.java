package fr.postscomments.posts.controllers;

import fr.postscomments.posts.dto.PostDto;
import fr.postscomments.posts.models.Post;
import fr.postscomments.posts.services.PostServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostServices postServices;


    public PostController(PostServices postServices) {
        this.postServices = postServices;
    }


    @GetMapping("")
    public ResponseEntity<List<Post>> getPosts() {
        return new ResponseEntity<>(postServices.findAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Post> getPostsById(@PathVariable() Long id) {
        return new ResponseEntity<>(postServices.findPostById(id), HttpStatus.FOUND);
    }

    @PostMapping("/createPost")
    public ResponseEntity<Post> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postServices.addPost(postDto), HttpStatus.CREATED);
    }

    @PutMapping("/updatePost")
    public ResponseEntity<Post> updatePost(@Valid @RequestBody Post postUpdated) {
        return new ResponseEntity<>(postServices.updatePost(postUpdated), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable() Long id) {
        postServices.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}