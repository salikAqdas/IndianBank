import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Account {

    long AccountNumber;
    float balance;
    int userId;
    Account(long AccountNumber , float balance , int userId){
        this.AccountNumber = AccountNumber;
        this.balance = balance;
        this.userId = userId;
    }
    public static Account createAccount(users user) throws Exception{
        Scanner sc = new Scanner(System.in);
        long an = System.currentTimeMillis();
        System.out.print("Enter money to deposit in the account: ");
        float money = sc.nextFloat();
        String query = "insert into account values("+an+","+ money + "," +user.getUserid()+")";
        DbConnect.execute(query);

        return new Account(an,money,user.getUserid());
    }
    public static String getAccount(users user) throws Exception{
        String query = "select * from account where userid = "+user.getUserid();
        ResultSet rs = DbConnect.execute(query);
        if(!rs.next()){
            return "No Account Found";
        }
        StringBuilder ret = new StringBuilder("Account Number\t\tBalance\n______________________________________________________________________\n");
        do{
           long an = rs.getLong("accountnumber");
           float balance = rs.getFloat("balance");
           ret.append(an).append("\t\t").append(balance);
        }while(rs.next());
        ret.append("\n");
        return ret.toString();
    }
    public static float fetchbalance(users user, String accountNumber) throws Exception{
        String query = "select * from account where accountnumber = '"+accountNumber.trim()+"' and userid ="+user.getUserid();
        ResultSet rs = DbConnect.execute(query);
        rs.next();
        float balance = rs.getFloat("balance");
        return balance;
    }
    public static void withdraw(users user, String accountNumber , float amount){
        String query;
        query = "select * from account where userid = "+user.getUserid() +" and accountnumber = '"+accountNumber+"' and balance >="+amount;
        try{
            ResultSet rs = DbConnect.execute(query);
            if(!rs.next()){
                System.out.println("You don't have sufficient balance");
            }
            else{
                query = "update account set balance = balance - "+amount+" where accountnumber = '"+accountNumber+"'";
                DbConnect.executeQuery(query);
                long id = System.currentTimeMillis();
                LocalDate currentDate = LocalDate.now();
                query = "insert into transaction values('"+id+"' ,'"+currentDate+"' ,'"+amount+"' ,'debit' ,"+ user.getUserid()+",'"+accountNumber+"')";
                DbConnect.executeQuery(query);
                System.out.println("Withdrawal Successful");
            }
        }
        catch (SQLException s){
            System.out.println("In account: "+s.getMessage());
        }
    }
    public static String viewLastTransaction(users user, String accountNum, int count) throws SQLException{

        String query = "SELECT transactionid, date, amount, type, accountnumber FROM transaction order by date desc LIMIT "+count+";";
        ResultSet rs = DbConnect.execute(query);
        if(!rs.next()){
            return "No Transactions Found";
        }
        StringBuilder result = new StringBuilder("Transaction Id\t\t\tDate\t\t\tType\t\t\tAmount\n");
        result.append("__________________________________________________________________________________________________________\n");
        do{
            result.append(rs.getString("transactionid")).append("\t\t\t").append(rs.getDate("date")).append("\t\t\t").append(rs.getString("type")).append("\t\t\t").append(rs.getFloat("amount")).append("\n");
        }
        while(rs.next());

        return result.toString();


    }
}
