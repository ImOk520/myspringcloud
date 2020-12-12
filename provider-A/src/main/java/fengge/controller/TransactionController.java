package fengge.controller;

import com.feng.pojo.Dept;
import fengge.dao.DeptDao;
import fengge.service.DeptService;
import fengge.service.DeptServiceIml;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Feng, Ge 2020-10-13 20:32
 */
@Slf4j
@RequestMapping("/transaction")
@RestController
public class TransactionController {

    @Autowired
    private DeptService deptService;

    @Autowired
    private DeptDao deptDao;

    /**
     * @Transactional 决定是否将sql执行加入spring管理，若加入spring管理，在异常发生时将触发事务回滚
     * Feng, Ge
     */
    @PostMapping("/test1")
    @Transactional(rollbackFor = Exception.class)
    public void test1(Long deptno) {
        deptService.update(deptno);
        int i = 10/0;
    }

    /**
     * @Transactional 即使加入spring管理，若被try catch处理了，也不会生效，数据仍被改变
     * Feng, Ge
     */
    @PostMapping("/test2")
    @Transactional(rollbackFor = Exception.class)
    public void test2(Long deptno) {
        deptService.update(deptno);
        try {
            int i = 10/0;
        } catch (ArithmeticException e) {
            log.info("ok_ok_ok");
            e.printStackTrace();
        }
    }

    /**
     * @Transactional 自身调用不会触发事务
     * Feng, Ge
     */
    @PostMapping("/test3")
    public void test3(Long deptno) {
        deptService.testTransaction(deptno);
        int i = 10/0;
    }

}