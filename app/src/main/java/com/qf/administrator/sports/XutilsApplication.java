package com.qf.administrator.sports;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.se7en.utils.DeviceUtils;
import com.se7en.utils.SystemUtil;

import org.xutils.x;

import java.io.File;

import cn.bmob.v3.Bmob;


/**
 * Created by Administrator on 2016/9/27.
 */
public class XutilsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        initLibs();
        x.Ext.init(this);//xtuils的初始化
        Bmob.initialize(this, "3b5f4db2c530dc804dc1b4b601ba2600");

    }
    private void initLibs()
    {
        SystemUtil.setContext(this);
        DeviceUtils.setContext(this);
    }

    private void initImageLoader()
    {
        String filePath = "";
        // 如果外部储存卡处于挂载状态则使用外部储存卡,否则使用内部储存卡
        if (Environment.MEDIA_MOUNTED
                .equals(Environment.getExternalStorageState()))
        {
            filePath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/sport";
        }
        else
        {
            filePath = Environment.getDataDirectory().getAbsolutePath()
                    + "/sport";
        }
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this).
                // 设置内存缓存的大小(当超过这个值会去回收bitmap)
                // Runtime.getRuntime().maxMemory()获取当前应用运行时内存的大小
                        memoryCache(new LruMemoryCache(
                        (int) (Runtime.getRuntime().maxMemory() / 4)))
                .
                // 设置磁盘缓存的位置
                        diskCache(new UnlimitedDiskCache(new File(filePath)))
                .memoryCacheExtraOptions(200, 200).
                // 设置加载图片的线程池数量
                        threadPoolSize(8).build();
        ImageLoader.getInstance().init(config);

    }
}
