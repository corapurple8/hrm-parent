package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.domain.CourseType;
import cn.itsource.hrm.mapper.CourseTypeMapper;
import cn.itsource.hrm.service.ICourseTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程目录 服务实现类
 * </p>
 *
 * @author cora
 * @since 2020-11-19
 */
@Service
public class CourseTypeServiceImpl extends ServiceImpl<CourseTypeMapper, CourseType> implements ICourseTypeService {
    /**
     * 加载课程类型树
     * @return
     */
    @Override
    public List<CourseType> treeData() {
        //return treeDataRecursive(0L);//递归入口 从第一级进去
        //return treeDataFor();从双重for循环获取
        return treeDataForMap();//最佳优化获取
    }

    /**
     * 递归方法
     * 栈容易溢出
     * @param pid
     * @return
     */
    public List<CourseType> treeDataRecursive(Long pid){
        List<CourseType> courseTypes = baseMapper.selectList(new QueryWrapper<CourseType>().eq("pid",pid));
        for (CourseType courseType : courseTypes) {
            //将查询出来的每一个课程id作为其他课程父id查找 到所有的子级
            List<CourseType> children = treeDataRecursive(courseType.getId());
            //再设置进去
            courseType.setChildren(children);
        }
        return courseTypes;
    }

    /**
     * for循环方法
     * 两次循环 占内存
     * @return
     */
    public List<CourseType> treeDataFor(){
        List<CourseType> list = new ArrayList<>();
        //先查出所有的课程
        List<CourseType> allCourseTypes = baseMapper.selectList(null);
        for (CourseType courseType : allCourseTypes) {
            //进行判断pid为0则为父级
            if (courseType.getPid()==0){
                //直接加入
                list.add(courseType);
                continue;
            }
            //再循环一次寻找父级
            for (CourseType parent : allCourseTypes) {
                if (courseType.getPid().longValue()==parent.getId().longValue()){
                    parent.getChildren().add(courseType);
                }
            }
        }
        return list;
    }

    /**
     * 循环+map方法
     * @return
     */
    public List<CourseType> treeDataForMap(){
        //map保存其id和对象
        Map<Long, CourseType> map = new HashMap<>();
        //保存结果
        List<CourseType> list = new ArrayList<>();
        //先查出所有的课程
        List<CourseType> allCourseTypes = baseMapper.selectList(null);
        for (CourseType courseType : allCourseTypes) {
            map.put(courseType.getId(),courseType);
        }
        //再循环一次寻找父级
        for (CourseType courseType : allCourseTypes) {
            if (courseType.getPid()==0){
                //直接加入
                list.add(courseType);
                continue;
            }
            CourseType parent = map.get(courseType.getPid());
            if (parent!=null){
                parent.getChildren().add(courseType);
            }
        }
        return list;
    }
}
