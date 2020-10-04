package com.ibm.logAggregation.feed.domain;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class FeedWithComment extends Feeds {

	private List<Comment> comments;
}
