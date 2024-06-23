package Arini.src;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class App {
    public static void main(String[] args) {
        ZonedDateTime borrowDateTime = ZonedDateTime.of(2023, 6, 1, 10, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime returnDateTime = ZonedDateTime.of(2023, 6, 10, 15, 0, 0, 0, ZoneId.of("UTC"));

        //BorrowRecord br = new BorrowRecord(borrowDateTime, returnDateTime);
        //double fine = library.calculateFine();

        System.out.println("The fine is: $" + fine);
    }
}
