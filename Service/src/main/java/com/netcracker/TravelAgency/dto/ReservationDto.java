package com.netcracker.TravelAgency.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.GregorianCalendar;
import java.util.Objects;

@Getter
@Setter
public class ReservationDto extends BaseEntityDto {
    private Integer roomId;
    private GregorianCalendar reservationDate;
    private Integer numberOfRooms;

    public static Builder builder() {
        return new ReservationDto().new Builder();
    }


    public class Builder {
        private Builder() {
        }

        public Builder withId(final Integer id) {
            ReservationDto.super.setId(id);
            return this;
        }

        public Builder withReservationDate(final GregorianCalendar reservationDate) {
            ReservationDto.this.reservationDate = reservationDate;
            return this;
        }

        public Builder withNumberOfRooms(final Integer numberOfRooms) {
            ReservationDto.this.numberOfRooms = numberOfRooms;
            return this;
        }

        public Builder withRoom(final Integer roomId) {
            ReservationDto.this.roomId = roomId;
            return this;
        }

        public ReservationDto build() {
            return ReservationDto.this;
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                " \"room\":" + roomId +
                ", \"reservation date\":" + reservationDate +
                ", \"number of rooms\":" + numberOfRooms + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationDto that = (ReservationDto) o;
        return Objects.equals(roomId, that.roomId) &&
                Objects.equals(reservationDate, that.reservationDate) &&
                Objects.equals(numberOfRooms, that.numberOfRooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, reservationDate, numberOfRooms);
    }
}
