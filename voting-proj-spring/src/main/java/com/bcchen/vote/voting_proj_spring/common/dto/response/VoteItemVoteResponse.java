package com.bcchen.vote.voting_proj_spring.common.dto.response;

public record VoteItemVoteResponse(
        Integer itemId,
        String itemName,
        Integer voteCount) {
}