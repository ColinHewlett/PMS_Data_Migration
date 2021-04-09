/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicpmsdatamigrator.store.interfaces;

import clinicpmsdatamigrator.model.Appointment;
import clinicpmsdatamigrator.model.Patient;
import clinicpmsdatamigrator.store.Store;
import clinicpmsdatamigrator.store.exceptions.StoreException;
import java.util.ArrayList;


/**
 *
 * @author colin
 */
public interface IMigrationManager {
    public void action(Store.MigrationMethod mm)throws StoreException;
    public ArrayList<Appointment> getAppointments();
    public ArrayList<Patient> getPatients();
    public void setAppointments(ArrayList<Appointment> appointments);
    public void setPatients(ArrayList<Patient> patients);
    public int getAppointmentCount();
    public int getPatientCount();
    public int getNonExistingPatientsReferencedByAppointmentsCount();
    public int getUnfilteredAppointmentCount();
    public int getFilteredAppointmentCount();
    public void setAppointmentCount(int value);
    public void setPatientCount(int count);
    public void setNonExistingPatientsReferencedByAppointmentsCount(int count);
    public void setUnfilteredAppointmentCount(int count);
    public void setFilteredAppointmentCount(int count);

            int filteredAppointmentCount = 0;
    /*
    public void createAppointmentTable()throws StoreException;
    public void dropAppointmentTable()throws StoreException;
    public void createPatientTable()throws StoreException;
    public void dropPatientTable()throws StoreException;
    public int insertMigratedAppointments(ArrayList<Appointment> appointments)throws StoreException;
    public int insertMigratedPatients(ArrayList<Patient> patients)throws StoreException;
    public int migratedAppointmentsIntegrityCheck()throws StoreException;
    public void migratedPatientsTidied()throws StoreException;
    */
}
