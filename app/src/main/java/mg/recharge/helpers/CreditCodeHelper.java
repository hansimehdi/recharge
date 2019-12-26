package mg.recharge.helpers;

import java.io.Serializable;

public class CreditCodeHelper implements Serializable {
    private String creditCode;
    private final int CREDIT_CODE_LENGTH = 14;

    public CreditCodeHelper(String creditCode) {
        this.creditCode = creditCode;
    }

    public CreditCodeHelper() {
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public void formatCode(){
        this.setCreditCode(this.creditCode.replaceAll("\\s+",""));
    }

    public boolean isValidCode(){
        if (this.creditCode.length()!=this.CREDIT_CODE_LENGTH){
            return false;
        } else {
            return true;
        }
    }
}
