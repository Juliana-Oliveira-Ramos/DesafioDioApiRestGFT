package Entites;
import  com.github.javafaker.DateAndTime;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BookingDates {
public String checkin;
public String checkout;




    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public BookingDates(String checkin, String checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }
}
