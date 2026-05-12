package pl.dmcs.rkotas.springbootjsp_iwa2026.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"student", "id"})
@Entity
public class Account {

    @Id
    @GeneratedValue
    private long id;

    private String accountName;

    //Commented out due to simplify http requests sent from angular app
    //@JsonIgnore
    //@OneToOne(mappedBy = "account")
    //private Student student;
    // Commented out due to simplify http requests sent from angular app

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    // Commented out due to simplify http requests sent from angular app
    //public Student getStudent() {
    //    return student;
    //}
    //
    //public void setStudent(Student student) {
    //    this.student = student;
    //}
    // Commented out due to simplify http requests sent from angular app

}










