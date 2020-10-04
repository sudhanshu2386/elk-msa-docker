package com.ibm.logAggregation.comment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ibm.logAggregation.comment.domain.Comment;
import com.ibm.logAggregation.comment.service.CommentService;

import lombok.extern.slf4j.Slf4j;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

	private static List<Comment> COMMENTS = new ArrayList<>();

	static {

		COMMENTS.add(Comment.builder().id(1L).feedId(1L).content("Awesome!").build());

		COMMENTS.add(Comment.builder().id(2L).feedId(1L).content("Perfect!").build());

		COMMENTS.add(Comment.builder().id(3L).feedId(2L).content("Best post ever!").build());
	}

	@Override
	public List<Comment> getCommentsForFeed(Long feedId) {

		log.info("Finding comments of feed with id {}", feedId);

		List<Comment> comments = COMMENTS.stream().filter(comment -> comment.getFeedId().equals(feedId))
				.collect(toList());

		log.info("Found {} comment(s) of feed with id {}", comments.size(), feedId);
		return comments;
	}
}
