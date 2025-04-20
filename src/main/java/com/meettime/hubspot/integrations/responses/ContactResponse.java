package com.meettime.hubspot.integrations.responses;

import java.time.ZonedDateTime;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ContactResponse {

    private String id;

    private boolean archived;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private ZonedDateTime archivedAt;

    private String objectWriteTraceId;

    private Map<String, String> properties;

    @JsonProperty("propertiesWithHistory")
    private Map<String, PropertyHistory[]> propertiesWithHistory;

    public static class PropertyHistory {
        private String sourceId;
        private String sourceType;
        private String sourceLabel;
        private int updatedByUserId;
        private String value;
        private ZonedDateTime timestamp;
    }
}
