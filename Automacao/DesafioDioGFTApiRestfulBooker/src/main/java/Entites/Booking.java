package Entites;

public class Booking {

    private String firstName;
    private String lastName;
    private Float totalPrice;
    private Boolean depositPaid;
    private BookingDates bookingDates;
    private String additionalNeeds;



    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastname(String lastname) {
        this.lastName = lastName;
    }

    public Float getTotalPrice() {

        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {

        this.totalPrice = totalPrice;
    }

    public Boolean getDepositPaid() {
        return depositPaid;
    }

    public void setDepositPaid(Boolean depositPaid) {

        this.depositPaid = depositPaid;
    }

    public BookingDates getBookingDates() {

        return bookingDates;
    }

    public void setBookingDates(BookingDates bookingDates) {
        this.bookingDates = bookingDates;
    }

    public String getAdditionalNeeds() {
        return additionalNeeds;
    }

    public void setAdditionalNeeds(String additionalNeeds) {

        this.additionalNeeds = additionalNeeds;
    }

    public Booking(String firstName, String lastName, Float totalPrice, Boolean depositPaid, BookingDates bookingDates, String additionalNeeds) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.totalPrice = totalPrice;
        this.depositPaid = depositPaid;
        this.bookingDates = bookingDates;
        this.additionalNeeds= additionalNeeds;
    }
}
