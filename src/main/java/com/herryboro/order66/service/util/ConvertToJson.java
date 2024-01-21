package com.herryboro.order66.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.herryboro.order66.dto.store.Option;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConvertToJson {
    private ObjectMapper objectMapper = new ObjectMapper();

    public String convertJsonToString(List<Option> options) {
        try {
            return objectMapper.writeValueAsString(options);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting options to JSON", e);
        }
    }
}
