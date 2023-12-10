package hcmute.nhom.kltn.services;

import java.util.List;
import java.util.UUID;
import hcmute.nhom.kltn.common.payload.ListResponse;
import hcmute.nhom.kltn.dto.FriendDTO;
import hcmute.nhom.kltn.model.Friend;

/**
 * Class FriendService.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
public interface FriendService extends AbstractService<FriendDTO, Friend> {

    /**
     * getFriendByUserName.
     * @param userName String
     * @return ListResponse<FriendDTO>
     */
    ListResponse<FriendDTO> getSuggestFriend(String userName);

    /**
     * Get Friend Receive.
     * @param userName String
     * @return ListResponse<FriendDTO>
     */
    ListResponse<FriendDTO> getFriendReceive(String userName);

    /**
     * Get Friend Request.
     * @param userName String
     * @return ListResponse<FriendDTO>
     */
    ListResponse<FriendDTO> getFriendRequest(String userName);

    /**
     * Add friend.
     * @param userNameSession String
     * @param userName String
     * @return FriendDTO
     */
    FriendDTO addFriend(String userNameSession, String userName);

    /**
     * Accept friend.
     * @param id String
     * @return Boolean
     */
    Boolean acceptFriend(String userName, String id);

    /**
     * Reject friend.
     * @param id String
     * @return Boolean
     */
    Boolean rejectFriend(String userName, String id);

    /**
     * Un friend.
     * @param userName String
     * @param friendUserName String
     * @return Boolean
     */
    Boolean unFriend(String userName, String friendUserName);

    /**
     * Block friend.
     * @param userName String
     * @param friendUserName String
     * @return Boolean
     */
    Boolean blockFriend(String userName, String friendUserName);

    /**
     * Un block friend.
     * @param userName String
     * @param friendUserName String
     * @return Boolean
     */
    Boolean unBlockFriend(String userName, String friendUserName);

    /**
     * Get Block Friend.
     * @param userName String
     * @return ListResponse<FriendDTO>
     */
    ListResponse<FriendDTO> getBlockFriend(String userName);

    /**
     * Get Friend By UserName.
     * @param userId UUID
     * @return ListResponse<FriendDTO>
     */
    List<FriendDTO> getFriendByUserName(String userId);
}
