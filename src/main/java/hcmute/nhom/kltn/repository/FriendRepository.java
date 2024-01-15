package hcmute.nhom.kltn.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import hcmute.nhom.kltn.model.Friend;

/**
 * Class FriendRepository.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
public interface FriendRepository extends AbstractRepository<Friend, String> {

    @Query(value = "SELECT * FROM T_FRIEND f INNER JOIN T_USER u ON f.USER_ID = u.id WHERE u.userName = :userName"
            + "AND f.STATUS = 0 AND f.removal_flag = 0",
            nativeQuery = true)
    List<Friend> getFriendReceive(String userName);

    @Query(value = "SELECT * FROM T_FRIEND f INNER JOIN T_USER u ON f.FRIEND_ID = u.id WHERE u.userName = :userName"
            + "AND f.STATUS = 0 AND f.removal_flag = 0",
            nativeQuery = true)
    List<Friend> getFriendRequest(String userName);

    @Query(value = "SELECT * FROM T_FRIEND f "
            + "JOIN T_USER u ON f.USER_ID = u.id"
            + "JOIN T_USER friend ON f.USER_ID = friend.id"
            + " WHERE u.userName = :userName AND friend.userName = :friendUserName"
            + "AND f.STATUS = 1 AND f.removal_flag = 0",
            nativeQuery = true)
    Optional<Friend> getFriendByUserName(String userName, String friendUserName);

    @Query(value = "SELECT * FROM T_FRIEND f "
            + "JOIN T_USER u ON f.USER_ID = u.id"
            + " WHERE u.userName = :userName"
            + "AND f.STATUS = 2 AND f.removal_flag = 0",
            nativeQuery = true)
    List<Friend> getBlockFriendByUserName(String userName);

    @Query(value = "SELECT * FROM T_FRIEND "
            + " WHERE user_id = :userId"
            + "AND f.STATUS = 1 AND f.removal_flag = 0",
            nativeQuery = true)
    List<Friend> getFriendByUserId(String userId);
}
