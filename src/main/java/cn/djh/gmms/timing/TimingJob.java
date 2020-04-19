package cn.djh.gmms.timing;

import cn.djh.gmms.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling// 开启定时任务
public class TimingJob {

    @Autowired
    private IEmployeeService employeeService;

    // 定时任务 每年1月1日0分0秒执行
    @Scheduled(cron = "0 0 0 1 1 ?")
    public void execute(){
        // 重置会员用户的会员类型为普通会员
        employeeService.resetMember();
    }

}
