package com.lethanh98.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PostUserRQ {
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @NotNull(message = "firstName là bắt buộc")
  private String firstName;

  @NotNull(message = "lastName là bắt buộc")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String lastName;
}
