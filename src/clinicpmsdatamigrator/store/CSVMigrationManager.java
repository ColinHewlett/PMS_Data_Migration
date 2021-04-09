/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicpmsdatamigrator.store;

import clinicpmsdatamigrator.store.exceptions.StoreException;
import java.util.ArrayList;

/**
 *
 * @author colin
 */
public class CSVMigrationManager {
    private CSVMigrationManager.Appointment appointment = 
            new CSVMigrationManager.Appointment();
    private CSVMigrationManager.Patient patient = 
            new CSVMigrationManager.Patient();
    private ArrayList<clinicpmsdatamigrator.model.Appointment> appointments = null;
    private ArrayList<clinicpmsdatamigrator.model.Patient> patients = null;
    
    public ArrayList<clinicpmsdatamigrator.model.Appointment> getAppointments(){
        return appointments;
    }
    
    public ArrayList<clinicpmsdatamigrator.model.Patient> getPatients(){
        return patients;
    }
    
    public void setAppointments(ArrayList<clinicpmsdatamigrator.model.Appointment> value){
        appointments = value;
    }
    
    public void setPatients(ArrayList<clinicpmsdatamigrator.model.Patient> value){
        patients = value;
    }
    public CSVMigrationManager.Appointment getAppointment(){
        return appointment;
    }
    
    public CSVMigrationManager.Patient getPatient(){
        return patient;
    }
    
    public class Appointment{
        private String data = null;
        
        public String getData(){
            return data;
        }
        
        public void setData(String value){
            data = value;
        }
    }
    
    public class Patient{
        private String data = null;
        
        public String getData(){
            return data;
        }
        
        public void setData(String value){
            data = value;
        }
    }

    public void action(Store.CSVMigrationMethod mm)throws StoreException{
        ArrayList<clinicpmsdatamigrator.model.Appointment> as = null;
        ArrayList<clinicpmsdatamigrator.model.Patient> ps = null;
        int count = 0;
        
        switch (mm){
            case CSV_APPOINTMENT_FILE_CONVERTER ->{
                 as = CSVStore.getInstance().migrateAppointments(getAppointment().getData());
                 CSVMigrationManager.this.setAppointments(as);
                 count = getAppointments().size();
            }
            case CSV_PATIENT_FILE_CONVERTER -> {
                ps = CSVStore.getInstance().migratePatients(getPatient().getData());
                CSVMigrationManager.this.setPatients(ps);
            }
        }
    }
}
