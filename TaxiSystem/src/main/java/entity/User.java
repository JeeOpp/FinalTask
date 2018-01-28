package entity;

/**
 * Bean, contains information about all the users registered in taxi system.
 */
public class User {
    private int id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private boolean banStatus;
    private String role;

    public User(){}

    public User(UserBuilder userBuilder){
        this.id = userBuilder.id;
        this.login = userBuilder.login;
        this.password = userBuilder.password;
        this.firstName = userBuilder.firstName;
        this.lastName = userBuilder.lastName;
        this.banStatus = userBuilder.banStatus;
        this.role = userBuilder.role;
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

    public static class UserBuilder{
        private int id;
        private String login;
        private String password;
        private String firstName;
        private String lastName;
        private boolean banStatus;
        private String role;

        public UserBuilder(){}

        public UserBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public UserBuilder setLogin(String login) {
            this.login = login;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder setBanStatus(boolean banStatus) {
            this.banStatus = banStatus;
            return this;
        }

        public UserBuilder setRole(String role) {
            this.role = role;
            return this;
        }
        public User build(){
            return new User(this);
        }
    }
}
