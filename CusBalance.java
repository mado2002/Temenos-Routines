package initial.pkg;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.temenos.t24.api.complex.eb.enquiryhook.EnquiryContext;
import com.temenos.t24.api.complex.eb.enquiryhook.FilterCriteria;
import com.temenos.t24.api.hook.system.Enquiry;
import com.temenos.t24.api.records.account.AccountRecord;
import com.temenos.t24.api.records.customer.CustomerRecord;
import com.temenos.t24.api.system.DataAccess;

/**
 * TODO: Document me!
 *
 * @author mwbarakat
 *
 */
public class CusBalance extends Enquiry {
    private static final Logger logger = Logger.getLogger(AcBuild.class.getName());

    @Override
    public List<String> setIds(List<FilterCriteria> filterCriteria, EnquiryContext enquiryContext) {
        String customerId="";
        String mnemonic="";
        List<String> outDetails=new ArrayList<>();
        List<String> accountList;
        for(FilterCriteria filter:filterCriteria)
        {
            if(filter.getFieldname().equals("customerId"))
            {
                customerId=filter.getValue();
            }
        }
        DataAccess da=new DataAccess(this);
        Double balance=0.0;

        if(!customerId.isEmpty())
        {
            logger.info("Customer Id= "+customerId);
            CustomerRecord customerRecord=new CustomerRecord(da.getRecord("CUSTOMER", customerId));
            mnemonic=customerRecord.getMnemonic().getValue();
            accountList=da.selectRecords("", "ACCOUNT", "", "WITH CUSTOMER EQ "+customerId);
            
            if(!accountList.isEmpty())
            {
                for(String account:accountList)
                {  logger.info("Account: "+account);
                    AccountRecord accountRecord=new AccountRecord(da.getRecord("ACCOUNT", account));
                    String balanceStr=accountRecord.getWorkingBalance().getValue();
                    logger.info("Balance= "+balanceStr);
                    if(!balanceStr.isEmpty()){
                        balance+=Double.parseDouble(balanceStr);
                    }
                }
            }
        }
        String out=mnemonic+"*"+String.valueOf(balance);
        logger.info("Out"+out);
        outDetails.add(out);
        return outDetails;
        
    }

}
