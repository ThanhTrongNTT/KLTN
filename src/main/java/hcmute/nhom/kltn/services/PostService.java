package hcmute.nhom.kltn.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import hcmute.nhom.kltn.common.payload.ListResponse;
import hcmute.nhom.kltn.common.payload.PagingResponseCustom;
import hcmute.nhom.kltn.dto.MediaFileDTO;
import hcmute.nhom.kltn.dto.PostDTO;
import hcmute.nhom.kltn.model.Post;

/**
 * Class PostService.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
public interface PostService extends AbstractService<PostDTO, Post> {
    /**
     * searchPostList.
     *
     * @param postDTO PostDTO
     * @return Page<PostDTO>
     */
    ListResponse<PostDTO> searchPost(PostDTO postDTO);

    /**
     * getAllPost.
     *
     * @return Page<PostDTO>
     */
    ListResponse<PostDTO> getAllPost();

    /**
     * getAllPost.
     *
     * @param pageNo  Page number
     * @param pageSize Page size
     * @param sortBy Sort by
     * @param sortDir Sort direction
     * @return Page<PostDTO>
     */
    PagingResponseCustom<PostDTO> getAllPostPaging(int pageNo, int pageSize, String sortBy, String sortDir);

    /**
     * Get post by user.
     * @param pageNo  Page number
     * @param pageSize Page size
     * @param sortBy Sort by
     * @param sortDir Sort direction
     * @param userName User name
     * @return Page<PostDTO>
     */
    PagingResponseCustom<PostDTO> getPostByUserPaging(int pageNo, int pageSize, String sortBy,
                                                      String sortDir, String userName);

    /**
     * Create post.
     * @param postDTO PostDTO
     * @return  PostDTO
     */
    PostDTO createPost(PostDTO postDTO, String email);

    /**
     *  Update post.
     * @param id UUID
     * @param postDTO PostDTO
     * @return PostDTO
     */
    PostDTO updatePost(String id, PostDTO postDTO);

    /**
     * Delete post.
     * @param id UUID
     * @return Boolean
     */
    Boolean deletePost(String id, String userName);

    /**
     * Get post by user and id.
     * @param id UUID
     * @param userName  String
     * @return PostDTO
     */
    PostDTO getPostByUserAndId(String id, String userName);

    /**
     * Get friend posts.
     * @param userName String
     * @return List<PostDTO>
     */
    List<PostDTO> getFriendPosts(String userName);

    /**
     * Like post.
     * @param id UUID
     * @param userName String
     * @return Boolean
     */
    Map<String, Boolean> likePost(String id, String userName);

    /**
     * Get post by id.
     * @param id String
     * @return PostDTO
     */
    PostDTO getPostById(String id);

    /**
     * Get all media by userName.
     * @param userName String
     * @return ListResponse<MediaFileDTO>
     */
    ListResponse<MediaFileDTO> getAllMediaByUserName(String userName);
}
