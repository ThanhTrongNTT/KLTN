package hcmute.nhom.kltn.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import hcmute.nhom.kltn.common.payload.ListResponse;
import hcmute.nhom.kltn.dto.FriendDTO;
import hcmute.nhom.kltn.dto.UserDTO;
import hcmute.nhom.kltn.enums.FriendshipStatus;
import hcmute.nhom.kltn.exception.NotFoundException;
import hcmute.nhom.kltn.exception.SystemErrorException;
import hcmute.nhom.kltn.mapper.FriendMapper;
import hcmute.nhom.kltn.model.Friend;
import hcmute.nhom.kltn.repository.FriendRepository;
import hcmute.nhom.kltn.services.FriendService;
import hcmute.nhom.kltn.services.UserService;
import hcmute.nhom.kltn.util.Constants;
import hcmute.nhom.kltn.util.Utilities;

/**
 * Class FriendServiceImpl.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Service
@RequiredArgsConstructor
public class FriendServiceImpl
        extends AbstractServiceImpl<FriendRepository, FriendMapper, FriendDTO, Friend>
        implements FriendService {
    private static final Logger logger = LoggerFactory.getLogger(FriendServiceImpl.class);
    private static final String SERVICE_NAME = "FriendService";
    private static final String OUTPUT = "output";
    private static final String USER_NAME = "userName";
    private final FriendRepository friendRepository;
    private final UserService userService;

    @Override
    public FriendMapper getMapper() {
        return FriendMapper.INSTANCE;
    }

    @Override
    public FriendRepository getRepository() {
        return friendRepository;
    }

    @Override
    public ListResponse<FriendDTO> getSuggestFriend(String userName) {
        String method = "getSuggestFriend";
        logger.info(getMessageStart(SERVICE_NAME, method));
        logger.info(getMessageInputParam(SERVICE_NAME, USER_NAME, userName));
        try {
            UserDTO userDTO = userService.findByUserName(userName);
            List<FriendDTO> friendList = findAll();
            friendList.stream().filter(friendDTO -> userDTO.getUserProfile().getAddress().getCity()
                    .equals(friendDTO.getFriendUser().getUserProfile().getAddress().getCity()));
            ListResponse<FriendDTO> friendDTOListResponse = Utilities.createListResponse(friendList);
            logger.debug(getMessageOutputParam(SERVICE_NAME, method, friendDTOListResponse.toString()));
            logger.info(getMessageEnd(SERVICE_NAME, method));
            return friendDTOListResponse;
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error(message);
            logger.info(getMessageEnd(SERVICE_NAME, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public ListResponse<FriendDTO> getFriendReceive(String userName) {
        String method = "getFriendReceive";
        logger.info(getMessageStart(SERVICE_NAME, method));
        logger.info(getMessageInputParam(SERVICE_NAME, USER_NAME, userName));
        try {
            List<Friend> friends = getRepository().getFriendReceive(userName);
            List<FriendDTO> friendDTOS = returnListFriendDTO(friends);
            ListResponse<FriendDTO> output = Utilities.createListResponse(friendDTOS);
            logger.debug(getMessageOutputParam(SERVICE_NAME, OUTPUT, output));
            logger.info(getMessageEnd(SERVICE_NAME, method));
            return output;
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error(message);
            logger.info(getMessageEnd(SERVICE_NAME, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public ListResponse<FriendDTO> getFriendRequest(String userName) {
        String method = "getFriendRequest";
        logger.info(getMessageStart(SERVICE_NAME, method));
        logger.info(getMessageInputParam(SERVICE_NAME, USER_NAME, userName));
        try {
            List<Friend> friends = getRepository().getFriendRequest(userName);
            List<FriendDTO> friendDTOS = returnListFriendDTO(friends);
            ListResponse<FriendDTO> output = Utilities.createListResponse(friendDTOS);
            logger.debug(getMessageOutputParam(SERVICE_NAME, OUTPUT, output));
            logger.info(getMessageEnd(SERVICE_NAME, method));
            return output;
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error(message);
            logger.info(getMessageEnd(SERVICE_NAME, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public FriendDTO addFriend(String userNameSession, String userName) {
        String method = "addFriend";
        logger.info(getMessageStart(SERVICE_NAME, method));
        logger.info(getMessageInputParam(SERVICE_NAME, "userNameSession", userNameSession));
        logger.info(getMessageInputParam(SERVICE_NAME, USER_NAME, userName));
        try {
            FriendDTO friendDTO = new FriendDTO();
            friendDTO.setFriendUser(userService.findByUserName(userName));
            friendDTO.setUser(userService.findByUserName(userNameSession));
            friendDTO.setStatus(FriendshipStatus.PENDING);
            FriendDTO output = save(friendDTO);
            logger.debug(getMessageOutputParam(SERVICE_NAME, OUTPUT, output.toString()));
            logger.info(getMessageEnd(SERVICE_NAME, method));
            return output;
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error(message);
            logger.info(getMessageEnd(SERVICE_NAME, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public Boolean acceptFriend(String userName, String id) {
        String method = "acceptFriend";
        logger.info(getMessageStart(SERVICE_NAME, method));
        logger.info(getMessageInputParam(SERVICE_NAME, "id", id));
        logger.info(getMessageInputParam(SERVICE_NAME, USER_NAME, userName));
        try {
            FriendDTO friendDTO = findById(id);
            if (friendDTO.getUser().getUserName().equals(userName)) {
                friendDTO.setStatus(FriendshipStatus.ACCEPTED);
                friendDTO = save(friendDTO);
                logger.debug(getMessageOutputParam(SERVICE_NAME, OUTPUT, friendDTO.toString()));
                logger.info(getMessageEnd(SERVICE_NAME, method));
                return Boolean.TRUE;
            }
            logger.debug(getMessageOutputParam(SERVICE_NAME, OUTPUT, friendDTO.toString()));
            logger.info(getMessageEnd(SERVICE_NAME, method));
            return Boolean.FALSE;
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error(message);
            logger.info(getMessageEnd(SERVICE_NAME, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public Boolean rejectFriend(String userName, String id) {
        String method = "rejectFriend";
        logger.info(getMessageStart(SERVICE_NAME, method));
        logger.info(getMessageInputParam(SERVICE_NAME, USER_NAME, userName));
        logger.info(getMessageInputParam(SERVICE_NAME, "id", id));
        try {
            FriendDTO friendDTO = findById(id);
            if (friendDTO.getUser().getUserName().equals(userName)) {
                delete(friendDTO);
                logger.debug(getMessageOutputParam(SERVICE_NAME, OUTPUT, Boolean.TRUE.toString()));
                logger.info(getMessageEnd(SERVICE_NAME, method));
                return Boolean.TRUE;
            }
            logger.debug(getMessageOutputParam(SERVICE_NAME, OUTPUT, Boolean.FALSE.toString()));
            logger.info(getMessageEnd(SERVICE_NAME, method));
            return Boolean.FALSE;
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error(message);
            logger.info(getMessageEnd(SERVICE_NAME, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public Boolean unFriend(String userName, String friendUserName) {
        String method = "unFriend";
        logger.info(getMessageStart(SERVICE_NAME, method));
        logger.info(getMessageInputParam(SERVICE_NAME, USER_NAME, userName));
        logger.info(getMessageInputParam(SERVICE_NAME, "friendUserName", friendUserName));
        try {
            Friend friend = getRepository().getFriendByUserName(userName, friendUserName).orElse(null);
            if (Objects.isNull(friend)) {
                String message = Constants.FRIEND_NOT_FOUND;
                logger.debug(getMessageOutputParam(SERVICE_NAME, OUTPUT, Boolean.FALSE.toString()));
                logger.info(getMessageEnd(SERVICE_NAME, method));
                throw new NotFoundException(message);
            }
            if (friend.getUser().getUserName().equals(userName)) {
                delete(friend.getId());
                logger.debug(getMessageOutputParam(SERVICE_NAME, OUTPUT, Boolean.TRUE.toString()));
                logger.info(getMessageEnd(SERVICE_NAME, method));
                return Boolean.TRUE;
            }
            logger.debug(getMessageOutputParam(SERVICE_NAME, OUTPUT, Boolean.FALSE.toString()));
            logger.info(getMessageEnd(SERVICE_NAME, method));
            return Boolean.FALSE;
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error(message);
            logger.info(getMessageEnd(SERVICE_NAME, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public Boolean blockFriend(String userName, String friendUserName) {
        String method = "blockFriend";
        logger.info(getMessageStart(SERVICE_NAME, method));
        logger.info(getMessageInputParam(SERVICE_NAME, USER_NAME, userName));
        logger.info(getMessageInputParam(SERVICE_NAME, "friendUserName", friendUserName));
        try {
            Friend friend = getRepository().getFriendByUserName(userName, friendUserName).orElse(null);
            if (Objects.isNull(friend)) {
                String message = Constants.FRIEND_NOT_FOUND;
                logger.debug(getMessageOutputParam(SERVICE_NAME, OUTPUT, Boolean.FALSE.toString()));
                logger.info(getMessageEnd(SERVICE_NAME, method));
                throw new NotFoundException(message);
            }
            if (friend.getUser().getUserName().equals(userName)) {
                friend.setStatus(FriendshipStatus.BLOCKED);
                save(friend);
                logger.debug(getMessageOutputParam(SERVICE_NAME, OUTPUT, Boolean.TRUE.toString()));
                logger.info(getMessageEnd(SERVICE_NAME, method));
                return Boolean.TRUE;
            }
            logger.debug(getMessageOutputParam(SERVICE_NAME, OUTPUT, Boolean.FALSE.toString()));
            logger.info(getMessageEnd(SERVICE_NAME, method));
            return Boolean.FALSE;
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error(message);
            logger.info(getMessageEnd(SERVICE_NAME, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public Boolean unBlockFriend(String userName, String friendUserName) {
        String method = "unBlockFriend";
        logger.info(getMessageStart(SERVICE_NAME, method));
        logger.info(getMessageInputParam(SERVICE_NAME, USER_NAME, userName));
        logger.info(getMessageInputParam(SERVICE_NAME, "friendUserName", friendUserName));
        try {
            Friend friend = getRepository().getFriendByUserName(userName, friendUserName).orElse(null);
            if (Objects.isNull(friend)) {
                String message = Constants.FRIEND_NOT_FOUND;
                logger.debug(getMessageOutputParam(SERVICE_NAME, OUTPUT, Boolean.FALSE.toString()));
                logger.info(getMessageEnd(SERVICE_NAME, method));
                throw new NotFoundException(message);
            }
            if (friend.getUser().getUserName().equals(userName)) {
                friend.setStatus(FriendshipStatus.ACCEPTED);
                save(friend);
                logger.debug(getMessageOutputParam(SERVICE_NAME, OUTPUT, Boolean.TRUE.toString()));
                logger.info(getMessageEnd(SERVICE_NAME, method));
                return Boolean.TRUE;
            }
            logger.debug(getMessageOutputParam(SERVICE_NAME, OUTPUT, Boolean.FALSE.toString()));
            logger.info(getMessageEnd(SERVICE_NAME, method));
            return Boolean.FALSE;
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error(message);
            logger.info(getMessageEnd(SERVICE_NAME, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public ListResponse<FriendDTO> getBlockFriend(String userName) {
        String method = "getBlockFriend";
        logger.info(getMessageStart(SERVICE_NAME, method));
        logger.info(getMessageInputParam(SERVICE_NAME, USER_NAME, userName));
        try {
            List<Friend> friends = getRepository().getBlockFriendByUserName(userName);
            List<FriendDTO> friendDTOS = returnListFriendDTO(friends);
            ListResponse<FriendDTO> output = Utilities.createListResponse(friendDTOS);
            logger.debug(getMessageOutputParam(SERVICE_NAME, OUTPUT, output));
            logger.info(getMessageEnd(SERVICE_NAME, method));
            return output;
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error(message);
            logger.info(getMessageEnd(SERVICE_NAME, method));
            throw new SystemErrorException(message);
        }
    }

    @Override
    public List<FriendDTO> getFriendByUserName(String userId) {
        String method = "getFriendByUserName";
        logger.info(getMessageStart(SERVICE_NAME, method));
        logger.info(getMessageInputParam(SERVICE_NAME, "userId", userId.toString()));
        try {
            List<Friend> friends = getRepository().getFriendByUserId(userId);
            List<FriendDTO> friendDTOS = returnListFriendDTO(friends);
            logger.debug(getMessageOutputParam(SERVICE_NAME, OUTPUT, friendDTOS.size()));
            logger.info(getMessageEnd(SERVICE_NAME, method));
            return friendDTOS;
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error(message);
            logger.info(getMessageEnd(SERVICE_NAME, method));
            throw new NotFoundException(message);
        }
    }

    private List<FriendDTO> returnListFriendDTO(List<Friend> friends) {
        return friends.stream().map(item -> getMapper()
                .toDto(item, getCycleAvoidingMappingContext())).collect(Collectors.toList());
    }
}
