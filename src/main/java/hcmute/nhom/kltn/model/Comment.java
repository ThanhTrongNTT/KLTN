package hcmute.nhom.kltn.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import hcmute.nhom.kltn.enums.LikeStatusType;

/**
 * Class Comment.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Entity
@Table(name = "T_COMMENT")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends AbstractAuditModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "ID", nullable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User author;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "IMAGE")
    private String image;
    @Column(name = "VIDEO")
    private String video;
    @Column(name = "LIKE_STATUS")
    private LikeStatusType likeStatus;
    @ManyToMany(mappedBy = "likedComments") // Nhiều người dùng thích comment này
    private List<User> likedByUsers;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<ReplyComment> replies;
    @Column(name = "REMOVAL_FLAG", nullable = false, length = 1)
    private Boolean removalFlag = false;
}
