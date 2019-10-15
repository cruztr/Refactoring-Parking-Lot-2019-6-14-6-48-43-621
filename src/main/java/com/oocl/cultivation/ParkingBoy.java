package com.oocl.cultivation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

public class ParkingBoy {

    public static final String NOT_ENOUGH_POSITION = "Not enough position.";
    public static final String PLEASE_PROVIDE_YOUR_PARKING_TICKET = "Please provide your parking ticket.";
    public static final String UNRECOGNIZED_PARKING_TICKET = "Unrecognized parking ticket.";

    List<ParkingLot> parkingLotList;
    String lastErrorMessage;

    public ParkingBoy(ParkingLot parkingLot) {
        this.parkingLotList = new ArrayList<>();
        this.parkingLotList.add(parkingLot);
    }

    public void addParkingLot(ParkingLot parkingLot) {
        this.parkingLotList.add(parkingLot);
    }

    public List<ParkingLot> getParkingLotList(){
        return this.parkingLotList;
    }

    public ParkingLot getParkingLotAtIndex(int index) {
        return this.parkingLotList.get(index);
    }

    public ParkingTicket park(Car car) {
        ParkingLot parkingLot = getFirstAvailableParkingLot();
        if(parkingLot == null) {
            this.lastErrorMessage = NOT_ENOUGH_POSITION;
            return null;
        }

        ParkingTicket parkingTicket = parkingLot.park(car);
        if(parkingTicket == null)
            this.lastErrorMessage = NOT_ENOUGH_POSITION;

        return parkingTicket;
    }

    public Car fetch(ParkingTicket ticket) {
        if(parkingLotList.isEmpty())
            return null;

        if(ticket == null){
            this.lastErrorMessage = PLEASE_PROVIDE_YOUR_PARKING_TICKET;
            return null;
        }

        Car car = parkingLotList.stream()
                .map(parkingLotInsideList -> parkingLotInsideList.fetch(ticket))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        if(!isNull(car)){
            return car;
        }

        this.lastErrorMessage = UNRECOGNIZED_PARKING_TICKET;
        return null;
    }

    private ParkingLot getFirstAvailableParkingLot() {
        return parkingLotList.stream()
                .filter(parkingLot -> parkingLot.getAvailableParkingPosition() != 0)
                .findFirst()
                .orElse(null);
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
}
