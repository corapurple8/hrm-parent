package cn.itsource.hrm.mapper;

import cn.itsource.hrm.domain.Systemdictionaryitem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cora
 * @since 2020-11-18
 */
public interface SystemdictionaryitemMapper extends BaseMapper<Systemdictionaryitem> {
    /**
     * 根据数据字典目录的sn去查询明细
     * @param sn
     * @return
     */
    List<Systemdictionaryitem> selectBySn(String sn);
}
