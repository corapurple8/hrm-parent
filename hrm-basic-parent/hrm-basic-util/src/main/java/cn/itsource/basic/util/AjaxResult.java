package cn.itsource.basic.util;

/**
 * json格式前后端交互数据的封装
 */
public class AjaxResult {
    /**成功状态*/
    private boolean success;
    /**执行信息*/
    private String message;
    /**交互结果*/
    private Object resultObj;
    /**错误码*/
    private Integer errorCode;

    /**
     * 私有化构造方法
     */
    private AjaxResult() {
    }

    public static AjaxResult me(){
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setSuccess(true);
        ajaxResult.setMessage("执行成功");
        return ajaxResult;
    }

    public boolean isSuccess() {
        return success;
    }

    /**
     * 修改所有set方法改造为链式编程
     * @param success
     * @return
     */
    public AjaxResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AjaxResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getResultObj() {
        return resultObj;
    }

    public AjaxResult setResultObj(Object resultObj) {
        this.resultObj = resultObj;
        return this;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public AjaxResult setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
        return this;
    }
}
