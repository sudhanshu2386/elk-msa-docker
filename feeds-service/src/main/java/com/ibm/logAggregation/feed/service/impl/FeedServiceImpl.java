package com.ibm.logAggregation.feed.service.impl;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ibm.logAggregation.feed.domain.Comment;
import com.ibm.logAggregation.feed.domain.FeedWithComment;
import com.ibm.logAggregation.feed.domain.Feeds;
import com.ibm.logAggregation.feed.service.FeedService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

	@Value("${comment-service.base-url}")
	private String commentServiceBaseUrl;

	@Autowired
	private final RestTemplate restTemplate;

	private static final List<Feeds> FEEDS = new ArrayList<>();

	static {

		FEEDS.add(Feeds.builder().id(1L).title("Post 1").content("Post 1 content")
				.publishDateTime(OffsetDateTime.now(ZoneOffset.UTC)).build());

		FEEDS.add(Feeds.builder().id(2L).title("Post 2").content("Post 2 content")
				.publishDateTime(OffsetDateTime.now(ZoneOffset.UTC)).build());
	}

	@Override
	public List<Feeds> getFeeds() {
		log.info("Finding details of all feeds");
		return FEEDS;
	}

	@Override
	public Optional<FeedWithComment> getFeed(Long id) {

		log.info("Finding details of feed with id {}", id);
		Optional<Feeds> optionalFeed = FEEDS.stream().filter(feed -> feed.getId().equals(id)).findFirst();
		Optional<FeedWithComment> optionalFeedWithComments = optionalFeed.map(this::asFeedWithComments);
		optionalFeedWithComments.ifPresent(feedWithComments -> {
			List<Comment> comments = this.findCommentsForFeed(feedWithComments);
			feedWithComments.setComments(comments);
		});

		return optionalFeedWithComments;
	}

	private List<Comment> findCommentsForFeed(Feeds feed) {

		log.info("Finding comments of feed with id {}", feed.getId());
		
		String url = UriComponentsBuilder.fromHttpUrl(commentServiceBaseUrl).path("comments")
				.queryParam("feedId", feed.getId()).toUriString();


		ResponseEntity<List<Comment>> response = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Comment>>() {
				});

		List<Comment> comments = Objects.isNull(response.getBody()) ? new ArrayList<>() : response.getBody();
		log.info("Found {} comment(s) of feed with id {}", comments.size(), feed.getId());
		return comments;
	}

	private FeedWithComment asFeedWithComments(Feeds feed) {
		FeedWithComment feedWithComments = new FeedWithComment();
		BeanUtils.copyProperties(feed, feedWithComments);
		return feedWithComments;
	}
}
