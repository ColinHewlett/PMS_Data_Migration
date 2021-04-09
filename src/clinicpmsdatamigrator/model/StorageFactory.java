/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicpmsdatamigrator.model;

import clinicpmsdatamigrator.store.CSVStore;
import clinicpmsdatamigrator.store.AccessStore;
import clinicpmsdatamigrator.store.PostgreSQLStore;
import clinicpmsdatamigrator.store.SQLExpressStore;
import clinicpmsdatamigrator.store.exceptions.StoreException;
import clinicpmsdatamigrator.store.Store;
import clinicpmsdatamigrator.store.interfaces.IStore;

/**
 *
 * @author colin
 */
public class StorageFactory {
    public StorageFactory(Store.Storage type)  throws StoreException{
        IStore store = null;
        switch (type){
            //case CSV -> CSVStore.getInstance();
            case ACCESS -> AccessStore.getInstance();
            case POSTGRES -> PostgreSQLStore.getInstance();
            case SQL_EXPRESS -> SQLExpressStore.getInstance();  
            case UNDEFINED_DATABASE -> throw new StoreException(
                    "Undefined database", Store.ExceptionType.UNDEFINED_DATABASE);
        }
    }
}
