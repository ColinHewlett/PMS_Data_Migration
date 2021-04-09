/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicpmsdatamigrator.model.interfaces;
import clinicpmsdatamigrator.model.Appointment;
import clinicpmsdatamigrator.model.Appointment.Category;
import clinicpmsdatamigrator.model.Patient;
import clinicpmsdatamigrator.store.exceptions.StoreException;
import java.util.ArrayList;
import java.time.LocalDate;
/**
 *
 * @author colin
 */
public interface IAppointments {

    public ArrayList<Appointment> getAppointmentsFor(LocalDate day) throws StoreException;
    public ArrayList<Appointment> getAppointmentsFor(Patient p,Category c ) throws StoreException;
    public Appointment getLastDentalAppointmentFor(Patient p) throws StoreException;
    public Appointment getNextDentalAppointmentFor(Patient p) throws StoreException;
    public Appointment getLastHygieneAppointmentFor(Patient p) throws StoreException;
    public Appointment getNextHygieneAppointmentFor(Patient p) throws StoreException;
    
}
