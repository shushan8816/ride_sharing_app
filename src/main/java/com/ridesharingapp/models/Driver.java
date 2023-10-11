package com.ridesharingapp.models;

import com.ridesharingapp.enums.DriverStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "driver")
    private Set<Ride> rides;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DriverStatus driverStatus;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Driver)) return false;
        Driver driver = (Driver) o;
        return getId() == driver.getId() && Objects.equals(getFirstName(), driver.getFirstName()) && Objects.equals(getLastName(), driver.getLastName()) && Objects.equals(getRides(), driver.getRides()) && getDriverStatus() == driver.getDriverStatus();
    }


    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getRides(), getDriverStatus());
    }


    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", rides=" + rides +
                ", driverStatus=" + driverStatus +
                '}';
    }
}
