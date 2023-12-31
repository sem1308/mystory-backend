package uos.mystory.dto.mapping.insert;

import lombok.Builder;
import lombok.Getter;
import uos.mystory.domain.Blog;
import uos.mystory.domain.Category;
import uos.mystory.domain.User;
import uos.mystory.domain.enums.OpenState;
import uos.mystory.domain.enums.PostType;
import uos.mystory.domain.enums.WriteType;
import uos.mystory.dto.request.create.CreatePostDTO;

@Getter
@Builder
public class InsertPostDTO {
    private PostType postType;

    private WriteType writeType;

    private OpenState openState;

    private String title;

    private String content;

    private String url;

    private String titleImgPath;

    /**
     * 연관 관계 매핑
     */
    private User user;

    private Category category;

    private Blog blog;

    public static InsertPostDTO of(CreatePostDTO postDTO, User user, Blog blog, Category category){
        return InsertPostDTO.builder()
                .postType(postDTO.postType())
                .writeType(postDTO.writeType())
                .openState(postDTO.openState())
                .title(postDTO.title())
                .content(postDTO.content())
                .url(postDTO.url())
                .titleImgPath(postDTO.titleImgPath())
                .user(user)
                .category(category)
                .blog(blog)
                .build();
    }
}
