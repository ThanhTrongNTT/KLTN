package hcmute.nhom.kltn.model;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import hcmute.nhom.kltn.enums.LikeStatusType;

/**
 * Class Post.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Table(name = "T_POST")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends AbstractAuditModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ID", nullable = false)
    private String id;
    @Column(name = "CONTENT")
    private String content;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "IMAGE_ID")
    private MediaFile image;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "VIDEO_ID")
    private MediaFile video;
    @Column(name = "CATEGORY")
    private String category;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User author;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
    @ManyToMany(mappedBy = "likedPosts") // Nhiều người dùng thích bài viết này
    private List<User> likedByUsers;
    @Column(name = "LIKE_STATUS")
    private LikeStatusType likeStatus;
    @Column(name = "REMOVAL_FLAG", nullable = false, length = 1)
    private Boolean removalFlag = false;
}
