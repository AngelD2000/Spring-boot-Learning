package com.example.Learning.Spring.business;

import com.example.Learning.Spring.data.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class ReservationService {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getRoomReservationsForDate(Date date) {
        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservationMap.put(room.getId(), roomReservation);
        });
        Iterable<Reservation> reservations = this.reservationRepository.findReservationByReservationDate(new java.sql.Date(date.getTime()));
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
            roomReservation.setDate(date);
            Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
            roomReservation.setGuestId(guest.getGuestId());
        });
        List<RoomReservation> roomReservations = new ArrayList<>();
        for (Long id : roomReservationMap.keySet()) {
            roomReservations.add(roomReservationMap.get(id));
        }
        roomReservations.sort(new Comparator<RoomReservation>() {
            @Override
            public int compare(RoomReservation o1, RoomReservation o2) {
                if (o1.getRoomName().equals(o2.getRoomName())) {
                    return o1.getRoomNumber().compareTo(o2.getRoomNumber());
                }
                return o1.getRoomName().compareTo(o2.getRoomName());
            }
        });
        return roomReservations;
    }
    public List<GuestInformation> getGuestInfos(){
        Iterable<Guest> guests = this.guestRepository.findAll();
        Map<Long, GuestInformation> guestInformationMap = new HashMap();
        guests.forEach(guest -> {
            GuestInformation guestInformation = new GuestInformation();
            guestInformation.setGuestId(guest.getGuestId());
            guestInformation.setFirstName(guest.getFirstName());
            guestInformation.setLastName(guest.getLastName());
            guestInformation.setEmailAddress(guest.getEmailAddress());
            guestInformation.setPhoneNumber(guest.getPhoneNumber());
            guestInformationMap.put(guest.getGuestId(), guestInformation);
        });
        List<GuestInformation> guestinfos = new ArrayList<>();
        for (Long id : guestInformationMap.keySet()) {
            guestinfos.add(guestInformationMap.get(id));
        }
        return guestinfos;
    }

    public void addNewGuest(Guest newGuest){
        if(null == newGuest){
            throw new RuntimeException("Guest cannot be null");
        }
        this.guestRepository.save(newGuest);
    }

    public List<Room> getAllRooms(){
        Iterable<Room> allRooms = this.roomRepository.findAll();
        List<Room> listAllRooms = new ArrayList<>();
        allRooms.forEach(room -> {listAllRooms.add(room);});
        return listAllRooms;
    }
}
