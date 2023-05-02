package fr.postsComments.comments.services;

import fr.postsComments.Shared.EntityNotFoundException;
import fr.postsComments.comments.models.Comment;
import fr.postsComments.comments.repository.ICommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServicesImpl implements ICommentServices {

    private final ICommentRepository commentRepository;

    public CommentServicesImpl(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
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
    public Comment addComment(Comment commentToAdd) {
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
}
