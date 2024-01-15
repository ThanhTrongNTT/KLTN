package hcmute.nhom.kltn.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import hcmute.nhom.kltn.enums.GenderType;

/**
 * Class UserProfile.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Entity
@Table(name = "T_USER_PROFILE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile extends AbstractAuditModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ID", nullable = false)
    private String id;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "BIRTH_DAY")
    private Date birthDay;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AVATAR_ID")
    private MediaFile avatar;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COVER_ID")
    private MediaFile cover;

    @Column(name = "GENDER")
    private GenderType gender;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    @OneToOne(mappedBy = "userProfile")
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "REMOVAL_FLAG", nullable = false, length = 1)
    private Boolean removalFlag = false;
}
