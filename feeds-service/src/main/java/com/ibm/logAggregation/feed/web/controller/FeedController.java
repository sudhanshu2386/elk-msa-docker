package com.ibm.logAggregation.feed.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.logAggregation.feed.domain.FeedWithComment;
import com.ibm.logAggregation.feed.domain.Feeds;
import com.ibm.logAggregation.feed.service.FeedService;
import com.ibm.logAggregation.feed.web.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/feeds", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeedController {

	@Autowired
	private final FeedService service;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Feeds>> getFeeds() {
		List<Feeds> feeds = service.getFeeds();
		return ResponseEntity.ok(feeds);
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FeedWithComment> getFeed(@PathVariable Long id) {
		FeedWithComment feedWithComments = service.getFeed(id).orElseThrow(ResourceNotFoundException::new);
		return ResponseEntity.ok(feedWithComments);
	}
}
