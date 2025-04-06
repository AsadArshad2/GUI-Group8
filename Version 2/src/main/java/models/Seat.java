package models;

public class Seat {
    private String row;
    private String seat;
    private boolean restrictedView;
    private boolean wheelchairAccessible;

    public Seat(String row, String seat, boolean restrictedView, boolean wheelchairAccessible) {
        this.row = row;
        this.seat = seat;
        this.restrictedView = restrictedView;
        this.wheelchairAccessible = wheelchairAccessible;
    }

    public String getRow() {
        return row;
    }

    public String getSeat() {
        return seat;
    }

    public boolean isRestrictedView() {
        return restrictedView;
    }

    public boolean isWheelchairAccessible() {
        return wheelchairAccessible;
    }
}
