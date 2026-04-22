package com.bcchen.vote.voting_proj_spring.business;

import com.bcchen.vote.voting_proj_spring.common.dto.request.VoteItemRequest;
import com.bcchen.vote.voting_proj_spring.common.dto.response.VoteItemResponse;
import com.bcchen.vote.voting_proj_spring.common.exception.BusinessException;
import com.bcchen.vote.voting_proj_spring.data.VoteItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VoteItemService {

    private final VoteItemRepository voteItemRepository;

    public List<VoteItemResponse> getAllItems() {
        return voteItemRepository.getAllItems();
    }

    public void createItem(VoteItemRequest request) {

        if (request.getItemName() == null || request.getItemName().isBlank()) {
            throw new BusinessException("投票項目名稱不能為空");
        }

        voteItemRepository.createItem(request);
    }

    public void updateItem(Integer itemId, VoteItemRequest request) {

        if (itemId == null || itemId <= 0) {
            throw new BusinessException("投票項目編號錯誤");
        }

        if (request.getItemName() == null || request.getItemName().isBlank()) {
            throw new BusinessException("投票項目名稱不能為空");
        }

        voteItemRepository.updateItem(itemId, request);
    }

    public void toggleItem(Integer itemId) {

        if (itemId == null || itemId <= 0) {
            throw new BusinessException("投票項目編號錯誤");
        }

        voteItemRepository.toggleItem(itemId);
    }

    public void deleteItem(Integer itemId) {

        if (itemId == null || itemId <= 0) {
            throw new BusinessException("投票項目編號錯誤");
        }

        voteItemRepository.deleteItem(itemId);
    }
}