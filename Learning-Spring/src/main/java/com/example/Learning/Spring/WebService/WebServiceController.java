package com.example.Learning.Spring.WebService;

import com.example.Learning.Spring.business.GuestInformation;
import com.example.Learning.Spring.business.ReservationService;
import com.example.Learning.Spring.business.RoomReservation;
import com.example.Learning.Spring.data.Guest;
import com.example.Learning.Spring.data.Room;
import com.example.Learning.Spring.util.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WebServiceController {
    private final DateUtils dateUtils;
    private final ReservationService reservationService;

    public WebServiceController(DateUtils dateUtils, ReservationService reservationService) {
        this.dateUtils = dateUtils;
        this.reservationService = reservationService;
    }

    @RequestMapping(path = "/reservations", method = RequestMethod.GET)
    public List<RoomReservation> getReservations(@RequestParam(value="date", required = false)String dataString){
        Date date = this.dateUtils.createDateFromDateString(dataString);
        return this.reservationService.getRoomReservationsForDate(date);
    }

    @RequestMapping(path = "/guests", method = RequestMethod.GET)
    public List<GuestInformation> getGuests(){
        return this.reservationService.getGuestInfos();
    }

    @RequestMapping(path = "/guests", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addGuest(@RequestBody Guest newGuest){
        this.reservationService.addNewGuest(newGuest);
    }

    @RequestMapping(path = "/rooms", method = RequestMethod.GET)
    public List<Room> getRooms(){
        return this.reservationService.getAllRooms();
    }
}
