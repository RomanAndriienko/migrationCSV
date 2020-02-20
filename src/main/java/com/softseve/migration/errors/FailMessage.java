package com.softseve.migration.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FailMessage {
   private Long line;
   private String msg;
}
