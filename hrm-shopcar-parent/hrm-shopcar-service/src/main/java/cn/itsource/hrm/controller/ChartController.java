package cn.itsource.hrm.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.controller.dto.CourseDto;
import cn.itsource.hrm.domain.CarCourse;
import cn.itsource.hrm.interceptor.Constant;
import cn.itsource.hrm.service.ICarCourseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

@RestController
@RequestMapping("/chart")
public class ChartController {

    @Autowired
    private ICarCourseService carCourseService;

    /**
     * 添加一个购物车对象
     * @param request
     * @param carCourse
     * @return
     */
    @PostMapping("/add")
    public AjaxResult add(HttpServletRequest request,@RequestBody CarCourse carCourse){
        //获取请求头
        String loginId = request.getHeader(Constant.LOGINID_KEY);
        if (!StringUtils.isNotBlank(loginId)) {//为空
            //没有请求头
            //调用没有用户的方法
            return AjaxResult.me().setSuccess(false).setMessage("没有用户");
        }
        //有请求头 则设置进课程里
        carCourse.setLoginId(Long.parseLong(loginId));
        //调用业务方法存储该课程购物车 还要进行业务判断是否已经存在
        carCourseService.add(carCourse);
        return AjaxResult.me();
    }

    /**
     * 删除一个购物车对象
     * @param request
     * @param courseDto
     * @return
     */
    @PostMapping("/delete")
    public AjaxResult delete(HttpServletRequest request,@RequestBody CourseDto courseDto){
        //获取请求头
        String loginId = request.getHeader(Constant.LOGINID_KEY);
        if (!StringUtils.isNotBlank(loginId)) {//为空
            //没有请求头
            //调用没有用户的方法
            return AjaxResult.me().setSuccess(false).setMessage("没有用户");
        }
        //调用业务方法存储该课程购物车 还要进行业务判断是否已经存在
        courseDto.setLoginId(Long.valueOf(loginId));
        carCourseService.delete(courseDto);
        return AjaxResult.me();
    }

    /**
     * 查询购物车所有详情
     * @param request
     * @return
     */
    @GetMapping("/boxGoodsList")
    public AjaxResult boxGoodsList(HttpServletRequest request){
        //获取请求头
        String loginId = request.getHeader(Constant.LOGINID_KEY);
        System.out.println(loginId);
        if (!StringUtils.isNotBlank(loginId)) {//为空
            //没有请求头
            //调用没有用户的方法
            return AjaxResult.me().setSuccess(false).setMessage("没有用户");
        }
        List<CourseDto> list = carCourseService.goodsList(Long.valueOf(loginId));
        return AjaxResult.me().setResultObj(list);
    }


    /**
     * 购买一个购物车对象
     * @param request
     * @param courseDto
     * @return
     */
    @PostMapping("/buy")
    public AjaxResult buy(HttpServletRequest request,@RequestBody CourseDto courseDto){
        //获取请求头
        String loginId = request.getHeader(Constant.LOGINID_KEY);
        if (!StringUtils.isNotBlank(loginId)) {//为空
            //没有请求头
            //调用没有用户的方法
            return AjaxResult.me().setSuccess(false).setMessage("没有用户");
        }
        //调用业务方法存储该课程购物车 还要进行业务判断是否已经存在
        courseDto.setLoginId(Long.valueOf(loginId));
        carCourseService.buy(courseDto);
        return AjaxResult.me();
    }
}
