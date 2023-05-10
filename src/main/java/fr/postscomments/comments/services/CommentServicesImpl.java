package fr.postscomments.comments.services;

import fr.postscomments.authentification.models.UserApp;
import fr.postscomments.authentification.security.services.userServices.UserServices;
import fr.postscomments.comments.dto.CommentUpdateDto;
import fr.postscomments.comments.dto.CommentDto;
import fr.postscomments.comments.models.Comment;
import fr.postscomments.comments.repository.CommentRepository;
import fr.postscomments.posts.models.Post;
import fr.postscomments.posts.repository.PostRepository;
import fr.postscomments.shared.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServicesImpl implements CommentServices {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;


    private final UserServices userServices;

    public CommentServicesImpl(CommentRepository commentRepository, PostRepository postRepository, UserServices userServices) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userServices = userServices;
    }


    @Override
    public Comment findCommentById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("The id must not be null");
        }
        return commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No comment founded with the given id " + id));
    }

    @Override
    public Comment addComment(CommentDto commentToAdd, Long idPost) {
        Post post = postRepository.findById(idPost).orElseThrow(() -> new EntityNotFoundException("No post founded with the given id " + idPost));
        UserApp user = userServices.findUserConnected();
        Comment comment = Comment.builder().content(commentToAdd.getContent()).post(post).author(user).build();
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(CommentUpdateDto commentUpdateDto) {
        Comment commentOld = commentRepository.findById(commentUpdateDto.getId()).orElseThrow(() -> new EntityNotFoundException("No such comment with the given id "+commentUpdateDto.getId()));
        Comment commentUpdated = Comment.builder()
                .id(commentUpdateDto.getId())
                .content(commentUpdateDto.getContent())
                .author(commentOld.getAuthor())
                .post(commentOld.getPost())
                .build();
        return commentRepository.save(commentUpdated);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> findAllCommentsOfOnePost(Long idPost) {
        if (idPost == null) {
            throw new IllegalArgumentException("The id of the post must not be null");
        }
        if (!postRepository.existPostById(idPost)) {
            throw new EntityNotFoundException("No post founded with the given id " + idPost);
        }
        return commentRepository.findAllCommentsOfOnePost(idPost);
    }
}
