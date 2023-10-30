package uos.mystory.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;
import uos.mystory.domain.history.PostHistory;
import uos.mystory.domain.history.QPostHistory;
import uos.mystory.dto.mapping.select.*;
import uos.mystory.repository.condition.HistorySearchCondition;

import java.time.LocalDate;
import java.util.List;

import static uos.mystory.domain.history.QPostHistory.postHistory;

@Repository
public class PostHistoryQueryRepository extends Querydsl4RepositorySupport<PostHistory, QPostHistory>{

    public PostHistoryQueryRepository() {
        super(PostHistory.class, postHistory);
    }

    public List<SelectHistoryDTO> findAllByPostIdGroupByDateAndVisitedPath(Long blogId) {
        return select(new QSelectHistoryDTO(
                        postHistory.post.id,
                        postHistory.createdDate,
                        postHistory.path,
                        postHistory.count()
                ))
                .from(postHistory)
                .where(postHistory.post.id.eq(blogId))
                .groupBy(postHistory.createdDate, postHistory.path)
                .fetch();
    }

    public List<SelectHistoryDTO> findAllByConditionGroupByDateAndVisitedPath(HistorySearchCondition condition) {
        return select(new QSelectHistoryDTO(
                postHistory.post.id,
                postHistory.createdDate,
                postHistory.path,
                postHistory.count()
        ))
                .from(postHistory)
                .where(
                        postIdEq(condition.id()),
                        dateBetween(condition.from(), condition.to())
                        )
                .groupBy(postHistory.createdDate, postHistory.path)
                .fetch();
    }

    public List<SelectHistoryVisitsDTO> getVisitsByBlogGroupByDate(Long blogId) {
        return select(new QSelectHistoryVisitsDTO(postHistory.createdDate, postHistory.count()))
                .from(postHistory)
                .where(postHistory.post.id.eq(blogId))
                .groupBy(postHistory.createdDate)
                .fetch();
    }

    private BooleanExpression postIdEq(Long postId) {
        return postId != null ? postHistory.post.id.eq(postId) :null;
    }

    private BooleanExpression dateBetween(LocalDate from, LocalDate to) {
        return (from != null && to != null) ? postHistory.createdDate.between(from,to) :null;
    }

}
