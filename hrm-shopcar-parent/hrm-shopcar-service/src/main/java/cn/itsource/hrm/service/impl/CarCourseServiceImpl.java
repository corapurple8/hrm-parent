package cn.itsource.hrm.service.impl;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.CodeGenerateUtils;
import cn.itsource.hrm.client.CourseClient;
import cn.itsource.hrm.client.CourseDetailClient;
import cn.itsource.hrm.client.CourseMarketClient;
import cn.itsource.hrm.client.RedisClient;
import cn.itsource.hrm.config.AlipayConfig;
import cn.itsource.hrm.controller.dto.CourseDto;
import cn.itsource.hrm.domain.*;
import cn.itsource.hrm.interceptor.Constant;
import cn.itsource.hrm.mapper.*;
import cn.itsource.hrm.service.ICarCourseService;
import cn.itsource.hrm.service.IPayService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cora
 * @since 2020-11-28
 */
@Service
public class CarCourseServiceImpl extends ServiceImpl<CarCourseMapper, CarCourse> implements ICarCourseService {
    @Autowired//调用redis接口存储
    private RedisClient redisClient;


    //课程接口用于查询
    @Autowired
    private CourseClient courseClient;
    @Autowired
    private CourseMarketClient courseMarketClient;
    @Autowired
    private CourseDetailClient courseDetailClient;

    @Autowired//注入购物车mapper
    private ShopcarMapper shopcarMapper;

    @Autowired//注入订单地址mapper (用户地址,用户联系方式)
    private OrderAddressMapper orderAddressMapper;

    @Autowired//注入订单mapper
    private OrderCourseMapper orderCourseMapper;

    @Autowired
    private PayBillMapper payBillMapper;

    @Autowired
    private IPayService payService;


    @Override
    @Transactional//开启事务修改数据库
    public void add(CarCourse carCourse) {
        //该课程id
        Long courseId = carCourse.getCourseId();
        //获取购物车
        Shopcar shopcar = shopcarMapper.selectOne(new QueryWrapper<Shopcar>().eq("login_id", carCourse.getLoginId()));
        if (shopcar == null) {//若没查询成功则直接创建一个新的购物车
            shopcar = new Shopcar();
            shopcar.setLoginId(carCourse.getLoginId());
        }
        //获取其存在数据库里的愿望清单
        String wantIds = shopcar.getWantIds();
        if (StringUtils.isNotBlank(wantIds)) {
            //如果本身存在 则判断是否重复了
            //购物车对象的id
            String[] carCourseIds = wantIds.split(",");
            for (int i = 0; i < carCourseIds.length; i++) {
                CarCourse selectOne = baseMapper.selectOne(new QueryWrapper<CarCourse>().eq("id", Long.valueOf(carCourseIds[i])));
                if (selectOne != null) {
                    if (courseId == selectOne.getCourseId()) {
                        //重复了 不作为
                        return;
                    }
                }
            }
        }
        //获取详情等信息
        CourseDto courseDto = getCourseDto(courseId);
        //将该订单信息存入数据库
        carCourse.setPrice(new BigDecimal(courseDto.getPrice()));
        //购物车存入数据库  获得主键
        baseMapper.insert(carCourse);
        //保存购物车对象id和登录id  其id就是课程id
        courseDto.setCarCourseId(carCourse.getId());
        courseDto.setLoginId(carCourse.getLoginId());
        if (StringUtils.isNotBlank(wantIds)) {
            //没有重复 直接在后面添加
            wantIds = wantIds + "," + carCourse.getId();
            //设置回购物车
            shopcar.setWantIds(wantIds);
        } else {
            //购物车里本身就不存在 或者本身没有购物车
            wantIds = carCourse.getId().toString();
            //设置回购物车
            shopcar.setWantIds(wantIds);
        }

        //购物车存储进数据库
        if (shopcar.getId() == null) {
            shopcarMapper.insert(shopcar);
        } else {
            shopcarMapper.update(shopcar, new UpdateWrapper<Shopcar>().eq("id",shopcar.getId()));
        }

        //保存在redis中两条数据 一个是该课程详情 一个是所有的愿望清单
        //所有愿望清单 可以直接获取 根据清单去找购物车课程详情
        //shopcar:2=1,2,3,4
        redisClient.set(Constant.SHOPCAR_PRE + carCourse.getLoginId(), wantIds, Constant.EXPIRE_TIME);
        //转换为json字符串
        String chartCourse = JSON.toJSONString(courseDto);
        //设置为半小时过期
        //shopcar:2:10=购物车id为10的课程1
        redisClient.set(Constant.COURSE_PRE + carCourse.getLoginId() + ":" + carCourse.getId(), chartCourse, Constant.EXPIRE_TIME);
    }

    @Override
    @Transactional
    public void delete(CourseDto courseDto) {
        //查询购物车
        Shopcar shopcar = shopcarMapper.selectOne(new QueryWrapper<Shopcar>().eq("login_id", courseDto.getLoginId()));
        //课程愿望清单
        String wantIds = shopcar.getWantIds();
        //购物车对象从数据库删除
        baseMapper.deleteById(courseDto.getCarCourseId());
        //删除redis
        Long loginId = courseDto.getLoginId();
        Long carCourseId = courseDto.getCarCourseId();
        AjaxResult ajaxResult = redisClient.del(Constant.COURSE_PRE + loginId + ":" + carCourseId);
        Assert.isTrue(ajaxResult.isSuccess(), ajaxResult.getMessage());

        String[] carCourseIds = wantIds.split(",");
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < carCourseIds.length; i++) {
            if (Long.valueOf(carCourseIds[i]) != carCourseId) {
                strings.add(carCourseIds[i]);
            }
        }
        wantIds = strings.stream().collect(Collectors.joining(","));
        System.out.println(wantIds);
        shopcar.setWantIds(wantIds);
        //购物车从数据库中修改
        shopcarMapper.update(shopcar, new UpdateWrapper<Shopcar>().eq("id",shopcar.getId()));
        //改变redis
        redisClient.set(Constant.SHOPCAR_PRE + loginId, wantIds, Constant.EXPIRE_TIME);
    }

    /**
     * 查询购物车里所有的商品
     *
     * @param loginId
     * @return
     */
    @Override
    public List<CourseDto> goodsList(Long loginId) {
        //先从redis里查询
        AjaxResult ajaxResult = redisClient.get(Constant.SHOPCAR_PRE + loginId);
        Assert.isTrue(ajaxResult.isSuccess(), ajaxResult.getMessage());
        String wantIds = (String) ajaxResult.getResultObj();
        if (StringUtils.isNotBlank(wantIds)) {
            List<CourseDto> courseDtoList = findCourseDtoList(wantIds, loginId);
            //刷新redis
            putInRedis(loginId,wantIds,courseDtoList);
            return courseDtoList;
        } else {
            //redis中没有
            Shopcar shopcar = shopcarMapper.selectOne(new QueryWrapper<Shopcar>().eq("login_id", loginId));
            if (shopcar == null) {//购物车不存在
                //响应一个空数组
                return Collections.emptyList();
            }
            //存在 且不在redis中 要重新从数据库里查
            String wantIdsSql = shopcar.getWantIds();
            List<CourseDto> courseDtoList = findCourseDtoList(wantIdsSql, loginId);
            //重新放回redis中
            putInRedis(loginId,wantIdsSql,courseDtoList);
            return courseDtoList;
        }
    }

    /**
     * 购买一个在购物车里的对象
     * @param courseDto
     */
    @Override
    public String buy(CourseDto courseDto) {
        //创建一个新的课程订单
        OrderCourse orderCourse = new OrderCourse();

        //订单地址
        OrderAddress orderAddress = new OrderAddress();
        orderAddress.setContacts(courseDto.getContacts());
        orderAddress.setAddress(courseDto.getAddress());
        orderAddress.setPhone(courseDto.getPhone());

        //生成随机订单号
        String orderSn = CodeGenerateUtils.generateOrderSn(courseDto.getLoginId());
        //设置进订单和订单地址
        orderCourse.setOrdersn(orderSn);
        orderAddress.setOrdersn(orderSn);
        orderCourse.setLoginId(courseDto.getLoginId());
        orderAddress.setLoginId(courseDto.getLoginId());
        orderCourse.setTenantId(courseDto.getTenantId());
        orderCourse.setCourseId(courseDto.getId());
        orderCourse.setPaytype(1);
        //价格
        orderCourse.setPrice(new BigDecimal(courseDto.getPrice()));


        //写进数据库得到主键 存入订单地址
        orderAddressMapper.insert(orderAddress);
        orderCourse.setOrderAddressId(orderAddress.getId());

        //设置过期时间
        //支付过期 60s*5 5分钟
        Date lastPayTime = DateUtils.addSeconds(new Date(),60*5);
        //订单确认过期 7天确认收货
        Date lastConfirmTime =DateUtils.addDays(new Date(),7) ;
        orderCourse.setLastpaytime(lastPayTime);
        orderCourse.setLastconfirmtime(lastConfirmTime);
        //设置订单简述
        orderCourse.setDigest("课程选择:"+courseDto.getLoginId()+"购买了"+courseDto.getName());
        //将主键写回 正常订单
        orderCourse.setState(Constant.NORMAL);
        //写进数据库
        orderCourseMapper.insert(orderCourse);
        //创建一个支付订单
        PayBill payBill = this.createPayBill(orderCourse);
        //存入数据库
        payBillMapper.insert(payBill);
        //调用支付接口，返回一个html字符串，可以用来跳转到支付页面
        String formHtml = payService.jumpToPay(orderCourse);
        //删除购物车里的courseDto
        delete(courseDto);
        System.out.println(formHtml);
        return formHtml;
    }

    /**
     * 生成一个支付单
     * @param orderCourse
     * @return
     */
    private PayBill createPayBill(OrderCourse orderCourse) {
        PayBill payBill = new PayBill();
        payBill.setDigest(orderCourse.getDigest());
        payBill.setMoney(orderCourse.getPrice());
        //支付宝方式
        payBill.setPaychannel(orderCourse.getPaytype());
        //待支付
        payBill.setState(Constant.AUDIT);
        payBill.setLastpaytime(orderCourse.getLastpaytime());
        payBill.setLoginId(orderCourse.getLoginId());
        payBill.setTenantId(orderCourse.getTenantId());
        payBill.setOrdersn(orderCourse.getOrdersn());
        return payBill;
    }

    /**
     * 将购物车在redis中的数据刷新查询
     * @param loginId
     * @param wantIds
     * @param courseDtoList
     */
    public void putInRedis(Long loginId,String wantIds,List<CourseDto> courseDtoList){
        redisClient.set(Constant.SHOPCAR_PRE+loginId,wantIds,Constant.EXPIRE_TIME);
        for (CourseDto courseDto : courseDtoList) {
            redisClient.set(Constant.COURSE_PRE+loginId+":"+courseDto.getCarCourseId(),JSON.toJSONString(courseDto),Constant.EXPIRE_TIME);
        }
    }


    /**
     * 查询所有对象的方法
     * @param wantIds
     * @param loginId
     * @return
     */
    public List<CourseDto> findCourseDtoList(String wantIds, Long loginId) {
        //购物车所有对象
        List<CourseDto> courseDtos = new ArrayList<>();
        if (StringUtils.isNotBlank(wantIds)) {
            //有记录  直接从记录的redis里找
            String[] carCourseIds = wantIds.split(",");
            for (int i = 0; i < carCourseIds.length; i++) {
                AjaxResult result = redisClient.get(Constant.COURSE_PRE + loginId + ":" + carCourseIds[i]);
                Assert.isTrue(result.isSuccess(), result.getMessage());
                //查询到了
                String obj = (String) result.getResultObj();
                if (StringUtils.isNotBlank(obj)) {
                    //能直接查询出来的都有购物对象的carCourseId和登录的loginId
                    CourseDto courseDto = JSON.parseObject(obj, CourseDto.class);
                    courseDtos.add(courseDto);
                } else {
                    //单个购物对象已经过期 直接从数据库查找
                    CarCourse carCourse = baseMapper.selectById(carCourseIds[i]);
                    //通过购物车对象的课程id查找
                    CourseDto courseDto = getCourseDto(Long.valueOf(carCourse.getCourseId()));
                    //设置回购物对象id和登陆id
                    courseDto.setCarCourseId(carCourse.getId());
                    courseDto.setLoginId(loginId);
                    courseDtos.add(courseDto);
                }
            }
        }
        return courseDtos;
    }

    /**
     * 获取课程辅助详情
     *
     * @param id
     * @return
     */
    public CourseDto getCourseDto(Long id) {
        //调用其他接口 查询课程的价格等相关资讯
        CourseDto courseDto = new CourseDto();
        Course course = courseClient.get(id);
        CourseMarket courseMarket = courseMarketClient.get(id);
        CourseDetail courseDetail = courseDetailClient.get(id);
        BeanUtils.copyProperties(course, courseDto);
        BeanUtils.copyProperties(courseMarket, courseDto);
        BeanUtils.copyProperties(courseDetail, courseDto);
        return courseDto;
    }
}