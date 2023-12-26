import java.sql.ResultSet;
import java.util.Scanner;

public class Login {
    public static void main(String[] args) {
        String accountNum;
        Scanner sc = new Scanner(System.in);
        try {
            users user;
            String username,pass;
            System.out.print("Enter Username: ");
            username = sc.next();
            System.out.print("Enter password: ");
            pass = sc.next();
            String query = "select * from users where username = '"+ username + "' and password = '"+pass+"'";
            try {
               ResultSet rs =  DbConnect.execute(query);
               if (!rs.next()){
                   System.out.println("No data found");
               }
               else{
                   do {
                       int id = rs.getInt("userid");
                       String foundUsername = rs.getString("username");
                       String email = rs.getString("email");
                       user = users.createUser(id,foundUsername,email);
                   } while (rs.next());
                   System.out.println("Welcome " + username);
                   System.out.println("=================================================================");
                   int choice;
                   do {
                       System.out.println("Choose an option: ");
                       System.out.println("1. Create new account \n2. List my Accounts \n3. Fetch Balance\n4. Withdraw \n5. View Last 5 Transaction \n6. Logout");
                       System.out.print("Enter your choice: ");
                       choice = sc.nextInt();
                       if (choice < 1 || choice > 6) {
                           System.out.println("Please enter a valid choice");
                           continue;
                       }
                       switch (choice){
                           case 1:
                               Account newAccount = Account.createAccount(user);
                               System.out.println("Account Created: \nAccount Number = "+newAccount.AccountNumber+"\nBalance: "+newAccount.balance);
                               break;
                           case 2:
                               String listAccount = Account.getAccount(user);
                               System.out.println(listAccount);
                               break;
                           case 3:
                               System.out.print("Enter account number: ");
                               accountNum = sc.next();
                               System.out.println("\nYour account Balance: "+ Account.fetchbalance(user,accountNum) + "\n");
                               break;

                           case 4:
                               System.out.print("Enter account number: ");
                               accountNum = sc.next();
                               System.out.print("Enter amount: ");
                               float amount = sc.nextFloat();
                               Account.withdraw(user,accountNum,amount);
                               break;
                           case 5:
                               System.out.print("Enter account number: ");
                               accountNum = sc.next();
                               String res = Account.viewLastTransaction(user ,accountNum, 5);
                               System.out.println(res);
                               break;
                           case 6: user = null;


                       }
                   }while(choice != 6);
               }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }

        } finally {
            sc.close();
        }


    }
}
