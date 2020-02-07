package com.netcracker.TravelAgency.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class CountryDto extends BaseEntityDto {

    private String name;
    private Integer travelAgentId;

    public static Builder builder() {
        return new CountryDto().new Builder();
    }


    public class Builder {
        private Builder() {
        }

        public Builder withId(final Integer id) {
            CountryDto.super.setId(id);
            return this;
        }

        public Builder withName(final String name) {
            CountryDto.this.name = name;
            return this;
        }

        public Builder withTravelAgentId(final Integer travelAgentId) {
            CountryDto.this.travelAgentId = travelAgentId;
            return this;
        }

        public CountryDto build() {
            return CountryDto.this;
        }
    }
    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                "\"name\":" + name +
                ", \"travelAgent\":" + travelAgentId + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryDto that = (CountryDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(travelAgentId, that.travelAgentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, travelAgentId);
    }
}
