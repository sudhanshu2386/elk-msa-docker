package com.ibm.logAggregation.feed.domain;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feeds {

	private Long id;

	private String title;

	private String content;

	private OffsetDateTime publishDateTime;
}
