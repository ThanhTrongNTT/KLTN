package hcmute.nhom.kltn.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * Class Address.
 * Create by: IntelliJ IDEA
 *
 * @author : ThanhTrong
 **/
@Entity
@Table(name = "T_ADDRESS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address extends AbstractAuditModel implements java.io.Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "ADDRESS_ID", nullable = false, columnDefinition = "uuid")
    private UUID id;
    @Column(name = "STREET")
    private String street;
    @Column(name = "DISTRICT")
    private String district;
    @Column(name = "CITY")
    private String city;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "POSTAL_CODE")
    private String postalCode;
    @Column(name = "REMOVAL_FLAG", nullable = false, length = 1)
    private Boolean removalFlag  = false;
}
