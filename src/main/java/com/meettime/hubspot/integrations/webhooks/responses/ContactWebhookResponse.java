package com.meettime.hubspot.integrations.webhooks.responses;

import lombok.Data;

@Data
public class ContactWebhookResponse {
    private Long appId;
    private Long eventId;
    private Long subscriptionId;
    private Long portalId;
    private Long occurredAt;
    private String subscriptionType;
    private Long attemptNumber;
    private Long objectId;
    private String changeSource;
    private String changeFlag;
}
