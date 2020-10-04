package com.ibm.logAggregation.feed.service;

import java.util.List;
import java.util.Optional;

import com.ibm.logAggregation.feed.domain.FeedWithComment;
import com.ibm.logAggregation.feed.domain.Feeds;

public interface FeedService {

	List<Feeds> getFeeds();
	
	Optional<FeedWithComment> getFeed(Long id);
}
