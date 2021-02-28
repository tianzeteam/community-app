package com.smart.home.modules.other.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.exception.DuplicateDataException;
import com.smart.home.enums.ReportCategoryEnum;
import com.smart.home.modules.article.dao.ArticleMapper;
import com.smart.home.modules.community.dao.CommunityPostMapper;
import com.smart.home.modules.other.dao.ReportHistoryMapper;
import com.smart.home.modules.other.entity.ReportHistory;
import com.smart.home.modules.other.entity.ReportHistoryExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ReportHistoryService {

    @Resource
    ReportHistoryMapper reportHistoryMapper;
    @Resource
    ArticleMapper articleMapper;
    @Resource
    CommunityPostMapper communityPostMapper;

    /**
     * 举报文章/帖子
     * @param reportCategoryEnum 举报类型
     * @param primaryKey 关联内容主键id
     * @param userId 举报人
     * @param contents 举报内容描述
     * @param images 看是否有截图证明，存图片路径
     * @return
     */
    public int create(ReportCategoryEnum reportCategoryEnum,Long primaryKey, Long userId, String contents, String images) {
        // 检查该用户有没有举报过了
        ReportHistoryExample example = new ReportHistoryExample();
        example.createCriteria()
                .andCreatedByEqualTo(userId)
                .andSourceIdEqualTo(primaryKey)
                .andCategoryEqualTo(reportCategoryEnum.getCode());
        if (reportHistoryMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("已经举报过了");
        }
        ReportHistory reportHistory = new ReportHistory();
        reportHistory.withCategory(reportCategoryEnum.getCode())
                .withCategoryName(reportCategoryEnum.getDesc())
                .withContents(contents)
                .withCreatedBy(userId)
                .withImages(images)
                .withRevision(0)
                .withSourceId(primaryKey)
                .withCreatedTime(new Date());
        int affectRow = reportHistoryMapper.insertSelective(reportHistory);
        if (affectRow > 0) {
            switch (reportCategoryEnum) {
                case ARTICLE:
                    articleMapper.increaseReportCount(primaryKey);
                    break;
                case POST:
                    communityPostMapper.increaseReportCount(primaryKey);
                    break;
                default:
                    break;
            }
        }
        return affectRow;
    }

    public int update(ReportHistory reportHistory) {
        reportHistory.setUpdatedTime(new Date());
        return reportHistoryMapper.updateByPrimaryKeySelective(reportHistory);
    }

    public int deleteById(Long id) {
        return reportHistoryMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            reportHistoryMapper.deleteByPrimaryKey(id);
        }
    }

    public List<ReportHistory> selectByPage(ReportHistory reportHistory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ReportHistoryExample example = new ReportHistoryExample();
        ReportHistoryExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return reportHistoryMapper.selectByExample(example);
    }

    public ReportHistory findById(Long id) {
        ReportHistory reportHistory = reportHistoryMapper.selectByPrimaryKey(id);
        return reportHistory;
    }

}
