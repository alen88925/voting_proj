package com.bcchen.vote.voting_proj_spring.data;

import com.bcchen.vote.voting_proj_spring.common.dto.request.CastVoteRequest;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
public class VoteRecordRepository {

    private final JdbcTemplate jdbcTemplate;

    public void castVotes(CastVoteRequest request) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withoutProcedureColumnMetaDataAccess()
                .withProcedureName("sp_cast_votes");

        // 分開呼叫 declareParameters
        jdbcCall.declareParameters(
                new SqlParameter("p_voter_id", Types.CHAR),
                new SqlParameter("p_voter_name", Types.VARCHAR),
                new SqlParameter("p_item_ids", Types.VARCHAR)
        );

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("p_item_ids", request.getItemIds().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")))
                .addValue("p_voter_id", request.getVoterId())
                .addValue("p_voter_name", request.getVoterName());

        jdbcCall.execute(params);
    }
}
