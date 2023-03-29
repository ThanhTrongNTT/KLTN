package hcmute.nhom.kltn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String street;
    private String district;
    private String city;
}
