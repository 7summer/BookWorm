package cn.BookWorm.collect.service;

import cn.BookWorm.collect.dao.CollectDao;
import cn.BookWorm.collect.domain.Collect;

import java.sql.SQLException;
import java.util.List;

public class CollectService {
    private CollectDao collectDao = new CollectDao();

    /**
     * 查找所有收藏
     * @param userId
     * @return
     */
    public List<Collect> findByUserId(String userId)
    {
        try {
            return collectDao.findByUserId(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加收藏
     * @param collect
     */
    public void addCollect(Collect collect)
    {
        try {
            Collect collect1 = collectDao.findByUserIdAndBookId(collect.getUser().getUserId(),
                    collect.getBook().getBookId());
            if(collect1 == null)
            {
                //收藏图书
                collectDao.addCollect(collect);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量删除收藏条目
     * @param collectIdArray
     */
    public void batchDeleteCollect(Object[] collectIdArray)
    {
        try {
            collectDao.batchDeleteCollect(collectIdArray);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
