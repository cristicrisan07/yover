package com.example.poatenumergi.service;

import com.example.poatenumergi.model.FoodCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
    public class FoodCategoryConverter implements AttributeConverter<FoodCategory, String> {

        @Override
        public String convertToDatabaseColumn(FoodCategory category) {
            if (category == null) {
                return null;
            }
            return category.getCode();
        }

        @Override
        public FoodCategory convertToEntityAttribute(String code) {
            if (code == null) {
                return null;
            }

            return Stream.of(FoodCategory.values())
                    .filter(c -> c.getCode().equals(code))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
    }

