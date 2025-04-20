package com.meettime.hubspot.integrations.requests;

import lombok.Data;

@Data
public class ContactRequest {
    public String email;
    public String firstname;
    public String lastname;
    public String phone;
    public String company;
    public String website;
    public String lifecyclestage;
}
