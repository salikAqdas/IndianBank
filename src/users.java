public class users {
    int userid;
    String username;
    String email;
    users(){

    }
    users(int id, String un , String em){
        userid = id;
        username = un;
        email = em;
    }
    int getUserid(){
        return this.userid;
    }
    String getEmail(){
        return this.email;
    }
    public static users createUser(int userId , String userName , String email){
        return new users(userId,userName,email);
    }
}
