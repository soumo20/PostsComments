package fr.postscomments.comments.services;

import fr.postscomments.authentification.models.UserApp;
import fr.postscomments.authentification.repository.IUserRepository;
import fr.postscomments.authentification.security.services.UserDetailsImpl;
import fr.postscomments.authentification.security.services.UserServices;
import fr.postscomments.comments.dto.CommentDto;
import fr.postscomments.comments.models.Comment;
import fr.postscomments.comments.repository.ICommentRepository;
import fr.postscomments.posts.models.Post;
import fr.postscomments.posts.repository.IPostRepository;
import fr.postscomments.shared.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServicesImpl implements ICommentServices {

    private final ICommentRepository commentRepository;

    private final IPostRepository postRepository;

    private final IUserRepository userRepository;

    private final UserServices userServices;

    public CommentServicesImpl(ICommentRepository commentRepository, IPostRepository postRepository, IUserRepository userRepository, UserServices userServices) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
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
    public Comment updateComment(Comment commentUpdated) {
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
