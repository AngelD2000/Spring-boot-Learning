package com.example.Learning.Spring.data;

import org.springframework.data.repository.CrudRepository;

import java.sql.Date;

public interface ReservationRepository extends CrudRepository<Reservation,Long> {
    Iterable<Reservation> findReservationByReservationDate(Date date);
}
