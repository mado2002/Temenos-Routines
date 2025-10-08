package initial.pkg;

import com.temenos.api.TStructure;
import com.temenos.api.TValidationResponse;
import com.temenos.t24.api.complex.eb.templatehook.TransactionContext;
import com.temenos.t24.api.hook.system.RecordLifecycle;
import com.temenos.t24.api.records.account.AccountRecord;
import com.temenos.t24.api.records.fundstransfer.FundsTransferRecord;
import com.temenos.t24.api.system.DataAccess;
import java.util.logging.Logger;

/**
 * TODO: Document me!
 *
 * @author mwbarakat
 *
 */
public class FT extends RecordLifecycle {
    private static final Logger logger = Logger.getLogger(AcBuild.class.getName());

    @Override
    public TValidationResponse validateRecord(String application, String currentRecordId, TStructure currentRecord,
            TStructure unauthorisedRecord, TStructure liveRecord, TransactionContext transactionContext) {
        FundsTransferRecord ft=new FundsTransferRecord(currentRecord);
        DataAccess da=new DataAccess(this);
        String debitAccountNo=ft.getDebitAcctNo().getValue();
        if(!debitAccountNo.isEmpty())
        {
            // get the account record
            AccountRecord acc=new AccountRecord(da.getRecord("ACCOUNT", debitAccountNo));
            String category =acc.getCategory().getValue();
            logger.info(category);
            if(!category.equalsIgnoreCase("6001")) // note: .equals() doesn't work with temenos.
            {
                ft.getDebitAcctNo().setError("Debit Account is not a Saving Account");
            }
        }
        return ft.getValidationResponse();
       
    }

  
    

}
