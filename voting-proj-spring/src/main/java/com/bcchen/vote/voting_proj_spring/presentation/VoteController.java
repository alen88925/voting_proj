package com.bcchen.vote.voting_proj_spring.presentation;

import com.bcchen.vote.voting_proj_spring.business.VoteService;
import com.bcchen.vote.voting_proj_spring.common.dto.request.CastVoteRequest;
import com.bcchen.vote.voting_proj_spring.common.dto.response.VoteItemVoteResponse;
import com.bcchen.vote.voting_proj_spring.common.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vote")
@AllArgsConstructor
public class VoteController {
    private final VoteService voteService;

    // GET /api/vote/items
    @GetMapping("/items")
    public ApiResponse getActiveItems() {
        List<VoteItemVoteResponse> items = voteService.getActiveItems();
        return ApiResponse.success("項目顯示成功", items);
    }

    // POST /api/vote/cast
    @PostMapping("/cast")
    public ApiResponse castVotes(@RequestBody CastVoteRequest request) {
        voteService.castVotes(request);
        return ApiResponse.success("投票成功", null);
    }
}
