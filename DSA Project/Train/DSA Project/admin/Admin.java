package admin;

 public class Admin{
  private int adminId;
    private String adminName;
    private String username;
    private String passwordHash;
    private String email;
    private String phoneNumber;
    private String accountStatus;
 
    // constructor
    Admin(int a,String n,String u,String p,String e,String pN,String aS ){

        this.adminId = a;
        this.adminName = n;
        this.username = u;
        this.passwordHash = p;
        this.email = e;
        this.phoneNumber = pN;
        this.accountStatus = aS;


    }

    // Getters and Setters

public int getAdminId() {
    return adminId;
}

public void setAdminId(int adminId) {
    this.adminId = adminId;
}

public String getAdminName() {
    return adminName;
}

public void setAdminName(String adminName) {
    this.adminName = adminName;
}

public String getUsername() {
    return username;
}

public void setUsername(String username) {
    this.username = username;
}

public String getPasswordHash() {
    return passwordHash;
}

public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public String getPhoneNumber() {
    return phoneNumber;
}

public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
}

public String getAccountStatus() {
    return accountStatus;
}

public void setAccountStatus(String accountStatus) {
    this.accountStatus = accountStatus;
}








}

