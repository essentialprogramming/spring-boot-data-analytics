package com.util.convertor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class JacksonEnumConverter implements GenericConverter {

    private final ObjectMapper mapper;
    private final Set<ConvertiblePair> set;

    @Autowired
    public JacksonEnumConverter(ObjectMapper mapper) {
        set = new HashSet<>();
        set.add(new ConvertiblePair(String.class, Enum.class));
        this.mapper = mapper;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return set;
    }

    @Override
    public Object convert(@Nullable Object source, @NonNull TypeDescriptor sourceType, @NonNull TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        try {
            return mapper.readValue("\"" + source + "\"", targetType.getType());
        } catch (IOException e) {
            throw new IllegalArgumentException (targetType.getName());
        }
    }
}
