package com.github.wolfgang.operation.core.dal.dataobject;

import com.github.wolfgang.operation.core.dal.enumeration.OperationTypeEnum;
import com.github.wolfgang.operation.core.mybatis.EnumToTinyintHandler;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "`operation`")
public class OperationDO {

    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 0代表未执行，1代表已执行
     */
    @Column(name = "`is_invoke`")
    private Boolean isInvoke;

    /**
     * 执行时间
     */
    @Column(name = "`invoke_time`")
    private Date invokeTime;

    /**
     * 操作状态（0:创建，1:已完成， 2：已取消，3. 异常）
     */
    @Column(name = "`oper_status`")
    private String operStatus;

    /**
     * 操作人员
     */
    @Column(name = "`user_uuid`")
    private String userUuid;

    /**
     * 备注
     */
    @Column(name = "`comment`")
    private String comment;

    /**
     /**
     * 操作是否结束，0代表未结束，1代表已结束
     */
    @Column(name = "`is_done`")
    private Boolean isDone;

    /**
     * 业务的key
     */
    @Column(name = "`biz_key`")
    private String bizKey;

    @ColumnType(column = "`type`", typeHandler = EnumToTinyintHandler.class)
    @Column(name = "type")
    private OperationTypeEnum type;

    /**
     * callback receiver
     */
    @Column(name = "`caller`")
    private String caller;

    @Column(name = "`create_time`")
    private Date createTime;

    @Column(name = "`update_time`")
    private Date updateTime;

    public static final String ID = "id";

    public static final String INSTANCE_ID = "instanceId";

    public static final String INSTANCE_TYPE = "instanceType";

    public static final String IS_INVOKE = "isInvoke";

    public static final String INVOKE_TIME = "invokeTime";

    public static final String OPER_STATUS = "operStatus";

    public static final String OPER_TYPE = "operType";

    public static final String OPER_PARAMS = "operParams";

    public static final String USER_UUID = "userUuid";

    public static final String COMMENT = "comment";

    public static final String IS_DONE = "isDone";

    public static final String TYPE = "type";

    public static final String BIZ_KEY = "bizKey";

    public static final String RECEIVER = "receiver";

    public static final String EXPIRE_TIME = "expireTime";
    public static final String CREATE_TIME = "createTime";

    public static final String UPDATE_TIME = "updateTime";

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public OperationTypeEnum getType() {
        return type;
    }

    public void setType(OperationTypeEnum type) {
        this.type = type;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取0代表未执行，1代表已执行
     *
     * @return is_invoke - 0代表未执行，1代表已执行
     */
    public Boolean getIsInvoke() {
        return isInvoke;
    }

    /**
     * 设置0代表未执行，1代表已执行
     *
     * @param isInvoke 0代表未执行，1代表已执行
     */
    public void setIsInvoke(Boolean isInvoke) {
        this.isInvoke = isInvoke;
    }

    /**
     * 获取执行时间
     *
     * @return invoke_time - 执行时间
     */
    public Date getInvokeTime() {
        return invokeTime;
    }

    /**
     * 设置执行时间
     *
     * @param invokeTime 执行时间
     */
    public void setInvokeTime(Date invokeTime) {
        this.invokeTime = invokeTime;
    }

    /**
     * 获取操作状态（0:创建，1:已完成， 2：已取消，3. 异常）
     *
     * @return oper_status - 操作状态（0:创建，1:已完成， 2：已取消，3. 异常）
     */
    public String getOperStatus() {
        return operStatus;
    }

    /**
     * 设置操作状态（0:创建，1:已完成， 2：已取消，3. 异常）
     *
     * @param operStatus 操作状态（0:创建，1:已完成， 2：已取消，3. 异常）
     */
    public void setOperStatus(String operStatus) {
        this.operStatus = operStatus;
    }

    /**
     * 获取操作人员
     *
     * @return user_uuid - 操作人员
     */
    public String getUserUuid() {
        return userUuid;
    }

    /**
     * 设置操作人员
     *
     * @param userUuid 操作人员
     */
    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid == null ? null : userUuid.trim();
    }

    /**
     * 获取备注
     *
     * @return comment - 备注
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置备注
     *
     * @param comment 备注
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    /**
     * 获取操作是否结束，0代表未结束，1代表已结束
     *
     * @return is_done - 操作是否结束，0代表未结束，1代表已结束
     */
    public Boolean getIsDone() {
        return isDone;
    }

    /**
     * 设置操作是否结束，0代表未结束，1代表已结束
     *
     * @param isDone 操作是否结束，0代表未结束，1代表已结束
     */
    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * 获取业务的key
     *
     * @return biz_key - 业务的key
     */
    public String getBizKey() {
        return bizKey;
    }

    /**
     * 设置业务的key
     *
     * @param bizKey 业务的key
     */
    public void setBizKey(String bizKey) {
        this.bizKey = bizKey == null ? null : bizKey.trim();
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", isInvoke=").append(isInvoke);
        sb.append(", invokeTime=").append(invokeTime);
        sb.append(", operStatus=").append(operStatus);
        sb.append(", userUuid=").append(userUuid);
        sb.append(", comment=").append(comment);
        sb.append(", isDone=").append(isDone);
        sb.append(", bizKey=").append(bizKey);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OperationDO other = (OperationDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId())) && (this.getIsInvoke() == null ?
                other.getIsInvoke() == null : this.getIsInvoke().equals(other.getIsInvoke())) && (this.getInvokeTime() == null ?
                other.getInvokeTime() == null : this.getInvokeTime().equals(other.getInvokeTime())) && (this.getOperStatus() == null ?
                other.getOperStatus() == null : this.getOperStatus().equals(other.getOperStatus())) && (this.getUserUuid() == null ?
                other.getUserUuid() == null : this.getUserUuid().equals(other.getUserUuid())) && (this.getComment() == null ?
                other.getComment() == null : this.getComment().equals(other.getComment())) && (this.getIsDone() == null ? other.getIsDone()
                == null : this.getIsDone().equals(other.getIsDone())) && (this.getBizKey() == null ? other.getBizKey() == null
                : this.getCreateTime().equals(other.getCreateTime())) && (this.getUpdateTime() == null ? other.getUpdateTime() == null
                : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getIsInvoke() == null) ? 0 : getIsInvoke().hashCode());
        result = prime * result + ((getInvokeTime() == null) ? 0 : getInvokeTime().hashCode());
        result = prime * result + ((getOperStatus() == null) ? 0 : getOperStatus().hashCode());
        result = prime * result + ((getUserUuid() == null) ? 0 : getUserUuid().hashCode());
        result = prime * result + ((getComment() == null) ? 0 : getComment().hashCode());
        result = prime * result + ((getIsDone() == null) ? 0 : getIsDone().hashCode());
        result = prime * result + ((getBizKey() == null) ? 0 : getBizKey().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}