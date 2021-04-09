/**
 * Current version includes an inner class which defines references to
 * Appointment objects, i.e the lastDentalAppointment, the nextDentalAppointment 
 * and the nextHygieneAppointment. The query leg work to fetch the Appointment 
 * objects can be done automatically by the Store object or initiated by the 
 * Patient class. Currently the latter option is adopted.   
 */
package clinicpmsdatamigrator.model;

import clinicpmsdatamigrator.store.exceptions.StoreException;
import clinicpmsdatamigrator.store.interfaces.IStore;
import clinicpmsdatamigrator.store.Store;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author colin
 */
public class Patient {
    
    private LocalDate dob = null;
    private Patient guardian = null;
    private String gender = null;
    private Boolean isGuardianAPatent = null;
    private Integer key  = null;
    private String notes = null;
    private String phone1 = null;
    private String phone2 = null;

    private Patient.AppointmentHistory appointmentHistory = null;
    private Patient.Address address = null;
    private Patient.Name name = null;
    private Patient.Recall recall = null;

    public enum PatientField    {       ID,
                                        KEY,
                                        PHONE1,
                                        PHONE2,
                                        GENDER,
                                        DOB,
                                        IS_GUARDIAN_A_PATIENT,
                                        PATIENT_GUARDIAN,
                                        NOTES;
                    public enum Name    {   TITLE,
                                            FORENAMES,
                                            SURNAME
                                        }
                    public enum Address {   LINE1,
                                            LINE2,
                                            TOWN,
                                            COUNTY,
                                            POSTCODE
                                        }
                    public enum Recall  {   DENTAL_DATE,
                                            HYGIENE_DATE,
                                            DENTAL_FREQUENCY,
                                            HYGIENE_FREQUENCY
                                        }
                    public enum Activity    {   LAST_DENTAL_APPOINTMENT,
                                                NEXT_DENTAL_APPOINTMENT,
                                                NEXT_HYGIENE_APPOINTMENT
                                            }
                                }
    /**
     * Constructs a new Patient object with none of its fields initialised
     */
    public Patient(){
        name = new Name();
        address = new Address();
        recall = new Recall();
        appointmentHistory = new AppointmentHistory();
    } 
    
    public Patient(Integer key) {
            name = new Name();
            address = new Address();
            recall = new Recall();
            appointmentHistory = new AppointmentHistory();
            this.key = key;
    } 
    
    public Patient create() throws StoreException{
        IStore store = Store.factory();
        return store.create(this);    
    }
    
    public void delete() throws StoreException{
        IStore store = Store.factory();
        store.delete(this);
    }
    
    public Patient read() throws StoreException{
        IStore store = Store.factory();
        return store.read(this); 
    }
    
    public Patient update() throws StoreException{ 
        IStore store = Store.factory();
        return store.update(this);
    }

    public class Name {

        private String forenames = null;
        private String surname = null;
        private String title = null;

        public String getForenames() {
            return forenames;
        }

        public void setForenames(String forenames) {
            this.forenames = forenames;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }
    
    public class Address {

        private String line1 = null;
        private String line2 = null;
        private String town = null;
        private String county = null;
        private String postcode = null;

        public String getLine1() {
            return line1;
        }

        public void setLine1(String line1) {
            this.line1 = line1;
        }

        public String getLine2() {
            return line2;
        }

        public void setLine2(String line2) {
            this.line2 = line2;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

    }

    public class Recall {

        private LocalDate dentalDate = null;
        private LocalDate hygieneDate = null;
        private Integer dentalFrequency = null;
        private Integer hygieneFrequency = null;

        public LocalDate getDentalDate() {
            return dentalDate;
        }

        public void setDentalDate(LocalDate dentalDate) {
            this.dentalDate = dentalDate;
        }

        public Integer getDentalFrequency() {
            return dentalFrequency;
        }

        public void setDentalFrequency(Integer dentalFrequency) {
            this.dentalFrequency = dentalFrequency;
        }

        public LocalDate getHygieneDate() {
            return hygieneDate;
        }

        public void setHygieneDate(LocalDate hygieneDate) {
            this.hygieneDate = hygieneDate;
        }

        public Integer getHygieneFrequency() {
            return hygieneFrequency;
        }

        public void setHygieneFrequency(Integer hygieneFrequency) {
            this.hygieneFrequency = hygieneFrequency;
        }
    }
    
    public class AppointmentHistory{

        public ArrayList<Appointment> getDentalAppointments()throws StoreException{
            if (Patient.this.getKey()!=null) return new Appointments().getAppointmentsFor(
                    Patient.this,Appointment.Category.DENTAL);
            else return null;
        }
        
        public ArrayList<Appointment> getHygieneAppointments()throws StoreException{
            if (Patient.this.getKey()!=null) return new Appointments().getAppointmentsFor(
                    Patient.this,Appointment.Category.HYGIENE);
            else return null;
        }
    }
    
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDOB() {
        return dob;
    }
    public void setDOB(LocalDate dob) {
        this.dob = dob;
    }
    
    public Integer getKey(){
        return key;
    }
    public void setKey(Integer key){
        this.key = key;
    }
    
    public String getNotes(){
        return notes;
    }
    public void setNotes(String notes){
        this.notes = notes;
    }
    
    public String getPhone1(){
        return phone1;
    }
    public void setPhone1(String phone1){
        this.phone1 = phone1;
    }
    
    public String getPhone2(){
        return phone2;
    }
    public void setPhone2(String phone2){
        this.phone2 = phone2;
    }
    
    public Boolean getIsGuardianAPatient(){
        return isGuardianAPatent;
    }
    public void setIsGuardianAPatient(Boolean isGuardianAPatient){
        this.isGuardianAPatent = isGuardianAPatient;
    }
    
    public Patient getGuardian(){
        return guardian;
    }
    public void setGuardian(Patient guardian){
        this.guardian = guardian;
    }
    
    public Patient.Name getName(){
        return name;
    }
    public void setName(Patient.Name name){
        this.name = name;
    }
    
    public Patient.Address getAddress(){
        return address;
    }
    public void setAddress(Patient.Address address){
        this.address = address;
    }
    
    public Patient.Recall getRecall(){
        return recall;
    }
    public void setRecall(Patient.Recall recall){
        this.recall = recall;
    }
    
    public Patient.AppointmentHistory getAppointmentHistory(){
        return appointmentHistory;
    }
    public void setAppointmentHistory(Patient.AppointmentHistory value){
        this.appointmentHistory = value;
    }
    

}
