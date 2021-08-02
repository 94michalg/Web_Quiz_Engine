package engine;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
//    @NotNull
    @Column(nullable = false)
    private String email;

//    @NotNull(message = "You have to specify a password")
//    @Length(min = 5, message = "Password must have at least 5 characters")
    @Column(nullable = false)
    private String password;

    //    @Column
//    private ArrayList<UserCompletions> userCompletionsArrayList = new ArrayList<>();
//
//    public ArrayList<UserCompletions> getUserCompletionsArrayList() {
//        return userCompletionsArrayList;
//    }
//
//    public void setUserCompletionsArrayList(ArrayList<UserCompletions> userCompletionsArrayList) {
//        this.userCompletionsArrayList = userCompletionsArrayList;
//    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public void addNewCompletion(Long id) {
//        LocalDateTime localDateTime = LocalDateTime.now();
//        UserCompletions userCompletions = new UserCompletions();
//        userCompletions.setLocalDateTime(localDateTime);
//        userCompletions.setId(id);
//        this.getUserCompletionsSet().add(userCompletions);
//    }
//
//    public void printAllCompletions() {
//        for (UserCompletions uc: this.getUserCompletionsSet()) {
//            System.out.println(uc.toString());
//        }
//    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                ", userCompletionsSet=" + userCompletionsSet +
//                '}';
//    }
}