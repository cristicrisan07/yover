package com.example.poatenumergi.service;

import com.example.poatenumergi.model.FoodCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
    public class FoodCategoryConverter implements AttributeConverter<FoodCategory, String> {
    /**
     *
     * @param category FoodCategory instance that has to be converted into a string value.
     * @return String value of category.
     */
        @Override
        public String convertToDatabaseColumn(FoodCategory category) {

            if (category == null) {
                return null;
            }
            return category.getCode();
        }

    /**
     *
     * @param code string value of a FoodCategory instance.
     * @return correspondent instance of the provided string.
     */
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

