package com.netcracker.TravelAgency.dto;

import com.netcracker.TravelAgency.entity.Hotel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class TourDto extends BaseEntityDto {

    private List<Integer> arrivalDatesId;
    private Integer lengthOfStay;
    private Hotel.HotelCategory hotelCategory;
    private Hotel.TypeOfFood typeOfFood;
    private Integer operatorId;
    private Integer countryId;
    private Integer hotelId;
    private Integer roomId;
    private Integer cost;


    public static Builder builder() {
        return new TourDto().new Builder();
    }


    public class Builder {
        private Builder() {
        }

        public Builder withId(final Integer id) {
            TourDto.super.setId(id);
            return this;
        }

        public Builder withArrivalDatesId(final List<Integer> arrivalDates) {
            TourDto.this.arrivalDatesId = arrivalDates;
            return this;
        }

        public Builder withLengthOfStay(final Integer lengthOfStay) {
            TourDto.this.lengthOfStay = lengthOfStay;
            return this;
        }

        public Builder withHotelCategory(final Hotel.HotelCategory hotelCategory) {
            TourDto.this.hotelCategory = hotelCategory;
            return this;
        }

        public Builder withTypeOfFood(final Hotel.TypeOfFood typeOfFood) {
            TourDto.this.typeOfFood = typeOfFood;
            return this;
        }

        public Builder withCountryId(final Integer country) {
            TourDto.this.countryId = country;
            return this;
        }

        public Builder withOperatorId(final Integer operator) {
            TourDto.this.operatorId = operator;
            return this;
        }

        public Builder withHotel(final Integer hotel) {
            TourDto.this.hotelId = hotel;
            return this;
        }

        public Builder withRoomId(final Integer roomId) {
            TourDto.this.roomId = roomId;
            return this;
        }

        public Builder withCost(final Integer cost) {
            TourDto.this.cost = cost;
            return this;
        }

        public TourDto build() {
            return TourDto.this;
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                "\"country\":" + countryId +
                ", \"length of stay\":" + lengthOfStay +
                ", \"hotel\":" + hotelId +
                ", \"operator\":" + operatorId +
                ", \"hotel category\":" + hotelCategory.toString() +
                ", \"type of food\":" + typeOfFood.toString() +
                "\"room\":" + roomId +
                ", \"cost\":" + cost + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TourDto tourDto = (TourDto) o;
        return Objects.equals(arrivalDatesId, tourDto.arrivalDatesId) &&
                Objects.equals(lengthOfStay, tourDto.lengthOfStay) &&
                hotelCategory == tourDto.hotelCategory &&
                typeOfFood == tourDto.typeOfFood &&
                Objects.equals(operatorId, tourDto.operatorId) &&
                Objects.equals(countryId, tourDto.countryId) &&
                Objects.equals(hotelId, tourDto.hotelId) &&
                Objects.equals(roomId, tourDto.roomId) &&
                Objects.equals(cost, tourDto.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arrivalDatesId, lengthOfStay, hotelCategory, typeOfFood, operatorId, countryId, hotelId, roomId, cost);
    }
}
