package com.netcracker.TravelAgency.dto;

import com.netcracker.TravelAgency.entity.Hotel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class HotelDto extends BaseEntityDto {

    private String accommodations;
    private Hotel.HotelCategory hotelCategory;
    private String name;
    private Hotel.TypeOfFood typeOfFood;
    private Integer operatorId;
    private Integer countryId;


    public static Builder builder() {
        return new HotelDto().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder withId(final Integer id) {
            HotelDto.super.setId(id);
            return this;
        }

        public Builder withName(final String name) {
            HotelDto.this.name = name;
            return this;
        }

        public Builder withTypeOfFood(final Hotel.TypeOfFood typeOfFood) {
            HotelDto.this.typeOfFood = typeOfFood;
            return this;
        }

        public Builder withCountryId(final Integer country) {
            HotelDto.this.countryId = country;
            return this;
        }

        public Builder withOperatorId(final Integer operatorId) {
            HotelDto.this.operatorId = operatorId;
            return this;
        }

        public Builder withAccommodations(final String accommodations) {
            HotelDto.this.accommodations = accommodations;
            return this;
        }

        public Builder withHotelCategory(final Hotel.HotelCategory hotelCategory) {
            HotelDto.this.hotelCategory = hotelCategory;
            return this;
        }

        public HotelDto build() {
            return HotelDto.this;
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                "\"accommodation\":" + accommodations +
                ", \"operator\":" + operatorId +
                ", \"country\":" + countryId +
                ", \"hotel category\":" + hotelCategory.toString() +
                ", \"type of food\":" + typeOfFood.toString() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotelDto hotelDto = (HotelDto) o;
        return Objects.equals(accommodations, hotelDto.accommodations) &&
                hotelCategory == hotelDto.hotelCategory &&
                Objects.equals(name, hotelDto.name) &&
                typeOfFood == hotelDto.typeOfFood &&
                Objects.equals(operatorId, hotelDto.operatorId) &&
                Objects.equals(countryId, hotelDto.countryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accommodations, hotelCategory, name, typeOfFood, operatorId, countryId);
    }
}
