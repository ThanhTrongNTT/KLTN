package hcmute.nhom.kltn.model;

import hcmute.nhom.kltn.common.GenderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "T_user")
@EqualsAndHashCode(callSuper = false)
public class User extends AbstractAuditModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID", nullable = false)
    private String id;
    @Column(name = "email",nullable = false)
    private String email;
    @Column(name = "userName")
    private String userName;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @Column(name= "phoneNumber", length = 10)
    private String phoneNumber;
    @Column(name = "birthDay")
    private Date birthDay;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "gender")
    private GenderType gender;
    @Column(name = "description")
    private String description;

}
