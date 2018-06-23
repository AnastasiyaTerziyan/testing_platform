package ru.itis.terziyan.diplom.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @GenericGenerator(
            name = "userSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "User_SEQUENCE"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "100"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @GeneratedValue(generator = "userSequenceGenerator")
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "*Это обязательное поле")
    @Length(max = 17, message = "*Поле должно быть не больше 18 символов длинной")
    private String name;


    @Column(name = "active")
    private int active = 1;

    @Column(name = "last_name")
    @NotEmpty(message = "*Это обязательное поле")
    @Length(max = 18, message = "*Поле должно быть не больше 18 символов длинной")
    private String lastName;

    @Column(name = "mid_name")
    @Length(max = 18, message = "*Поле должно быть не больше 18 символов длинной")
    private String midName;

    @Column(name = "email")
    @Email(message = "*Пожалуйста введите корректный email")
    @NotEmpty(message = "*Это поле не должно быть пустым")
    @Length(max = 100, message = "*Поле должно быть не больше 100 символов длинной")
    private String email;

    @Column(name = "password")
    @Length(min = 6, max = 250, message = "*Пароль не менее 6 символов длинной и не больше 250")
    @NotEmpty(message = "*Пожалуйста введите пароль")
    private String password;

    @Transient
    private transient String passwordConfirm;

    @Transient
    private transient String roleMessage;

    @Transient
    private transient String confirmMessage;

    @Column(name = "phone_number")
    @NotEmpty(message = "*Это поле не должно быть пустым")
    private String phoneNumber;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "position")
    private String position;

    @Column(name = "is_student")
    private boolean isStudent = false;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    @JsonIgnore
    public Set<Materials> getMaterials() {
        return materials;
    }

    public void setMaterials(Set<Materials> materials) {
        this.materials = materials;
    }

    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<Materials> materials;


    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<TestAnswer> testAnswers;

    @Column(name = "coins")
    private Double coins;

    @OneToMany(mappedBy = "user")
    private Set<UserCompetency> userCompetencies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
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

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getRoleMessage() {
        return roleMessage;
    }

    public void setRoleMessage(String roleMessage) {
        this.roleMessage = roleMessage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonIgnore
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getConfirmMessage() {
        return confirmMessage;
    }

    public void setConfirmMessage(String confirmMessage) {
        this.confirmMessage = confirmMessage;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }

    public Set<TestAnswer> getTestAnswers() {
        return testAnswers;
    }

    public void setTestAnswers(Set<TestAnswer> testAnswers) {
        this.testAnswers = testAnswers;
    }

    public Double getCoins() {
        return coins;
    }

    public void setCoins(Double coins) {
        this.coins = coins;
    }

    public Set<UserCompetency> getUserCompetencies() {
        return userCompetencies;
    }

    public void setUserCompetencies(Set<UserCompetency> userCompetencies) {
        this.userCompetencies = userCompetencies;
    }
}
