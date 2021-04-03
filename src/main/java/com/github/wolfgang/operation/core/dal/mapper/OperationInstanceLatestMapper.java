package com.github.wolfgang.operation.core.dal.mapper;

import com.github.wolfgang.operation.core.dal.dataobject.OperationInstanceLatestDO;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface OperationInstanceLatestMapper extends Mapper<OperationInstanceLatestDO>, InsertListMapper<OperationInstanceLatestDO> {
}