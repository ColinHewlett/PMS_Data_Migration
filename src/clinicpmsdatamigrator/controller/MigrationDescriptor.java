/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicpmsdatamigrator.controller;

import clinicpmsdatamigrator.model.Appointment;
import clinicpmsdatamigrator.model.Patient;
import java.util.ArrayList;
/**
 *
 * @author colin
 */
public class MigrationDescriptor {
    private MigrationDescriptor.Appointment appointment = null;
    private MigrationDescriptor.Patient patient = null;
    private Target target = null;
    private ArrayList<clinicpmsdatamigrator.model.Appointment> appointments = null;
    private ArrayList<clinicpmsdatamigrator.model.Patient> patients = null;
    
    public MigrationDescriptor(){
        appointment = new Appointment();
        patient = new Patient();
        target = new Target();
    }
    
    public  ArrayList<clinicpmsdatamigrator.model.Appointment>  getAppointments(){
        return appointments;
    }
    
    public void setAppointments(ArrayList<clinicpmsdatamigrator.model.Appointment> value){
        appointments = value;
    }
    
    public ArrayList<clinicpmsdatamigrator.model.Patient> getPatients(){
        return patients;
    }
    
    public void setPatients(ArrayList<clinicpmsdatamigrator.model.Patient> value){
        patients = value;
    }
    
    public Appointment getAppointment(){
        return appointment;
    }
    
    public Patient getPatient(){
        return patient;
    }
    
    public Target getTarget(){
        return target;
    }
    
    public void setTarget(Target value){
        target = value;
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
    
    public class Target{
        private String data = null;
        public String getData(){
            return data;
        }
        public void setData(String value){
            data = value;
        }
    }  
    
    public class Appointments{
        private ArrayList<clinicpmsdatamigrator.model.Appointment> appointments = null;
        
        public ArrayList<clinicpmsdatamigrator.model.Appointment> getData(){
            return appointments;
        } 
    }
    
    public class Patients{
        private ArrayList<clinicpmsdatamigrator.model.Patient> patients = null;
        
        public ArrayList<clinicpmsdatamigrator.model.Patient> getData(){
            return patients;
        }
    }
}
