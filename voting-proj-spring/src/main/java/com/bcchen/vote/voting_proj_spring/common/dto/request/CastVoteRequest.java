package com.bcchen.vote.voting_proj_spring.common.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CastVoteRequest {
    private String voterId;
    private String voterName;
    private List<Integer> itemIds;
}
