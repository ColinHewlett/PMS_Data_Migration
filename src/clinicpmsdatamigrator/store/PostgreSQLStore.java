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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author colin
 */
public class PostgreSQLStore extends Store {

    public enum AppointmentSQL   {
                            CREATE_APPOINTMENT,
                            DELETE_APPOINTMENT_WITH_KEY,
                            READ_APPOINTMENTS_FOR_DAY,
                            READ_APPOINTMENTS_FROM_DAY,
                            READ_APPOINTMENTS_FOR_PATIENT,
                            READ_APPOINTMENT_WITH_KEY,
                            READ_HIGHEST_KEY,
                            UPDATE_APPOINTMENT}

    public enum PatientSQL   {CREATE_PATIENT,
                                READ_ALL_PATIENTS,
                                READ_HIGHEST_KEY,
                                READ_PATIENT_WITH_KEY,
                                UPDATE_PATIENT}

    private static PostgreSQLStore instance;
    private Connection connection = null;
    private String message = null;
    private MigrationManager migrationManager = null;
    String databaseURL = "jdbc:postgresql://localhost/ClinicPMS?user=colin";
    
    DateTimeFormatter ymdFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    
    public String getDatabaseURL(){
        return databaseURL;
    }
    
    public void setDatabaseURL(String value){
        databaseURL = value;
    }
    private void setConnection(Connection con){
        this.connection = con;
    }
    private Connection getConnection()throws StoreException{
        Connection result = null;
        if (connection == null){
            try{
                connection = DriverManager.getConnection(databaseURL);
            }
            catch (SQLException ex){
                message = ex.getMessage();
                throw new StoreException("SQLException message -> " + message +"\n"
                        + "StoreException message -> raised trying to connect to the PostgreSQL database",
                ExceptionType.SQL_EXCEPTION);
            }
        }
        return connection;
    }
    
    public PostgreSQLStore()throws StoreException{
        connection = getConnection();
        migrationManager = new MigrationManager();
    }
    
    public static PostgreSQLStore getInstance()throws StoreException{
        PostgreSQLStore result = null;
        if (instance == null) result = new PostgreSQLStore();
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
        private int patientCount = 0;
        
        
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
        public String getAppointmentSourceFile(){
            return appointmentSourceFile;
        }
        
        public String getPatientSourceFile(){
            return patientSourceFile;
        }

        public String getDatabase(){
            return database;
        }

        public void setAppointmentSourceFile(String value){
            appointmentSourceFile = value;
        }
        
        public void setPatientSourceFile(String value){
            patientSourceFile = value;
        }

        public void setDatabase(String value){
            database = value;
        }
        
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
        
        public void action(Store.MigrationMethod mm)throws StoreException{
            int count = 0;

            switch (mm){
                case APPOINTMENT_TABLE_CREATE -> {}
                case APPOINTMENT_TABLE_POPULATE -> {}
                case APPOINTMENT_TABLE_INTEGRITY_CHECK -> {}
                case PATIENT_TABLE_CREATE ->{}
                case PATIENT_TABLE_DROP ->{}
            }
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
            int patientCount = 0;
            int orphanCount = 0;
            Integer key = null;
            ArrayList<Integer> nonExistingPatientsReferencedbyAppointments = new ArrayList<>();
            HashSet<Integer> patientSet = new HashSet<>();
            Iterator<Appointment> it = appointments.iterator();
            while (it.hasNext()){
                Appointment appointment = it.next();
                key = appointment.getPatient().getKey();
                patientSet.add(key);
            }
            Iterator<Integer> patientSetIt = patientSet.iterator();
            while (patientSetIt.hasNext()){
                key = patientSetIt.next();
                try{
                    read(new Patient(key));
                    patientCount++;
                }
                catch (StoreException ex){
                    if (ex.getErrorType().equals(ExceptionType.KEY_NOT_FOUND_EXCEPTION)){
                        nonExistingPatientsReferencedbyAppointments.add(key);
                    }
                }
            }
            deleteOrphanedAppointmentsFromAppointments(nonExistingPatientsReferencedbyAppointments);
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
