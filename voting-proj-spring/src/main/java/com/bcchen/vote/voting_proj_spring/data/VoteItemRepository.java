package com.bcchen.vote.voting_proj_spring.data;

import com.bcchen.vote.voting_proj_spring.common.dto.request.VoteItemRequest;
import com.bcchen.vote.voting_proj_spring.common.dto.response.VoteItemResponse;
import com.bcchen.vote.voting_proj_spring.common.dto.response.VoteItemVoteResponse;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import java.sql.Timestamp;

@AllArgsConstructor
@Repository
public class VoteItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<VoteItemResponse> getAllItems() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withoutProcedureColumnMetaDataAccess()
                .withProcedureName("sp_get_all_items");

        Map<String,Object> result = jdbcCall.execute();

        @SuppressWarnings("unchecked")
        List<Map<String,Object>> rows = (List<Map<String,Object>>) result.get("#result-set-1");

        return rows.stream()
                .map(row -> new VoteItemResponse(
                        (Integer) row.get("item_id"),
                        (String) row.get("item_name"),
                        ((Timestamp)  row.get("item_created_time")).toLocalDateTime(),
                        ((Timestamp)  row.get("item_updated_time")).toLocalDateTime(),
                        (Boolean) row.get("is_enable"),
                        ((Long) row.get("vote_count")).intValue()
                ))
                .toList();
    }

    public void createItem(VoteItemRequest request){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withoutProcedureColumnMetaDataAccess()
                .withProcedureName("sp_create_item");

        // 分開呼叫 declareParameters
        jdbcCall.declareParameters(
                new SqlParameter("p_item_name", Types.VARCHAR)
        );

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("p_item_name", request.getItemName());

        jdbcCall.execute(params);
    }

    public void updateItem(Integer itemId, VoteItemRequest request){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withoutProcedureColumnMetaDataAccess()
                .withProcedureName("sp_update_item");

        // 分開呼叫 declareParameters
        jdbcCall.declareParameters(
                new SqlParameter("p_item_id", Types.INTEGER),
                new SqlParameter("p_item_name", Types.VARCHAR)
        );

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("p_item_id", itemId)
                .addValue("p_item_name", request.getItemName());

        jdbcCall.execute(params);
    }

    public void toggleItem(Integer itemId){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withoutProcedureColumnMetaDataAccess()
                .withProcedureName("sp_toggle_item");

        // 分開呼叫 declareParameters
        jdbcCall.declareParameters(
                new SqlParameter("p_item_id", Types.INTEGER)
        );

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("p_item_id", itemId);

        jdbcCall.execute(params);
    }

    public void deleteItem(Integer itemId){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withoutProcedureColumnMetaDataAccess()
                .withProcedureName("sp_delete_item");

        // 分開呼叫 declareParameters
        jdbcCall.declareParameters(
                new SqlParameter("p_item_id", Types.INTEGER)
        );

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("p_item_id", itemId);

        jdbcCall.execute(params);
    }

    public List<VoteItemVoteResponse> getActiveItems() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withoutProcedureColumnMetaDataAccess()
                .withProcedureName("sp_get_active_items");

        Map<String,Object> result = jdbcCall.execute();

        @SuppressWarnings("unchecked")
        List<Map<String,Object>> rows = (List<Map<String,Object>>) result.get("#result-set-1");

        return rows.stream()
                .map(row -> new VoteItemVoteResponse(
                        (Integer) row.get("item_id"),
                        (String) row.get("item_name"),
                        ((Long) row.get("vote_count")).intValue()
                ))
                .toList();
    }
}