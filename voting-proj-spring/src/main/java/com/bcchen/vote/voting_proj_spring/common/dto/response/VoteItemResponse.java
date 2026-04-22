package com.bcchen.vote.voting_proj_spring.common.dto.response;

import java.time.LocalDateTime;

public record VoteItemResponse(
        Integer itemId,
        String itemName,
        LocalDateTime itemCreatedTime,
        LocalDateTime itemUpdatedTime,
        Boolean isEnable,
        Integer voteCount) {
}
