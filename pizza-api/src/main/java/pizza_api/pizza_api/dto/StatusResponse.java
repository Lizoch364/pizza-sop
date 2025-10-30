package pizza_api.pizza_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record StatusResponse(String status, String error) {}
