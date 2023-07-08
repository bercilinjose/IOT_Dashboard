package com.eoxys.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ArrayMapConverter implements AttributeConverter<List<Map<String, Object>>, String> {

  private static ObjectMapper mapper;

  static {
    // To avoid instantiating ObjectMapper again and again.
    mapper = new ObjectMapper();
  }

  @Override
  public String convertToDatabaseColumn(List<Map<String, Object>> data) {
    if (null == data) { 
      // You may return null if you prefer that style
      return null;
    }
    
    try {
      return mapper.writeValueAsString(data);
      
    } catch (IOException e) {
      throw new IllegalArgumentException("Error converting map to JSON", e);
    }
  }

  @Override
  public List<Map<String, Object>> convertToEntityAttribute(String s) {
    if (null == s) {
      // You may return null if you prefer that style
      return null;
    }

    try {
      return mapper.readValue(s, new TypeReference<List<Map<String, Object>>>() {});
      
    } catch (IOException e) {
      throw new IllegalArgumentException("Error converting JSON to map", e);
    }
  }
}
