/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicpmsdatamigrator.store;

import clinicpmsdatamigrator.model.Appointment;
import clinicpmsdatamigrator.model.Patient;
import clinicpmsdatamigrator.store.exceptions.StoreException;
import clinicpmsdatamigrator.store.interfaces.IMigrationManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author colin
 */
public class SQLExpressStore extends Store {
    private static SQLExpressStore instance = null;
    private MigrationManager migrationManager = null;
    String databaseURL = "jdbc:postgresql://localhost/ClinicPMS?user=colin";
    
    public String getDatabaseURL(){
        return databaseURL;
    }
    
    public void setDatabaseURL(String value){
        databaseURL = value;
    }
    public static SQLExpressStore getInstance(){
        SQLExpressStore result = null;
        if (instance == null) result = new SQLExpressStore();
        else result = instance;
        return result;
    }
    public Appointment create(Appointment a) throws StoreException{
        return null;
    }
    public Patient create(Patient p) throws StoreException{
        return null;
    }
    public void delete(Appointment a) throws StoreException{
        
    }
    public void delete(Patient p) throws StoreException{
        
    }
    public Appointment read(Appointment a) throws StoreException{
        return null;
    }
    public Patient read(Patient p) throws StoreException{
        return null;
    }
    public ArrayList<Appointment> readAppointments(LocalDate day) throws StoreException{
        return null;
    }
    public ArrayList<Appointment> readAppointments(Patient p, Appointment.Category c) throws StoreException{
        return null;
    }
    public ArrayList<Appointment> readAppointmentsFrom(LocalDate day) throws StoreException{
        return null;
    }
    public ArrayList<Patient> readPatients() throws StoreException{
        return null;
    }
    public Patient update(Patient p) throws StoreException{
        return null;
    }
    public Appointment update(Appointment a) throws StoreException{
        return null;
    }
    
    public MigrationManager getMigrationManager(){
        return migrationManager;
    }
    
    private void runSQL(MigrationAppointmentSQL q){
        
    }
    
    private void runSQL(MigrationPatientSQL q){
        
    }
    
    public class MigrationManager implements IMigrationManager{
        private ArrayList<clinicpmsdatamigrator.model.Appointment> appointments = null;
        private ArrayList<clinicpmsdatamigrator.model.Patient> patients = null;
        private String appointmentSourceFile = null;
        private String patientSourceFile = null;
        private String database = null;
        private int filteredAppointmentCount = 0;
        private int unfilteredAppointmentCount = 0;
        private int nonExistingPatientsReferencedByAppointmentsCount = 0;
        private int appointmentCount = 0;
        private int patientCount = 0;
        
        public int getAppointmentCount(){
            return appointmentCount; 
        }
        public void setAppointmentCount(int value){
            appointmentCount = value;
        }
        
        public int getPatientCount(){
            return patientCount; 
        }
        public void setPatientCount(int value){
            patientCount = value;
        }
        public int getFilteredAppointmentCount(){
            return filteredAppointmentCount; 
        }
        public void setFilteredAppointmentCount(int value){
            filteredAppointmentCount = value;
        }
        public int getUnfilteredAppointmentCount(){
            return unfilteredAppointmentCount; 
        }
        public void setUnfilteredAppointmentCount(int value){
            unfilteredAppointmentCount = value;
        }
        public int getNonExistingPatientsReferencedByAppointmentsCount(){
            return nonExistingPatientsReferencedByAppointmentsCount;
        }
        public void setNonExistingPatientsReferencedByAppointmentsCount(int value){
            nonExistingPatientsReferencedByAppointmentsCount = value;
        }
        public void action(Store.MigrationMethod mm)throws StoreException{
        }

        public String getAppointmentSourceFile(){
            return null;
        }
        
        public String getPatientSourceFile(){
            return null;
        }

        public String getDatabase(){
            return null;
        }

        public void setAppointmentSourceFile(String value){
   
        }
        
        public void setPatientSourceFile(String value){
 
        }

        public void setDatabase(String value){

        }
        
        public ArrayList<clinicpmsdatamigrator.model.Appointment> getAppointments(){
            return null;
        }

        public ArrayList<clinicpmsdatamigrator.model.Patient> getPatients(){
            return null;
        }

        public void setAppointments(ArrayList<clinicpmsdatamigrator.model.Appointment> value){
        }

        public void setPatients(ArrayList<clinicpmsdatamigrator.model.Patient> value){
        }
        
        public void createAppointmentTable()throws StoreException{
            runSQL(MigrationAppointmentSQL.APPOINTMENT_TABLE_CREATE);
        }
        
        public void createPatientTable()throws StoreException {
            runSQL(MigrationPatientSQL.PATIENT_TABLE_CREATE);
        }
        
        public void dropAppointmentTable()throws StoreException{
            runSQL(MigrationAppointmentSQL.APPOINTMENT_TABLE_DROP);
        }
        
        public void dropPatientTable() throws StoreException{
            runSQL(MigrationPatientSQL.PATIENT_TABLE_DROP);
        }
        
        /**
         * populates the Appointment table with appointment records
         * @param appointments
         * @return int count of the number of appointment records
         * @throws StoreException 
         */
       public int insertMigratedAppointments(ArrayList<Appointment> appointments)throws StoreException{
           int result = 0;
           return result;
       }
       
       public int insertMigratedPatients(ArrayList<Patient> patients)throws StoreException{
           int result = 0;
           return result;
       }
       
       public int migratedAppointmentsIntegrityCheck()throws StoreException{
            return 0;
        }

        private void deleteOrphanedAppointmentsFromAppointments(ArrayList<Integer> nonExistingPatientKeys)throws StoreException{
            ArrayList<Integer> orphanedAppointmentKeys = new ArrayList<Integer>();
            ArrayList<Integer> patientlessAppointmentKeys = new ArrayList<>();
            //ArrayList<Appointment> appointments = readAppointments();
            Iterator<Integer> nonExistingPatientKeysIt = nonExistingPatientKeys.iterator();
            while (nonExistingPatientKeysIt.hasNext()){
                Integer nonExistingPatientKey = nonExistingPatientKeysIt.next();
                //deleteAppointmentsWithPatientKey(nonExistingPatientKey);
            }
        }
        
        public void migratedPatientsTidied()throws StoreException{
            
        }
    }
}
