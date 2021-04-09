/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicpmsdatamigrator.model;

import clinicpmsdatamigrator.store.CSVStore;
import clinicpmsdatamigrator.store.Store;
import clinicpmsdatamigrator.store.exceptions.StoreException;
import clinicpmsdatamigrator.store.interfaces.IStore;
import java.util.ArrayList;

/**
 *
 * @author colin
 */
public class Patients {
    public ArrayList<Patient> getPatients() throws StoreException{
        IStore store = Store.factory();
        return store.readPatients();
    }
}
