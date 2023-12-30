package jeju.oneroom.domain.post.controller;

import jeju.oneroom.domain.houseinfo.entity.HouseInfo;
import jeju.oneroom.domain.houseinfo.service.HouseInfoService;
import jeju.oneroom.domain.post.dto.PostDto;
import jeju.oneroom.domain.post.entity.Post;
import jeju.oneroom.domain.post.service.PostService;
import jeju.oneroom.domain.user.entity.User;
import jeju.oneroom.domain.user.service.UserService;
import jeju.oneroom.global.common.dto.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    
    private final UserService userService;
    private final HouseInfoService houseInfoService;
    private final PostService postService;
    
    // 게시글 작성 기능
    @PostMapping
    public ResponseEntity<Long> post(@Valid @RequestBody PostDto.Post postDto) {
        HouseInfo houseInfo = houseInfoService.findVerifiedHouseInfo(postDto.getHouseInfoId());
        User user = userService.verifyExistsUserByEmail(postDto.getUserEmail());
        Post createdPost = postService.createPost(postDto, houseInfo, user);
        
        return new ResponseEntity<>(createdPost.getId(), HttpStatus.CREATED);
    }
    
    // 게시글 수정 기능
    @PatchMapping("/{post-id}")
    public ResponseEntity<Long> patchPost(@PathVariable("post-id") @Positive long postId,
                                          @Valid @RequestBody PostDto.Patch patchDto) {
        patchDto.setPostId(postId);
        User user = userService.verifyExistsUserByEmail(patchDto.getUserEmail());
        
        Post updatedPost = postService.updatePost(user, patchDto);
        
        return new ResponseEntity<>(updatedPost.getId(), HttpStatus.OK);
    }
    
    // post-id로 단일 게시글 조회
    @GetMapping("/{post-id}")
    public ResponseEntity<PostDto.Response> getPost(@PathVariable("post-id") @Positive long postId) {
        PostDto.Response response = postService.findPostByPostId(postId);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    // user-id로 해당 유저가 작성한 다중 게시글 조회
    @GetMapping("/users/{user-id}")
    public ResponseEntity<MultiResponseDto<PostDto.SimpleResponseDto>> getUserPost(@PathVariable("user-id") @Positive long userId,
                                                                                   @RequestParam int page,
                                                                                   @RequestParam int size) {
        User user = userService.verifyExistsUser(userId);
        Page<PostDto.SimpleResponseDto> findPosts = postService.findPostsByUserId(user, page, size);
        
        return new ResponseEntity<>(new MultiResponseDto<>(findPosts), HttpStatus.OK);
    }

    /*// 주소를 통한 다중 게시글 조회 (최신순)
    @GetMapping("/search-address")
    public ResponseEntity<MultiResponseDto<PostDto.SimpleResponseDto>> getPostsByAddress(@RequestParam("query") String query,
                                                                                         @RequestParam int page,
                                                                                         @RequestParam int size) {
        List<HouseInfo> houseInfos = houseInfoService.findHouseInfosByAddress(query);
        Page<PostDto.SimpleResponseDto> findPosts = postService.findPostsByHouseInfos(houseInfos, page, size);

        return new ResponseEntity<>(new MultiResponseDto<>(findPosts), HttpStatus.OK);
    }

    // 제목을 통한 다중 게시글 조회 (최신순)
    @GetMapping("/search")
    public ResponseEntity<MultiResponseDto<PostDto.SimpleResponseDto>> getPostsByTitle(@RequestParam("query") String query,
                                                                                       @RequestParam int page,
                                                                                       @RequestParam int size) {
        Page<PostDto.SimpleResponseDto> findPosts = postService.findPostsByTitle(query, page, size);

        return new ResponseEntity<>(new MultiResponseDto<>(findPosts), HttpStatus.OK);
    }

    // 모든 게시글 조회 (최신순)
    @GetMapping
    public ResponseEntity<MultiResponseDto<PostDto.SimpleResponseDto>> getAllPosts(@RequestParam int page,
                                                                                   @RequestParam int size) {
        Page<PostDto.SimpleResponseDto> findPosts = postService.findAllPost(page, size);

        return new ResponseEntity<>(new MultiResponseDto<>(findPosts), HttpStatus.OK);
    }*/
    
    // post-id로 단일 게시글 삭제
    @DeleteMapping("/{post-id}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable("post-id") @Positive long postId,
                                                 @Valid @RequestBody PostDto.Delete deleteDto) {
        User user = userService.verifyExistsUserByEmail(deleteDto.getUserEmail());
        
        postService.deletePost(user, postId);
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
