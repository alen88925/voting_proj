package com.bcchen.vote.voting_proj_spring.presentation;

import com.bcchen.vote.voting_proj_spring.business.VoteItemService;
import com.bcchen.vote.voting_proj_spring.common.dto.request.VoteItemRequest;
import com.bcchen.vote.voting_proj_spring.common.dto.response.VoteItemResponse;
import com.bcchen.vote.voting_proj_spring.common.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {

    private final VoteItemService voteItemService;

    // GET /api/admin/items
    @GetMapping("/items")
    public ApiResponse getAllItems() {
        List<VoteItemResponse> items = voteItemService.getAllItems();
        return ApiResponse.success("查詢成功", items);
    }

    // POST /api/admin/items
    @PostMapping("/items")
    public ApiResponse createItem(@RequestBody VoteItemRequest request) {
        voteItemService.createItem(request);
        return ApiResponse.success("新增成功", null);
    }

    // PUT /api/admin/items/{id}
    @PutMapping("/items/{id}")
    public ApiResponse updateItem(
            @PathVariable Integer id,
            @RequestBody VoteItemRequest request) {
        voteItemService.updateItem(id, request);
        return ApiResponse.success("更新成功", null);
    }

    // PATCH /api/admin/items/{id}/toggle
    @PatchMapping("/items/{id}/toggle")
    public ApiResponse toggleItem(@PathVariable Integer id) {
        voteItemService.toggleItem(id);
        return ApiResponse.success("狀態更新成功", null);
    }

    // DELETE /api/admin/items/{id}
    @DeleteMapping("/items/{id}")
    public ApiResponse deleteItem(@PathVariable Integer id) {
        voteItemService.deleteItem(id);
        return ApiResponse.success("刪除成功", null);
    }
}