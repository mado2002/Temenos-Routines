package test.cons.acc;

import com.temenos.api.TStructure;
import com.temenos.api.TValidationResponse;
import com.temenos.t24.api.complex.eb.templatehook.TransactionContext;
import com.temenos.t24.api.hook.system.RecordLifecycle;
import com.temenos.t24.api.records.account.AccountRecord;
import com.temenos.t24.api.records.customer.CustomerRecord;
import com.temenos.t24.api.system.DataAccess;

/**
 * TODO: Document me!
 *
 * @author atmahmoud
 *
 */
public class InputRtnCheck extends RecordLifecycle {

    @Override
    public TValidationResponse validateRecord(String application, String currentRecordId, TStructure currentRecord,
            TStructure unauthorisedRecord, TStructure liveRecord, TransactionContext transactionContext) {
        AccountRecord accRec = new AccountRecord(currentRecord);
        DataAccess da = new DataAccess(this);
        CustomerRecord cusRec = new CustomerRecord(da.getRecord("CUSTOMER", accRec.getCustomer().getValue()));
        if (!accRec.getCategory().getValue().equalsIgnoreCase("6001")) {
            accRec.getCategory().setError("Should Be Savings 6001");
        }
        if (!cusRec.getSector().getValue().equalsIgnoreCase("1001")) {
            accRec.getCustomer().setError("Should be individual 1001");
        }
        return accRec.getValidationResponse();

    }
}
