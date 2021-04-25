package com.mlc.autohaus.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class ApiError {
    private final String type;
    private final String title;
    private final int status;
    private final String instance;
    private final Long timestamp;
    private final String path;
}

