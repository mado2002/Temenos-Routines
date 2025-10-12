package initial.pkg;

import com.temenos.api.TStructure;
import com.temenos.api.TValidationResponse;
import com.temenos.t24.api.complex.eb.templatehook.TransactionContext;
import com.temenos.t24.api.hook.system.RecordLifecycle;
import com.temenos.t24.api.records.account.AccountRecord;
import com.temenos.t24.api.records.fundstransfer.FundsTransferRecord;
import com.temenos.t24.api.system.DataAccess;

/**
 * TODO: Document me!
 *
 * @author mwbarakat
 *
 */
public class DebitGTWorkingBalance extends RecordLifecycle {

    @Override
    public TValidationResponse validateRecord(String application, String currentRecordId, TStructure currentRecord,
            TStructure unauthorisedRecord, TStructure liveRecord, TransactionContext transactionContext) {
        FundsTransferRecord ft=new FundsTransferRecord(currentRecord);
        DataAccess da=new DataAccess(this);
        String debitAccountNo=ft.getDebitAcctNo().getValue();
        String debitAmount=ft.getDebitAmount().getValue();
        double debitAmountNum=Double.parseDouble(debitAmount);
        if(!debitAccountNo.isEmpty())
        {
            // get the account record
            AccountRecord acc=new AccountRecord(da.getRecord("ACCOUNT", debitAccountNo));
            String workingBalance=acc.getWorkingBalance().getValue();
            double workingBalanceNum=Double.parseDouble(workingBalance);
            if(debitAmountNum>workingBalanceNum)
            {
                ft.getDebitAmount().setError("Debit Amount Cannot be greater than Working Balance");
            }
        }
        return ft.getValidationResponse();
    }
    

}
