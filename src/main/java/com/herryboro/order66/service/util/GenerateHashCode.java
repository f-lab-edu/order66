package com.herryboro.order66.service.util;

import org.springframework.stereotype.Service;

@Service
public class GenerateHashCode {
    public String generateHashCode(Long menuId, String optionsJson) {
        String combined = menuId + optionsJson;
        return Integer.toString(combined.hashCode());
    }
}
