package db.springTempProc;


import lombok.Data;

@Data
public class DbToolOrderVerify {

    @OracleDataType(order = 1)
    private String toolOrderNo;//工具单编号
    @OracleDataType(order = 2)
    private String flag;//核验标志，0成功/1失败
    @OracleDataType(order = 3)
    private String inUserId;
    @OracleDataType(order = 4)
    private String inUserName;
    @OracleDataType(order = 5)
    private String inUserIdcardNo;
    @OracleDataType(order = 6)
    private String verifyChannel;
    @OracleDataType(order = 7)
    private String verifyUserId;
    @OracleDataType(order = 8)
    private String verifyUserName;
    @OracleDataType(order = 9)
    private String verifyUserIdcardNo;
    @OracleDataType(order = 10)
    private String verifyFailReason;
    @OracleDataType(order = 11)
    private String verifyFailRemark;
}
