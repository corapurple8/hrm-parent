package cn.itsource.hrm.service;

import cn.itsource.hrm.domain.Systemdictionaryitem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cora
 * @since 2020-11-18
 */
public interface ISystemdictionaryitemService extends IService<Systemdictionaryitem> {
    /**
     * 根据数据字典目录的sn去查询明细
     * @param sn
     * @return
     */
    List<Systemdictionaryitem> listBySn(String sn);
}
