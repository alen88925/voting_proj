package com.bcchen.vote.voting_proj_spring.common.security;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;

public class XssStringDeserializer extends StdDeserializer<String> {

    public XssStringDeserializer() {
        super(String.class);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctx)
            throws IOException {
        String value = p.getValueAsString();
        return value != null ? HtmlUtils.htmlEscape(value) : null;
    }
}