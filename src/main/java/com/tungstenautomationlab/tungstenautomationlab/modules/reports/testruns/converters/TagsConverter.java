package com.tungstenautomationlab.tungstenautomationlab.modules.reports.testruns.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class TagsConverter implements AttributeConverter<List<String>,String[]> {
    @Override
    public String[] convertToDatabaseColumn(List<String> list) {
        return list.toArray(new String[0]);
    }

    @Override
    public List<String> convertToEntityAttribute(String[] arr) {
        return Arrays.stream(arr).collect(Collectors.toList());
    }
}
