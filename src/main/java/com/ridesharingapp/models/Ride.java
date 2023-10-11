package com.ridesharingapp.models;

import com.ridesharingapp.enums.RideStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;


@Getter
@Setter
@Entity
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "rider_id")
    private User rider;
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @Column(nullable = false)
    private Date startTime;

    @Column(nullable = false)
    private Date endTime;

    @OneToOne(mappedBy = "ride", cascade = CascadeType.ALL)
    private Location startLocation;

    @OneToOne(mappedBy = "ride", cascade = CascadeType.ALL)
    private Location endLocation;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ride)) return false;
        Ride ride = (Ride) o;
        return getId() == ride.getId() && Objects.equals(getRider(), ride.getRider()) && Objects.equals(getDriver(), ride.getDriver()) && Objects.equals(getStartTime(), ride.getStartTime()) && Objects.equals(getEndTime(), ride.getEndTime()) && Objects.equals(getStartLocation(), ride.getStartLocation()) && Objects.equals(getEndLocation(), ride.getEndLocation()) && getRideStatus() == ride.getRideStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRider(), getDriver(), getStartTime(), getEndTime(), getStartLocation(), getEndLocation(), getRideStatus());
    }

    @Override
    public String toString() {
        return "Ride{" +
                "id=" + id +
                ", rider=" + rider +
                ", driver=" + driver +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", startLocation=" + startLocation +
                ", endLocation=" + endLocation +
                ", rideStatus=" + rideStatus +
                '}';
    }
}
