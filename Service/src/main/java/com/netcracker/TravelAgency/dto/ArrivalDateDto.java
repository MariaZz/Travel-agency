package com.netcracker.TravelAgency.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.GregorianCalendar;
import java.util.Objects;

@Getter
@Setter
public class ArrivalDateDto extends BaseEntityDto {

    private Integer countryId;
    private GregorianCalendar arrivalDate;
    private Integer numberOfFreePlaces;

    public static Builder builder() {
        return new ArrivalDateDto().new Builder();
    }


    public class Builder {
        private Builder() {
        }

        public Builder withId(final Integer id) {
            ArrivalDateDto.super.setId(id);
            return this;
        }

        public Builder withCountryId(final Integer country) {
            ArrivalDateDto.this.countryId= country;
            return this;
        }

        public Builder withArrivalDate(final GregorianCalendar arrivalDate) {
            ArrivalDateDto.this.arrivalDate = arrivalDate;
            return this;
        }

        public Builder withNumberOfFreePlaces(final Integer numberOfFreePlaces) {
            ArrivalDateDto.this.numberOfFreePlaces = numberOfFreePlaces;
            return this;
        }

        public ArrivalDateDto build() {
            return ArrivalDateDto.this;
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                "\"country\":" + countryId +
                ", \"arrival date\":" + arrivalDate +
                ", \"number of free places\":" + numberOfFreePlaces + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrivalDateDto that = (ArrivalDateDto) o;
        return Objects.equals(countryId, that.countryId) &&
                Objects.equals(arrivalDate, that.arrivalDate) &&
                Objects.equals(numberOfFreePlaces, that.numberOfFreePlaces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryId, arrivalDate, numberOfFreePlaces);
    }
}
