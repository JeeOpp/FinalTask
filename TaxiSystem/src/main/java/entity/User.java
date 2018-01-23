package entity;

public class User {
    private int id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private boolean banStatus;
    private String role;


    public User() {
    }

    public User(int id){
        this.id = id;
    }

    public User(String role){
        this.role = role;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(int id, String login, String password){
        this(login,password);
        this.id = id;
    }

    public User(int id, String role){
        this(role);
        this.id = id;
    }

    public User(int id, boolean banStatus, String role){
        this(id,role);
        this.banStatus = banStatus;
    }

    public User(String login, String password, String firstName, String lastName) {
        this(login,password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String login, String password, String firstName, String lastName, String role) {
        this(login,password,firstName,lastName);
        this.role = role;
    }

    public User(int id, String login, String password, String firstName, String lastName){
        this(login, password, firstName, lastName);
        this.id=id;
    }

    public User(int id, String login, String password, String firstName, String lastName,boolean banStatus, String role) {
        this(id,login,password,firstName,lastName);
        this.banStatus = banStatus;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean getBanStatus() {
        return banStatus;
    }

    public String getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBanStatus(boolean banStatus) {
        this.banStatus = banStatus;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
