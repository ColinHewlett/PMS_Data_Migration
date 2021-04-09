/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicpmsdatamigrator.model;

import clinicpmsdatamigrator.model.Appointment.Category;
import clinicpmsdatamigrator.model.interfaces.IAppointments;
import clinicpmsdatamigrator.store.CSVStore;
import clinicpmsdatamigrator.store.Store;
import clinicpmsdatamigrator.store.exceptions.StoreException;
import clinicpmsdatamigrator.store.interfaces.IStore;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author colin
 */
public class Appointments{
    /*
    private CSVStore getStore() throws StoreException{
        return CSVStore.getInstance();
    }
    */
    
    private Appointment getNextAppointment(ArrayList<Appointment> list){
        LocalDate today = LocalDate.now();
        Appointment nextAppointment = null;
        Iterator<Appointment> it = list.iterator();
        while(it.hasNext()){
            Appointment a = it.next();
            if (today.isBefore(a.getStart().toLocalDate())){
                nextAppointment = a;
            }
            else if (today.isEqual(a.getStart().toLocalDate())){
                nextAppointment = a;
                break;
            }
            if (today.isAfter(a.getStart().toLocalDate())){
                break;
            }   
        }
        return nextAppointment;
    }
    
    private Appointment getLastAppointment(ArrayList<Appointment> list){
        LocalDate today = LocalDate.now();
        Appointment lastAppointment = null;
        Iterator<Appointment> it = list.iterator();
        while(it.hasNext()){
            Appointment a = it.next();
            if (today.isAfter(a.getStart().toLocalDate())){
                lastAppointment = a;
            }
            else if (today.isEqual(a.getStart().toLocalDate())){
                lastAppointment = a;
                break;
            }
            if (today.isBefore(a.getStart().toLocalDate())){
                break;
            }   
        }
        return lastAppointment;
    }
    
    public ArrayList<Appointment> getAllAppointments() throws StoreException{
        return CSVStore.getInstance().readAppointments();
    }

    public Appointment getLastDentalAppointmentFor(Patient p) throws StoreException{
        ArrayList<Appointment> appointments = getAppointmentsFor(p,Category.DENTAL);
        return getLastAppointment(appointments);
    }

    public Appointment getNextDentalAppointmentFor(Patient p) throws StoreException{
        ArrayList<Appointment> appointments = 
                CSVStore.getInstance().readAppointments(p,Category.DENTAL);
        return getNextAppointment(appointments); 
    }

    public Appointment getLastHygieneAppointmentFor(Patient p) throws StoreException{
        ArrayList<Appointment> appointments = getAppointmentsFor(p,Category.HYGIENE);
        return getLastAppointment(appointments);
    }

    public Appointment getNextHygieneAppointmentFor(Patient p) throws StoreException{
        ArrayList<Appointment> appointments = getAppointmentsFor(p,Category.HYGIENE);
        return getNextAppointment(appointments);
    }

    /**
     * 
     * @param p Patient object
     * @param t Category enumeration constant
     * @return ArrayList of Appointment objects
     * @throws StoreException 
     */
    public ArrayList<Appointment> getAppointmentsFor(Patient p, Category t) throws StoreException{
        IStore store = Store.factory();
        return store.readAppointments(p, t);
    }
    
    /**
     * 
     * @param day LocalDate object
     * @return ArrayList of Appointment objects
     * @throws StoreException 
     */
    public ArrayList<Appointment> getAppointmentsFor(LocalDate day) throws StoreException{
        IStore store = Store.factory();
        return store.readAppointments(day);
    }
    
    public ArrayList<Appointment>getAppointmentsFrom(LocalDate day) throws StoreException{
        IStore store = Store.factory();
        return store.readAppointmentsFrom(day);
    }
}
