package cn.itsource.hrm.service.impl;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.client.RedisClient;
import cn.itsource.hrm.client.RedisConstants;
import cn.itsource.hrm.controller.vo.Crumb;
import cn.itsource.hrm.domain.CourseType;
import cn.itsource.hrm.mapper.CourseTypeMapper;
import cn.itsource.hrm.service.ICourseTypeService;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
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

    @Autowired
    private RedisClient redisClient;
    /**
     * 加载课程类型树
     * @return
     */
    @Override
    public List<CourseType> treeData() {
        //return treeDataRecursive(0L);//递归入口 从第一级进去
        //return treeDataFor();从双重for循环获取
        //return treeDataForMap();//最佳优化获取
        //先从redis中获取
        AjaxResult result = redisClient.get(RedisConstants.ALL_COURSE_TYPE_KEY);
        Assert.isTrue(result.isSuccess(),result.getMessage());
        //如果有
        String resultObj = (String) result.getResultObj();
        if (StringUtils.isNotBlank(resultObj)){
            //查询到了直接拿出来用
            List<CourseType> courseTypes = JSONArray.parseArray(resultObj, CourseType.class);
            return courseTypes;
        }
        //如果没查到
        //从数据库里查且放入缓存
        List<CourseType> courseTypes = treeDataForMap();
        String jsonString = JSONArray.toJSONString(courseTypes);
        AjaxResult result1 = redisClient.set(RedisConstants.ALL_COURSE_TYPE_KEY, jsonString, 60 * 60);
        Assert.isTrue(result1.isSuccess(),result.getMessage());
        return courseTypes;
    }

    @Override
    public List<Crumb> loadCrumbs(Long id) {
        List<Crumb> crumbs = new ArrayList<>();

        //当前课程类型
        CourseType courseType = baseMapper.selectById(id);
        //其他类型列表(同级) 从path获得
        String[] ids = courseType.getPath().split("\\.");
        List<CourseType> otherTypes = new ArrayList<>();
        for (String s : ids) {//该id也包括自己
            CourseType currentType = baseMapper.selectById(s);
            //其他同级的课程类型
            otherTypes=baseMapper.selectList(new QueryWrapper<CourseType>()
                    .eq("pid", currentType.getPid())//pid相等
                    .ne("id", currentType.getId())//排除自己
            );
            Crumb crumb = new Crumb(currentType, otherTypes);
            crumbs.add(crumb);
        }
        return crumbs;
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

    @Override
    @Transactional
    public boolean save(CourseType entity) {
        entity.setCreateTime(System.currentTimeMillis());
        CourseType parent = baseMapper.selectById(entity.getPid());
        boolean save = super.save(entity);
        //获取主键并拼接
        String path = parent.getPath()+"."+entity.getId();
        baseMapper.updateById(entity);
        if (save){
            //调用清空
            synRedis();
        }
        return save;
    }

    @Override
    public boolean removeById(Serializable id) {
        boolean b = super.removeById(id);
        if (b){
            synRedis();
        }
        return b;
    }

    @Override
    public boolean updateById(CourseType entity) {
        boolean b = super.updateById(entity);
        if (b){
            synRedis();
        }
        return b;
    }

    /**
     * 清空缓存
     */
    public void synRedis(){
        AjaxResult result = redisClient.del(RedisConstants.ALL_COURSE_TYPE_KEY);
        Assert.isTrue(result.isSuccess(),result.getMessage());
    }
}
