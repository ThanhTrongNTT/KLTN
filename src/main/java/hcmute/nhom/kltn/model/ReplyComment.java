package hcmute.nhom.kltn.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * Class ReplyComment.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Entity
@Table(name = "T_REPLY_COMMENT")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyComment extends AbstractAuditModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ID", nullable = false)
    private String id;
    @ManyToOne
    @JoinColumn(name = "COMMENT_ID", nullable = false)
    private Comment comment;
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User author;
    @Column(name = "CONTENT")
    private String content;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "IMAGE_ID")
    private MediaFile image;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "VIDEO_ID")
    private MediaFile video;
    @Column(name = "LIKE_STATUS")
    private Integer likeStatus;
    @Column(name = "REMOVAL_FLAG", nullable = false, length = 1)
    private Boolean removalFlag;
}
