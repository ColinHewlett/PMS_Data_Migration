/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicpmsdatamigrator.controller;

import clinicpmsdatamigrator.model.Patient;
import clinicpmsdatamigrator.store.CSVMigrationManager;
import clinicpmsdatamigrator.store.Store;
import clinicpmsdatamigrator.store.exceptions.StoreException;
import clinicpmsdatamigrator.view.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author colin
 */
public class PatientViewController extends ViewController {
    private MigrationDescriptor migrationDescriptorFromView = null;
    private ActionListener myController = null;
    private View view = null;
    private JFrame owningFrame = null;
    private PropertyChangeSupport pcSupport = null;
    
    public PatientViewController(DesktopViewController controller, JFrame desktopView)throws StoreException{

    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
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

