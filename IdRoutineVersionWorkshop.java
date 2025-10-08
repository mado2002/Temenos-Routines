package test.cons.acc;

import com.temenos.t24.api.complex.eb.templatehook.TransactionContext;
import com.temenos.t24.api.hook.system.RecordLifecycle;

/**
 * TODO: Document me!
 *
 * @author atmahmoud
 *
 */
public class IdRoutineVersionWorkshop extends RecordLifecycle {

    @Override
    public String checkId(String currentRecordId, TransactionContext transactionContext) {
        String prefix="";
        if ( !(currentRecordId.equals("L")||currentRecordId.equals("%ENQUIRY"))) {
             prefix ="THE.TRG";
        }
        return prefix+currentRecordId;
    }

}
