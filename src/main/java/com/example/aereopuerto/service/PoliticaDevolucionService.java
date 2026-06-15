package com.example.aereopuerto.service;
import com.example.aereopuerto.model.Reserva;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service

public class PoliticaDevolucionService {

    public BigDecimal calcular (Reserva reserva){

        long diasRestantes = ChronoUnit.DAYS.between(
                LocalDate.now(),
                reserva.getFechaReserva()
        );
        BigDecimal porcentaje;
        if (diasRestantes >= 30){
            return reserva.getValor();
        }
        else if (diasRestantes >= 20 &&  diasRestantes < 30){
           return reserva.getValor().multiply(new BigDecimal(.50));
        }
        else if (diasRestantes > 10 && diasRestantes < 20){
            return reserva.getValor().multiply(new BigDecimal(.15));
        }
        else {
            return BigDecimal.ZERO;
        }
    }

}
