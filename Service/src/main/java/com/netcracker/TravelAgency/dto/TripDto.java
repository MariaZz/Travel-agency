package com.netcracker.TravelAgency.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class TripDto extends BaseEntityDto {

    private GregorianCalendar arrivalDate;
    private Integer tourId;
    private List<Integer> touristsId;


    public static Builder builder() {
        return new TripDto().new Builder();
    }


    public class Builder {
        private Builder() {
        }

        public Builder withId(final Integer id) {
            TripDto.super.setId(id);
            return this;
        }

        public Builder withArrivalDate(final GregorianCalendar arrivalDate) {
            TripDto.this.arrivalDate = arrivalDate;
            return this;
        }

        public Builder withTourId(final Integer tour) {
            TripDto.this.tourId = tour;
            return this;
        }

        public Builder withTouristsId(final List<Integer> tourists) {
            TripDto.this.touristsId = tourists;
            return this;
        }

        public TripDto build() {
            return TripDto.this;
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                " \"tour\":" + tourId +
                ", \"arrival date\":" + arrivalDate + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripDto tripDto = (TripDto) o;
        return Objects.equals(arrivalDate, tripDto.arrivalDate) &&
                Objects.equals(tourId, tripDto.tourId) &&
                Objects.equals(touristsId, tripDto.touristsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arrivalDate, tourId, touristsId);
    }
}
