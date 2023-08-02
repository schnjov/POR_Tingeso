package cl.usach.tingeso.resumenservice.Services;

import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class DateComparator {

    public static boolean isFirstDateBeforeSecond(String dateStr1, String dateStr2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date1 = LocalDate.parse(dateStr1, formatter);
        LocalDate date2 = LocalDate.parse(dateStr2, formatter);

        return date1.isBefore(date2);
    }
}
