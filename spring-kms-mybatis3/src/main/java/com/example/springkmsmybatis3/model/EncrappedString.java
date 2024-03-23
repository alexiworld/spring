package com.example.springkmsmybatis3.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EncrappedString { // used to wrap encrypted values in format "enc(encrypted-value)"
  private String value;

  @Override public String toString() {
    return value ;
  }

  public static EncrappedString to(String value) { return new EncrappedString(value); }
}