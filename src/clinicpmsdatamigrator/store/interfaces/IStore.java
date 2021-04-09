/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicpmsdatamigrator.store.interfaces;

import clinicpmsdatamigrator.store.Store;
import clinicpmsdatamigrator.model.Appointment;
import clinicpmsdatamigrator.model.Appointment.Category;
import clinicpmsdatamigrator.model.Patient;
import com.opencsv.exceptions.CsvException;
import clinicpmsdatamigrator.model.Patient;
import clinicpmsdatamigrator.store.exceptions.StoreException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Defines simple interface for a CVS database to support the following breadth
 * of queries. 
 * A production system would use an RDBMS, never a collection CVS files.
 * -- all patients (returned if query = null and Patient arg has a null key)
 * -- patients due for recall in this month (query = "DentalRecallDate=dd/mm/yyyy")
 * -- all appointments (returned if query = null and Appointment arg has a null key)
 * -- appointments on this date (query = "Start=dd/mm/yyyy")
 * -- appointments scheduled for this patient (query = "Patient=dd/mm/yyyy)
 * -- Query syntax  can be primitive
 * -- Patient queries will be returned in name order
 * -- Appointment queries will be returned time ordered.
 * @author colin
 */
import java.io.IOException;
public interface IStore {

    public Appointment create(Appointment a) throws StoreException;
    public Patient create(Patient p) throws StoreException;
    public void delete(Appointment a) throws StoreException;
    public void delete(Patient p) throws StoreException;
    public IMigrationManager getMigrationManager();
    public Appointment read(Appointment a) throws StoreException;
    public Patient read(Patient p) throws StoreException;
    public ArrayList<Appointment> readAppointments(LocalDate day) throws StoreException;
    public ArrayList<Appointment> readAppointments(Patient p, Category c) throws StoreException;
    public ArrayList<Appointment> readAppointmentsFrom(LocalDate day) throws StoreException;
    public ArrayList<Patient> readPatients() throws StoreException;
    public Patient update(Patient p) throws StoreException;
    public Appointment update(Appointment a) throws StoreException; 
}
