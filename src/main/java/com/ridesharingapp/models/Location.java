package com.ridesharingapp.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Location  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Latitude is required")
    @Column(nullable = false)
    private double latitude;

    @NotNull(message = "longitude is required")
    @Column(nullable = false)
    private double longitude;

    @ManyToOne
    private Driver driver;

    @OneToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return getId() == location.getId() && Double.compare(location.getLatitude(), getLatitude()) == 0 && Double.compare(location.getLongitude(), getLongitude()) == 0 && Objects.equals(getDriver(), location.getDriver()) && Objects.equals(getRide(), location.getRide());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLatitude(), getLongitude(), getDriver(), getRide());
    }


    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", driver=" + driver +
                ", ride=" + ride +
                '}';
    }
}
