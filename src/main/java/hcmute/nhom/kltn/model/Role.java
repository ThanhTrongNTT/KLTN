package hcmute.nhom.kltn.model;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

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
public class Role extends AbstractAuditModel implements Serializable {
    /**
     * Role entity.
     */

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ID", nullable = false)
    private String roleId;
    @Column(name = "ROLE_NAME")
    private String roleName;
    @Column(name = "ADMIN_FLAG", length = 1, nullable = false)
    private Integer adminFlag;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    @Column(name = "REMOVAL_FLAG", nullable = false, length = 1)
    private Boolean removalFlag = false;
}
