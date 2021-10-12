package com.privateadvertisements.dao;

import com.privateadvertisements.api.dao.IAbstractDao;
import com.privateadvertisements.api.dataJpa.CrudComment;
import com.privateadvertisements.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDao implements IAbstractDao<Comment> {

    private final CrudComment commentDao;

    public CommentDao(CrudComment commentDao) {
        this.commentDao = commentDao;
    }


    @Override
    public Comment save(Comment comment) {
        return commentDao.save(comment);
    }

    @Override
    public void delete(Integer id) {
        commentDao.deleteById(id);
    }

    @Override
    public Comment get(Integer id) {
        return commentDao.getById(id);
    }

    @Override
    public List<Comment> getAll() {
        return commentDao.findAll();
    }
}
