package hcmute.nhom.kltn.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import hcmute.nhom.kltn.common.payload.NewPostRequest;
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
     * @param pageable Pageable
     * @return Page<PostDTO>
     */
    Page<PostDTO> searchPost(PostDTO postDTO, Pageable pageable);

    /**
     * getAllPost.
     *
     * @param pageNo  Page number
     * @param pageSize Page size
     * @param sortBy Sort by
     * @param sortDir Sort direction
     * @return Page<PostDTO>
     */
    Page<PostDTO> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

    /**
     * Get post by user.
     * @param pageNo  Page number
     * @param pageSize Page size
     * @param sortBy Sort by
     * @param sortDir Sort direction
     * @param userName User name
     * @return Page<PostDTO>
     */
    Page<PostDTO> getPostByUser(int pageNo, int pageSize, String sortBy, String sortDir, String userName);

    /**
     * Create post.
     * @param newPostRequest NewPostRequest
     * @return  PostDTO
     */
    PostDTO createPost(NewPostRequest newPostRequest);

    /**
     *  Update post.
     * @param id UUID
     * @param postDTO PostDTO
     * @return PostDTO
     */
    PostDTO updatePost(UUID id, PostDTO postDTO);

    /**
     * Delete post.
     * @param id UUID
     * @return Boolean
     */
    Boolean deletePost(UUID id, String userName);

    /**
     * Get post by user and id.
     * @param id UUID
     * @param userName  String
     * @return PostDTO
     */
    PostDTO getPostByUserAndId(UUID id, String userName);

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
    Map<String, Boolean> likePost(UUID id, String userName);
}
