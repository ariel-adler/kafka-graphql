package com.tikalk.kafkagraphql.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class Message {
    String text;
    Instant timestamp;
}
