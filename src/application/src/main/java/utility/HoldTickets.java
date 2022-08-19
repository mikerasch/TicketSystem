package utility;

import ticketsystem.Ticket;

import java.util.ArrayList;
import java.util.HashMap;

public final class HoldTickets {
    public static ArrayList<Ticket> ticketArrayList = new ArrayList<>();
    public static HashMap<String,Integer> rateLimit = new HashMap<>();
    private HoldTickets(){

    }

    public static void addTicket(Ticket ticket){
        ticketArrayList.add(ticket);
    }

}
