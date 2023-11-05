package  hcmute.nhom.kltn.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class Role.
 * Create by: IntelliJ IDEA
 *
 * @author : ThanhTrong
 **/
@Entity
@Table(name = "T_ROLE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role extends AbstractAuditModel implements java.io.Serializable {
    /**
     * Role entity.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID", nullable = false)
    private Long roleId;
    @Column(name = "ROLE_NAME")
    private String roleName;
    @Column(name = "ADMIN_FLAG", length = 1, nullable = false)
    private Integer adminFlag;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    @Column(name = "REMOVAL_FLAG", nullable = false, length = 1)
    private Boolean removalFlag = false;
}
