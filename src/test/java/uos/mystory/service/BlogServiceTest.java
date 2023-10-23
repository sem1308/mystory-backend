package uos.mystory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.domain.Blog;
import uos.mystory.domain.User;
import uos.mystory.dto.mapping.insert.InsertBlogDTO;
import uos.mystory.dto.mapping.insert.InsertUserDTO;
import uos.mystory.dto.mapping.update.UpdateBlogDTO;
import uos.mystory.dto.response.BlogInfoDTO;
import uos.mystory.exception.DuplicateException;
import uos.mystory.repository.condition.BlogSearchCondition;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class BlogServiceTest {
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;
    User user;

    @BeforeEach
    public void setup() {
        Long id = userService.saveUser(InsertUserDTO.builder().userId("sem1308").userPw("1308").nickname("ddory").phoneNum("01000000000").build());
        this.user = userService.getUser(id);
    }

    @Test
    public void 블로그_생성() throws Exception {
        //given
        InsertBlogDTO insertBlogDTO = InsertBlogDTO.builder().name("Dev").url("https://han-dev.mystory.com").description("기본 블로그").user(user).build();

        //when
        Long id = blogService.saveBlog(insertBlogDTO);
        Blog blog = blogService.getBlog(id);

        //then
        assertEquals(blog.getId(),id);
        assertEquals(blog.getUser(),user);
        System.out.println(blog);
    }
    
    @Test
    public void 블로그_변경() throws Exception {
        //given
        InsertBlogDTO insertBlogDTO = InsertBlogDTO.builder().name("Dev").url("https://han-dev.mystory.com").description("기본 블로그").user(user).build();
        Long id = blogService.saveBlog(insertBlogDTO);

        String updatedName = "Han-Dev";
        String updatedDesc = "상향된 블로그";
        String updatedUrl = null;
        UpdateBlogDTO updateBlogDTO = UpdateBlogDTO.builder().id(id).name(updatedName).url(updatedUrl).description(updatedDesc).build();

        //when
        blogService.updateBlog(updateBlogDTO);

        //then
        Blog blog = blogService.getBlog(id);
        assertEquals(blog.getName(), updatedName);
        assertEquals(blog.getDescription(), updatedDesc);
        System.out.println(blog);
    }

    @Test
    public void 블로그_중복_url_검사() throws Exception {
        //given
        InsertBlogDTO insertBlogDTO = InsertBlogDTO.builder().name("Dev").url("https://han-dev.mystory.com").description("기본 블로그").user(user).build();
        InsertBlogDTO insertBlogDTO2 = InsertBlogDTO.builder().name("Dev2").url("https://han-dev.mystory.com").description("기본 블로그2").user(user).build();

        //when
        blogService.saveBlog(insertBlogDTO);

        //then
        assertThrows(DuplicateException.class, () ->
            blogService.saveBlog(insertBlogDTO2)
        );
    }

    @Test
    public void 유저가_가진_블로그_목록_가져오기() throws Exception {
        //given
        int page = 0;
        int size = 2;
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(Sort.Order.desc("name"));
        Sort sort = Sort.by(orders);
        Pageable pageable = PageRequest.of(page, size,sort);
        BlogSearchCondition condition = new BlogSearchCondition(null);

        int totalElements = 3;
        int expectedTotalPages = (int) Math.ceil((double) totalElements / size);
        //when
        for (int i = 0; i < totalElements; i++) {
            int number = (3 - i);
            InsertBlogDTO insertBlogDTO = InsertBlogDTO.builder().name("Dev"+number).url("https://dev"+number+".mystory.com").description("기본 블로그").user(user).build();
            blogService.saveBlog(insertBlogDTO);
        }
        Page<BlogInfoDTO> blogInfoDTOS = blogService.getBlogsByContidion(condition,pageable);

        //then
        blogInfoDTOS.getContent().forEach(System.out::println);
        assertEquals(expectedTotalPages, blogInfoDTOS.getTotalPages());
        assertEquals(totalElements, blogInfoDTOS.getTotalElements());
    }
}
