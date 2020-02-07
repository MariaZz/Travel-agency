package com.netcracker.TravelAgency.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "\"Hotel\"")
public class Hotel extends BaseEntity {
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(name = "name")
    private String name;

    @Column(name = "accommodations")
    private String accommodations;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_id", nullable = false)
    private Operator operator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    @NotNull(message = "Country cannot be null")
    private Country country;

    @Enumerated(EnumType.ORDINAL)
    @NotNull(message = "Type of food cannot be null")
    @Column(name = "typeoffood")
    private TypeOfFood typeOfFood;

    @Enumerated(EnumType.ORDINAL)
    @NotNull(message = "Hotel category cannot be null")
    @Column(name = "hotelCategory")
    private HotelCategory hotelCategory;

    public enum TypeOfFood {
        NO("Without food"),
        BB("Breakfast"),
        HB("Breakfast + dinner"),
        FB("Breakfast + lunch + dinner"),
        ALL("All inclusive");
        private String str;

        TypeOfFood(String s) {
            this.str = s;
        }
    }

    public enum HotelCategory {
        TWO("2*"),
        THREE("3*"),
        FOUR("4*"),
        FIVE("5*"),
        FIVEPLUS("5+*");
        private String str;

        HotelCategory(String s) {
            this.str = s;
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                "\"accommodation\":" + accommodations +
                ", \"operator\":" + operator.getId() +
                ", \"country\":" + country.getId() +
                ", \"hotel category\":" + hotelCategory.str +
                ", \"type of food\":" + typeOfFood.str + '}';
    }
}
