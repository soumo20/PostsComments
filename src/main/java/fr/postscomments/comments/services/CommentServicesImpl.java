package fr.postscomments.comments.services;

import fr.postscomments.comments.models.Comment;
import fr.postscomments.comments.repository.ICommentRepository;
import fr.postscomments.posts.repository.IPostRepository;
import fr.postscomments.shared.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServicesImpl implements ICommentServices {

    private final ICommentRepository commentRepository;

    private final IPostRepository postRepository;

    public CommentServicesImpl(ICommentRepository commentRepository, IPostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public List<Comment> findAllComment() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findCommentById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("The id must not be null");
        }
        return commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No comment founded with the given id " + id));
    }

    @Override
    public Comment addComment(Comment commentToAdd, Long idPost) {
        if (idPost == null) {
            throw new IllegalArgumentException("The id of the post must not be null");
        }
        commentToAdd.setPost(postRepository.findById(idPost).orElseThrow(() -> new EntityNotFoundException("No post founded with the given id " + idPost)));
        return commentRepository.save(commentToAdd);
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
