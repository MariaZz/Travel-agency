package com.netcracker.TravelAgency.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class TouristDto extends BaseEntityDto {

    private String passportData;
    private Integer tripId;

    public static Builder builder() {
        return new TouristDto().new Builder();
    }


    public class Builder {
        private Builder() {
        }

        public Builder withId(final Integer id) {
            TouristDto.super.setId(id);
            return this;
        }

        public Builder withPasswordData(final String passwordData) {
            TouristDto.this.passportData = passportData;
            return this;
        }

        public Builder withTripId(final Integer tripId) {
            TouristDto.this.tripId = tripId;
            return this;
        }

        public TouristDto build() {
            return TouristDto.this;
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                " \"password data \":" + passportData +
                ", \"trip\":" + tripId + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TouristDto that = (TouristDto) o;
        return Objects.equals(passportData, that.passportData) &&
                Objects.equals(tripId, that.tripId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passportData, tripId);
    }
}
