package com.netcracker.TravelAgency.dto;

import com.netcracker.TravelAgency.entity.Room;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class RoomDto extends BaseEntityDto {

    private Integer hotelId;
    private Integer numberOfSleepingPlaces;
    private Room.TypeOfRoom typeOfRoom;
    private Integer numberOfRooms;


    public static Builder builder() {
        return new RoomDto().new Builder();
    }


    public class Builder {
        private Builder() {
        }

        public Builder withId(final Integer id) {
            RoomDto.super.setId(id);
            return this;
        }

        public Builder withHotelId(final Integer hotel) {
            RoomDto.this.hotelId = hotel;
            return this;
        }

        public Builder withNumberOfRooms(final Integer numberOfRooms) {
            RoomDto.this.numberOfRooms = numberOfRooms;
            return this;
        }

        public Builder withNumberOfSleepingPlaces(final Integer numberOfSleepingPlaces) {
            RoomDto.this.numberOfSleepingPlaces = numberOfSleepingPlaces;
            return this;
        }

        public Builder withTypeOfFood(final Room.TypeOfRoom typeOfRoom) {
            RoomDto.this.typeOfRoom = typeOfRoom;
            return this;
        }

        public RoomDto build() {
            return RoomDto.this;
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                "\"hotel\":" + hotelId +
                ", \"number of sleeping places\":" + numberOfSleepingPlaces +
                ", \"number of rooms\":" + numberOfRooms +
                ", \"type of room\":" + typeOfRoom.toString() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomDto roomDto = (RoomDto) o;
        return Objects.equals(hotelId, roomDto.hotelId) &&
                Objects.equals(numberOfSleepingPlaces, roomDto.numberOfSleepingPlaces) &&
                typeOfRoom == roomDto.typeOfRoom &&
                Objects.equals(numberOfRooms, roomDto.numberOfRooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotelId, numberOfSleepingPlaces, typeOfRoom, numberOfRooms);
    }
}
