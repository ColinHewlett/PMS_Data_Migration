/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicpmsdatamigrator.view;

import clinicpmsdatamigrator.view.interfaces.IView;
import clinicpmsdatamigrator.view.interfaces.IViewInternalFrameListener;
import java.beans.PropertyChangeListener;
import java.time.format.DateTimeFormatter;
import javax.swing.JInternalFrame;

/**
 *
 * @author colin
 */
public abstract class View extends JInternalFrame
                           implements PropertyChangeListener,IView, IViewInternalFrameListener{
    
    public View(){
        super("Appointments view",true,true,true,true);
        
    }
}
