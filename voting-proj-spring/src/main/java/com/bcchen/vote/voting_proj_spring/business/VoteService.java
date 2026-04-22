package com.bcchen.vote.voting_proj_spring.business;

import com.bcchen.vote.voting_proj_spring.common.dto.request.CastVoteRequest;
import com.bcchen.vote.voting_proj_spring.common.dto.response.VoteItemVoteResponse;
import com.bcchen.vote.voting_proj_spring.common.exception.BusinessException;
import com.bcchen.vote.voting_proj_spring.data.VoteItemRepository;
import com.bcchen.vote.voting_proj_spring.data.VoteRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteItemRepository voteItemRepository;
    private final VoteRecordRepository voteRecordRepository;

    public List<VoteItemVoteResponse> getActiveItems() {
        return voteItemRepository.getActiveItems();
    }

    public void castVotes(CastVoteRequest request) {

        if (request.getItemIds() == null || request.getItemIds().isEmpty()) {
            throw new BusinessException("投票項目不能為空");
        }

        if (request.getVoterId() == null || request.getVoterId().isBlank()) {
            throw new BusinessException("用戶編號不能為空");
        }

        if (request.getVoterName() == null || request.getVoterName().isBlank()) {
            throw new BusinessException("用戶名稱不能為空");
        }

        voteRecordRepository.castVotes(request);
    }

}