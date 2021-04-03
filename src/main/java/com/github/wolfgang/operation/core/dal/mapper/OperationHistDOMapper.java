package com.github.wolfgang.operation.core.dal.mapper;

import com.github.wolfgang.operation.core.dal.dataobject.OperationDO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface OperationHistDOMapper extends Mapper<OperationDO> {

    List<Integer> selectExpiredOperations();
}