package com.ibm.logAggregation.comment.service;

import java.util.List;

import com.ibm.logAggregation.comment.domain.Comment;

public interface CommentService {

	List<Comment> getCommentsForFeed(Long feedId);
}
