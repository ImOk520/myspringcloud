package fengge.dao;

import fengge.DTO.DataEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


@Component
public interface DeptDao {

    int update ( @Param("deptno") Long deptno);

}
