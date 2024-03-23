package com.example.springkmsmybatis2.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EncryptedString {
  private String value;

  @Override public String toString() {
    return value ;
  }

  public static EncryptedString to(String value) { return new EncryptedString(value); }
}