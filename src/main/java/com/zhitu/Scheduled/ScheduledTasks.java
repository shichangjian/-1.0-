package com.zhitu.Scheduled;

import com.zhitu.dao.mapper.RetrievePasswordMapper;
import com.zhitu.dao.mapper.TempFileMapper;
import com.zhitu.entity.Photo;
import com.zhitu.dao.mapper.PhotoMapper;
import com.zhitu.dao.mapper.UserMapper;
import com.zhitu.externalAPI.Baidu;
import com.zhitu.tools.PhotoTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * 定时任务表
 */
@Component  //不知道是哪个层就加，这个下面方法有@Autowired就加
public class ScheduledTasks {

    /**
     * 注入一个定义好的bean,用于容器(container)配置的注解
     * 无论以何种方式注入，注入的bean就相当于类中的一个普通对象应用，
     * 这是它的实例化是spring去容器中找符合的bean进行实例化，并注入到类当中的。
     * 他们之间的关系就是普通的一个对象持有另一个对象引用的关系。
     * 只是这些对象都是spring当中的bean而已。
     */
    @Autowired
    private PhotoTool photoTool;

    @Autowired
    private Baidu baidu;

    @Resource
    private TempFileMapper tempFileMapper;

    @Resource
    private PhotoMapper photoMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RetrievePasswordMapper retrievePasswordMapper;

    //每天检查一次回收站是否有过期照片

    /**
     * Scheduled注解用于执行定时任务，参数有以下几种：
     * cron（自定义格式）
     * fixedDelay（上次任务执行结束时间点延迟多久）
     * fixedRate（如果任务执行时间小于rate，那么两次任务的开始时间间隔rate执行，如果大于rate，那么上次任务执行完立即执行）
     * 这三种设置间隔的方式只能使用其中一种，不能同时存在。
     * 使用注解时，同时要使用@EnableScheduling注解，开启scheduled，不然不会执行
     */
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void cleanRecycleBin()
    {
        List<Integer> list = photoMapper.selectPhotoIdWhereExpired();
        for(int id : list)
        {
            Photo photo = photoMapper.selectAllByPhotoId(id);
            String path = photo.getPath();
            photoMapper.deleteByPhotoId(id);
            userMapper.updatePhotoInRecycleBinAmountByUserId(photo.getUserId(),-1);
            File file = new File(photoTool.LOCAL_DIR + path);
            file.delete();
        }
    }

    //每天清理一次24小时之前的临时文件ate
    //参数fixedRate意思是第一次延迟多长时间后再执行
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void cleanTempFile()
    {
        List<Integer> list = tempFileMapper.selectTempFileIdWhereExpired();
        for(int id : list)
        {
            String path = tempFileMapper.selectPathByTempFileId(id);
            File file = new File(photoTool.LOCAL_DIR + path);
            file.delete();
            tempFileMapper.deleteByTempFileId(id);
        }
    }

    //每天清理一次24小时之前的找回密码申请id
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void cleanSid()
    {
        List<Integer> list = retrievePasswordMapper.selectRetrievePasswordIdWhereExpired();
        for(int id : list)
        {
            retrievePasswordMapper.deleteByRetrievePasswordId(id);
        }
    }

    /**
     * L 表示最后的意思。
     * 在日字段设置上，表示当月的最后一天(依据当前月份，如果是二月还会依据是否是润年[leap]),
     * 在周字段上表示星期六，相当于”7”或”SAT”。
     * 如果在”L”前加上数字，则表示该数据的最后一个。
     * 例如在周字段上设置”6L”这样的格式,则表示“本月最后一个星期五”
     */
    //每25天更新一次baidu的access_token
    @Scheduled(fixedRate = 1000L * 60 * 60 * 24 * 25)
    public void updateBaiduAccessToken()
    {
        baidu.updateAccessToken();
    }
}
