package mg.recharge.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class CreditCode {
    @DatabaseField(useGetSet = true ,generatedId = true)
    private Integer id;
    @DatabaseField
    private String code;
    @DatabaseField
    private String operator;
    @DatabaseField
    private String date;

    public CreditCode(Integer id, String code, String operator, String date) {
        this.id = id;
        this.code = code;
        this.operator = operator;
        this.date = date;
    }

    public CreditCode() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
