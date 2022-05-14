package com.example.Learning.Spring.web;

import com.example.Learning.Spring.business.GuestInformation;
import com.example.Learning.Spring.business.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/guests")
public class GuestController {
    private final ReservationService reservationService;

    public GuestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getGuests(Model model){
        List<GuestInformation> guestInfos = this.reservationService.getGuestInfos();
        model.addAttribute("guestInfos", guestInfos);
        return "guestpage";
    }


}
