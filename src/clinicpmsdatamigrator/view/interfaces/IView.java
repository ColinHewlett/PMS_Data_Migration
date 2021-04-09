/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicpmsdatamigrator.view.interfaces;

import clinicpmsdatamigrator.controller.MigrationDescriptor;
//import java.awt.event.ActionListener;

/**
 *
 * @author colin
 */
public interface IView {  
    /**
     * Enables communication of property change and action listener events
     * between view and its controller
     * @return EntityDescriptor object contained in the PropertyChangeEvent received by the view
     */
    public MigrationDescriptor getMigrationDescriptor(); 
    public void initialiseView();
}
