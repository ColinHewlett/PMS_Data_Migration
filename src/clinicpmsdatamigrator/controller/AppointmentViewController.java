/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicpmsdatamigrator.controller;

import clinicpmsdatamigrator.model.Appointment;
import clinicpmsdatamigrator.model.Patient;
import clinicpmsdatamigrator.view.AppointmentMigratorView;
import clinicpmsdatamigrator.view.View;
import clinicpmsdatamigrator.store.CSVMigrationManager;
import clinicpmsdatamigrator.store.Store;
import clinicpmsdatamigrator.store.exceptions.StoreException;
import clinicpmsdatamigrator.store.interfaces.IStore;
import clinicpmsdatamigrator.store.interfaces.IMigrationManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author colin
 */
public class AppointmentViewController extends ViewController {
    private MigrationDescriptor migrationDescriptorFromView = null;
    private ActionListener myController = null;
    private View view = null;
    private JFrame owningFrame = null;
    private PropertyChangeSupport pcSupport = null;
    
    public AppointmentViewController(DesktopViewController controller, JFrame desktopView)throws StoreException{
        setMyController(controller);
        this.owningFrame = desktopView;
        pcSupport = new PropertyChangeSupport(this);
        //centre appointments view relative to desktop;
        this.view = new AppointmentMigratorView(this);
        super.centreViewOnDesktop(desktopView, view);
        this.view.addInternalFrameClosingListener(); 
        this.view.initialiseView();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        File file = null;
        BufferedWriter bw = null;
        if (e.getActionCommand().equals(
                AppointmentViewControllerActionEvent.
                        APPOINTMENTS_VIEW_CLOSED.toString())){
            /**
             * APPOINTMENTS_VIEW_CLOSED
             */
            ActionEvent actionEvent = new ActionEvent(
                    this,ActionEvent.ACTION_PERFORMED,
                    DesktopViewControllerActionEvent.VIEW_CLOSED_NOTIFICATION.toString());
            this.myController.actionPerformed(actionEvent);   
        }
        else if (e.getActionCommand().equals(
                ViewController.MigratorViewControllerActionEvent.
                        APPOINTMENT_MIGRATOR_REQUEST.toString())){
            
            setMigrationDescriptorFromView(getView().getMigrationDescriptor());
            CSVMigrationManager mm = new CSVMigrationManager();
            mm.getAppointment().setData(
                    getMigrationDescriptorFromView().getAppointment().getData());
            mm.getPatient().setData(
                    getMigrationDescriptorFromView().getPatient().getData());
            try {
                Path currentRelativePath = Paths.get("");
                String s = currentRelativePath.toAbsolutePath().toString();
                file = new File ("database/databasePath.txt");
                if (file.exists()){
                    file.delete();
                }
                if (file.createNewFile()){
                    FileWriter fw = new FileWriter(file);
                    bw = new BufferedWriter(fw);
                    bw.write(getMigrationDescriptorFromView().getTarget().getData());
                    bw.close();
                    mm.action(Store.CSVMigrationMethod.CSV_APPOINTMENT_FILE_CONVERTER);
                    mm.action(Store.CSVMigrationMethod.CSV_PATIENT_FILE_CONVERTER);
                    IStore store = Store.factory();
                    IMigrationManager  manager = store.getMigrationManager();
                    manager.setAppointments(mm.getAppointments());
                    manager.setPatients(mm.getPatients());
                    
                    try{
                        manager.action(Store.MigrationMethod.APPOINTMENT_TABLE_DROP);
                    }
                    catch (StoreException ex){
                        
                    }
                    manager.action(Store.MigrationMethod.APPOINTMENT_TABLE_CREATE);
                    manager.action(Store.MigrationMethod.APPOINTMENT_TABLE_POPULATE);
                    
                    
                    try{
                       manager.action(Store.MigrationMethod.PATIENT_TABLE_DROP); 
                    }
                    catch (StoreException ex){
                        
                    }
                    manager.action(Store.MigrationMethod.PATIENT_TABLE_CREATE);
                    manager.action(Store.MigrationMethod.PATIENT_TABLE_POPULATE);
                    /*
                    manager.action(Store.MigrationMethod.PATIENT_TABLE_TIDY);
                    manager.action(Store.MigrationMethod.APPOINTMENT_TABLE_INTEGRITY_CHECK);
                    */
                }   
            }
            catch (IOException ex){
               JOptionPane.showMessageDialog(null,
                                          new ErrorMessagePanel(ex.getMessage())); 
                
            }
            catch (StoreException ex){
                JOptionPane.showMessageDialog(null,
                                          new ErrorMessagePanel(ex.getMessage()));
            }
            finally{
                try{
                    if (bw!=null){
                        bw.close();
                    }
                }
                catch (IOException exFinal){
                    
                }
            }
        }
    }
    
    public MigrationDescriptor getMigrationDescriptorFromView(){
        return this.migrationDescriptorFromView;
    }
    private void setMigrationDescriptorFromView(MigrationDescriptor e){
        this.migrationDescriptorFromView = e;
    }
    
    public View getView( ){
        return view;
    }
    
    private ActionListener getMyController(){
        return this.myController;
    }
    private void setMyController(ActionListener myController){
        this.myController = myController;
    }
}
