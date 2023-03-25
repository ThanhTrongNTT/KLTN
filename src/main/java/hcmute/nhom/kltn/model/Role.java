package hcmute.nhom.kltn.model;

import hcmute.nhom.kltn.common.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "T_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private RoleName roleName;
}
