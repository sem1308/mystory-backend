package uos.mystory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uos.mystory.dto.mapping.select.SelectHistoryDTO;
import uos.mystory.dto.response.HistoryInfoDTO;
import uos.mystory.repository.PostHistoryRepository;
import uos.mystory.repository.condition.HistorySearchCondition;
import uos.mystory.repository.querydsl.PostHistoryQueryRepositoryImpl;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostHistoryService implements HistoryService{
    private final PostHistoryRepository postHistoryRepository;

    /**
     * @title 게시글 번호로 게시글 이력 얻기
     * @param postId
     * @return 게시글 이력 DTO
     */
    @Override
    public List<SelectHistoryDTO> getHistoryInfoDTOs(Long postId) {
        return postHistoryRepository.findAllByPostIdGroupByDateAndVisitedPath(postId);
    }

    /**
     * @title 게시글 번호로 정리된 게시글 이력 얻기
     * @param postId
     * @return 정리된 게시글 이력
     */
    @Override
    public HistoryInfoDTO getHistories(Long postId) {
        List<SelectHistoryDTO> selectBlogHistoryDTOS = postHistoryRepository.findAllByPostIdGroupByDateAndVisitedPath(postId);
        return HistoryInfoDTO.of(postId, selectBlogHistoryDTOS);
    }

    /**
     * @title 특정 조건으로(postId, from, to) 정리된 게시글 이력 얻기 ex) 특정 날짜의 게시글 이력 얻기
     * @param condition
     * @return 정리된 게시글 이력
     */
    @Override
    public HistoryInfoDTO getHistories(HistorySearchCondition condition) {
        List<SelectHistoryDTO> selectHistoryDTOS = postHistoryRepository.findAllByConditionGroupByDateAndVisitedPath(condition);
        return HistoryInfoDTO.of(condition.id(), selectHistoryDTOS);
    }
}
