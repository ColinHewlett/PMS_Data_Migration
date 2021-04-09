/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicpmsdatamigrator.controller;

import clinicpmsdatamigrator.model.Appointment;
import clinicpmsdatamigrator.model.Appointments;
import clinicpmsdatamigrator.model.Patient;
import clinicpmsdatamigrator.store.AccessStore;
//import clinicpmsdatamigrator.store.CSVMigrationManager;
import clinicpmsdatamigrator.store.interfaces.IStore;
import clinicpmsdatamigrator.store.Store;
import clinicpmsdatamigrator.store.Store.Storage;
import clinicpmsdatamigrator.store.exceptions.StoreException;
import clinicpmsdatamigrator.view.DesktopView;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.JOptionPane;

/**
 *
 * @author colin
 */
public class DesktopViewController extends ViewController{
    private boolean isDesktopPendingClosure = false;
    private DesktopView view = null;
    private ArrayList<AppointmentViewController> appointmentViewControllers = null;
    private ArrayList<PatientViewController> patientViewControllers = null;
    //private HashMap<ViewControllers,ArrayList<ViewController>> viewControllers = null;
     
    enum ViewControllers {
                            PATIENT_VIEW_CONTROLLER,
                            APPOINTMENT_VIEW_CONTROLLER,
                         }
   
    public MigrationDescriptor getMigrationDescriptorFromView(){
        return null;
    }
    
    private DesktopViewController(){
        
        //setAppointmentsViewController(new AppointmentViewController(this));
        //setPatientViewController(new PatientViewController(this));
        view = new DesktopView(this);
        view.setSize(850, 650);
        view.setVisible(true);
        setView(view);
        //view.setContentPane(view);
        
        appointmentViewControllers = new ArrayList<>();
        patientViewControllers = new ArrayList<>();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        String s;
        s = e.getSource().getClass().getSimpleName();
        switch(s){
            case "DesktopView" -> doDesktopViewAction(e);
            case "AppointmentViewController" -> doAppointmentViewControllerAction(e);
            case "PatientViewController" -> doPatientViewControllerAction(e);
        }
    }
    
    private void doAppointmentViewControllerAction(ActionEvent e){
        AppointmentViewController avc = null;
        if (e.getActionCommand().equals(
            ViewController.DesktopViewControllerActionEvent.VIEW_CLOSED_NOTIFICATION.toString())){
            Iterator<AppointmentViewController> viewControllerIterator = 
                    this.appointmentViewControllers.iterator();
            if (this.appointmentViewControllers.size()>0){
                while(viewControllerIterator.hasNext()){
                    avc = viewControllerIterator.next();
                    if (avc.equals(e.getSource())){
                        avc.getView().dispose();
                        break;
                    }
                }
                if (!this.appointmentViewControllers.remove(avc)){
                    String message = "Could not find AppointmentViewController in "
                                            + "DesktopViewController collection.";
                        displayErrorMessage(message,"DesktopViewController error",JOptionPane.WARNING_MESSAGE);
                        /*
                        JOptionPane.showMessageDialog(getView(),
                                                  new ErrorMessagePanel(message));
                        */
                }
                else{
                    if (this.isDesktopPendingClosure){
                        this.requestViewControllersToCloseViews();
                    }
                }
            }
        }
    }
    private void doPatientViewControllerAction(ActionEvent e){
        PatientViewController pvc = null;
        if (e.getActionCommand().equals(
            ViewController.DesktopViewControllerActionEvent.VIEW_CLOSED_NOTIFICATION.toString())){
            Iterator<PatientViewController> viewControllerIterator = 
                    this.patientViewControllers.iterator();
            while(viewControllerIterator.hasNext()){
                pvc = viewControllerIterator.next();
                if (pvc.equals(e.getSource())){
                    break;
                }
            }
            if (!this.patientViewControllers.remove(pvc)){
                String message = "Could not find PatientViewController in "
                                        + "DesktopViewController collection.";
                displayErrorMessage(message,"DesktopViewController error",JOptionPane.WARNING_MESSAGE);
                /*
                JOptionPane.showMessageDialog(getView(),
                                          new ErrorMessagePanel(message));
                */
            }
            else{
                if (this.isDesktopPendingClosure){
                    this.requestViewControllersToCloseViews();
                }
            }
        }
        else if (e.getActionCommand().equals(
            ViewController.PatientViewControllerActionEvent.
                    APPOINTMENT_VIEW_CONTROLLER_REQUEST.toString())){
            PatientViewController patientViewController = (PatientViewController)e.getSource();
            Optional<MigrationDescriptor> ed = Optional.of(patientViewController.getMigrationDescriptorFromView());
            createNewAppointmentViewController();
            
        }
    }
    /**
     * 
     * @param e source of event is DesktopView object
     */
    private void doDesktopViewAction(ActionEvent e){  
        if(e.getActionCommand().equals(
                ViewController.DesktopViewControllerActionEvent.VIEW_CLOSE_REQUEST.toString())){
            String[] options = {"Yes", "No"};
            String message;
            if (!appointmentViewControllers.isEmpty()||!patientViewControllers.isEmpty()){
                message = "At least one patient or appointment view is active. Close application anyway?";
            }
            else {message = "Close The Clinic Data Migrator?";}
            int close = JOptionPane.showOptionDialog(getView(),
                            message,null,
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            options,
                            null);
            if (close == JOptionPane.YES_OPTION){
                this.isDesktopPendingClosure = true;
                if (this.appointmentViewControllers.isEmpty()||this.patientViewControllers.isEmpty()){
                    requestViewControllersToCloseViews();
                }
                else {
                    getView().dispose();
                    System.exit(0);
                }    
            }
        }
        else if (e.getActionCommand().equals(
            ViewController.MigratorViewControllerActionEvent.
                    APPOINTMENT_MIGRATOR_REQUEST.toString())){
            createNewAppointmentViewController();
        }
        else if (e.getActionCommand().equals(
            ViewController.MigratorViewControllerActionEvent.
                    PATIENT_MIGRATOR_REQUEST.toString())){
            try{
                patientViewControllers.add(
                                        new PatientViewController(this, getView()));
                PatientViewController pvc = patientViewControllers.get(patientViewControllers.size()-1);

                this.getView().getDeskTop().add(pvc.getView());
                pvc.getView().setVisible(true);
                pvc.getView().setClosable(false);
                pvc.getView().setMaximizable(false);
                pvc.getView().setIconifiable(true);
                pvc.getView().setResizable(false);
                pvc.getView().setSelected(true);
                pvc.getView().setSize(450,230);
            }
            catch (StoreException ex){
                displayErrorMessage(ex.getMessage(),"DesktopViewController error",JOptionPane.WARNING_MESSAGE);
                /*
                JOptionPane.showMessageDialog(getView(),
                                          new ErrorMessagePanel(ex.getMessage()));
                */
            }
            catch (PropertyVetoException ex){
                displayErrorMessage(ex.getMessage(),"DesktopViewController error",JOptionPane.WARNING_MESSAGE);
                /*
                JOptionPane.showMessageDialog(getView(),
                                          new ErrorMessagePanel(ex.getMessage()));
                */
            }
        } 
        /**
         * user has attempted to close the desktop view
         */
        else if(e.getActionCommand().equals(
                ViewController.DesktopViewControllerActionEvent.VIEW_CLOSED_NOTIFICATION.toString())){
            System.exit(0);
        }
        /*
        else if(e.getActionCommand().equals(
                ViewController.DesktopViewControllerActionEvent.MIGRATE_APPOINTMENT_DBF_TO_CSV.toString())){
            try{
                CSVMigrationManager.action(Store.MigrationMethod.CSV_APPOINTMENT_FILE_CONVERTER);
            }
            catch (StoreException ex){
                displayErrorMessage(ex.getMessage(),"DesktopViewController error",JOptionPane.WARNING_MESSAGE);
            }
        } 
        */
        /*
        else if(e.getActionCommand().equals(
                ViewController.DesktopViewControllerActionEvent.MIGRATE_PATIENT_DBF_TO_CSV.toString())){
            try{
                CSVMigrationManager.action(Store.MigrationMethod.CSV_PATIENT_FILE_CONVERTER);
            }
            catch (StoreException ex){
                displayErrorMessage(ex.getMessage(),"DesktopViewController error",JOptionPane.WARNING_MESSAGE);
            }
        }
        */
        /*
        else if(e.getActionCommand().equals(
                ViewController.DesktopViewControllerActionEvent.MIGRATE_INTEGRITY_CHECK.toString())){
            try{
        */
        
                /**
                 * CSV_MIGRATION_INTEGRITY_PROCESS action produces a list of orphaned 
                 * appointments which refer to patient keys which no longer exist; and
                 * uses this to delete the orphaned records from the system.
                 * -- nonExistingPatients.csv lists the patient keys uniquely referred to by
                 * orphaned appointment records. Note this is derived from a set in which
                 * duplicate references (to the same patient key) do not appear in the list (1668 keys)
                 * -- the orphaned appointment.csv lists all references to patient keys that 
                 * do not exist (i.e. non unique list of keys, total 10081
                 * -- the referential integrity between appointments and patients is achieved 
                 * with the deletion of each of the orphaned appointment records in the list
                 * (37794 - 10081 = 27713 remaining appointment records)
                 * 
                 */
                /*
                CSVMigrationManager.action(Store.MigrationMethod.CSV_MIGRATION_INTEGRITY_PROCESS);
            }
            catch (StoreException ex){
                displayErrorMessage(ex.getMessage(),"DesktopViewController error",JOptionPane.WARNING_MESSAGE);

            }
        }
        */
        else if(e.getActionCommand().equals(
                ViewController.DesktopViewControllerActionEvent.MIGRATE_PATIENT_DATE_CLEANED_IN_ACCESS.toString())){
            try{
                AccessStore store = AccessStore.getInstance();
                store.tidyPatientImportedDate();
            }
            catch (StoreException ex){
                displayErrorMessage(ex.getMessage(),"DesktopViewController error",JOptionPane.WARNING_MESSAGE);
                /*
                JOptionPane.showMessageDialog(getView(),
                                          new ErrorMessagePanel(ex.getMessage()));
                */
            }
        }
    }

    private DesktopView getView(){
        return this.view;
    }       
    private void setView(DesktopView view){
        this.view = view;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {   
        Border border = null;
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            /**
             * Selection of persistent storage type
             */
            if (args.length > 0){
                switch (args[0]){
                    case "ACCESS" -> {
                        Store.setStorageType(Storage.ACCESS);
                    }
                    //case "CSV" -> Store.setStorageType(Storage.CSV);
                    case "POSTGRES" -> Store.setStorageType(Storage.POSTGRES);
                    case "SQL_EXPRESS" -> Store.setStorageType(Storage.SQL_EXPRESS);
                }
            }
            else Store.setStorageType(Storage.UNDEFINED_DATABASE);
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            /*
            javax.swing.UIManager.getDefaults().put("TableHeader.cellBorder",new LineBorder(Color.RED,2));
            border = javax.swing.UIManager.getBorder("TableHeader.cellBorder");
            */
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DesktopView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DesktopView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DesktopView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DesktopView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DesktopViewController();
            }
        });
    }
    
    private void requestViewControllersToCloseViews(){
        if (this.patientViewControllers.size() > 0){
            Iterator<PatientViewController> pvcIterator = patientViewControllers.iterator();
            while(pvcIterator.hasNext()){
                PatientViewController pvc = pvcIterator.next();
                ActionEvent actionEvent = new ActionEvent(
                        this,ActionEvent.ACTION_PERFORMED,
                        ViewController.DesktopViewControllerActionEvent.VIEW_CLOSE_REQUEST.toString());
                pvc.actionPerformed(actionEvent);    
            }
        }
        
        if (this.appointmentViewControllers.size() > 0){
            Iterator<AppointmentViewController> avcIterator = appointmentViewControllers.iterator();
            while(avcIterator.hasNext()){
                AppointmentViewController avc = avcIterator.next();
                ActionEvent actionEvent = new ActionEvent(
                        this,ActionEvent.ACTION_PERFORMED,
                        ViewController.DesktopViewControllerActionEvent.VIEW_CLOSE_REQUEST.toString());
                avc.actionPerformed(actionEvent);    
            }
        }
        if ((appointmentViewControllers.isEmpty()) && (patientViewControllers.isEmpty())){
            if (this.isDesktopPendingClosure){
                getView().dispose();
                System.exit(0);
            }
        }
        else{
            //this.requestViewControllersToCloseViews();
        }
         
    }
    
    private void createNewAppointmentViewController(){
        try{
                appointmentViewControllers.add(
                                            new AppointmentViewController(this, getView()));
                AppointmentViewController avc = 
                        appointmentViewControllers.get(appointmentViewControllers.size()-1);
                
                this.getView().getDeskTop().add(avc.getView());
                avc.getView().setVisible(true);
                //avc.getView().setTitle("Appointments");
                avc.getView().setClosable(false);
                avc.getView().setMaximizable(false);
                avc.getView().setIconifiable(true);
                avc.getView().setResizable(false);
                avc.getView().setSelected(true);
                avc.getView().setSize(450,300);
            }
            catch (StoreException ex){
                displayErrorMessage(ex.getMessage(),"DesktopViewController error",JOptionPane.WARNING_MESSAGE);
                /*
                JOptionPane.showMessageDialog(getView(),
                                          new ErrorMessagePanel(ex.getMessage()));
                */
            }
            catch (PropertyVetoException ex){
                displayErrorMessage(ex.getMessage(),"DesktopViewController error",JOptionPane.WARNING_MESSAGE);
                /*
                JOptionPane.showMessageDialog(getView(),
                                          new ErrorMessagePanel(ex.getMessage()));
                */
            }
    }
    
    
}
