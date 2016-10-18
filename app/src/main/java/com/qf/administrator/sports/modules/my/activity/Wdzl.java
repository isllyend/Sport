package com.qf.administrator.sports.modules.my.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qf.administrator.sports.R;
import com.qf.administrator.sports.activity.BaseActivity;
import com.qf.administrator.sports.activity.FragmentMy;
import com.qf.administrator.sports.modules.my.bean.ChangeIconEvent;
import com.qf.administrator.sports.modules.my.bean.ChangeNicknameEvent;
import com.qf.administrator.sports.modules.my.bean.LoginEvent;
import com.qf.administrator.sports.modules.my.bean.userinfo;
import com.qf.administrator.sports.util.ImageLoaderUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Wdzl extends BaseActivity implements View.OnClickListener {
    TextView grzl_gender;
    ImageView grzl_iv,iv_back;
    TextView grzl_nickname;
    TextView grzl_username;
    LinearLayout line1;
    LinearLayout line2;
    LinearLayout line3;
    LinearLayout line4;
    LinearLayout line5;

    protected void findViews() {
        grzl_username = ((TextView) findViewById(R.id.grzl_username));
        grzl_nickname = ((TextView) findViewById(R.id.grzl_nickname));
        grzl_gender = ((TextView) findViewById(R.id.grzl_gender));
        grzl_iv = ((ImageView) findViewById(R.id.grzl_iv));
        line1 = ((LinearLayout) findViewById(R.id.grzl_line1));
        line2 = ((LinearLayout) findViewById(R.id.grzl_line2));
        line3 = ((LinearLayout) findViewById(R.id.grzl_line3));
        line4 = ((LinearLayout) findViewById(R.id.grzl_line4));
        line5 = ((LinearLayout) findViewById(R.id.grzl_line5));
        iv_back= (ImageView) findViewById(R.id.iv_left);
    }

    protected void init() {
        grzl_username.setText(userinfo.getCurrentUser(userinfo.class).getUsername());
        grzl_gender.setText(userinfo.getCurrentUser(userinfo.class).getGender());
        grzl_nickname.setText(userinfo.getCurrentUser(userinfo.class).getNickname());

        if(userinfo.getCurrentUser(userinfo.class).getImage()==null){
            ImageLoader.getInstance().displayImage("http://img5.duitang.com/uploads/item/201408/25/20140825111147_iefmN.thumb.224_0.jpeg",grzl_iv,
                    ImageLoaderUtil.getCircleOption(null,0));
//            grzl_iv.setImageResource(R.mipmap.ic_launcher);
        }else{
        ImageLoader.getInstance().displayImage(userinfo.getCurrentUser(userinfo.class).getImage().getFileUrl(),grzl_iv,
                ImageLoaderUtil.getCircleOption(null,0));
        }
    }

    protected void initEvent() {
        iv_back.setOnClickListener(this);
        line1.setOnClickListener(this);
        line2.setOnClickListener(this);
        line3.setOnClickListener(this);
        line4.setOnClickListener(this);
        line5.setOnClickListener(this);
    }

    protected void loadData() {
    }
    String picturePath = null;
    File outFile = null;
    final Map<String, String> params_image = new HashMap<String, String>();

    public void setView() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                Wdzl.this);
        String[] strs = { "拍照上传", "相册选取", "取消" };
        builder.setItems(strs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent camera = new Intent(
                                "android.media.action.IMAGE_CAPTURE");
                        startActivityForResult(camera, 1);
                        break;
                    case 1:
                        Intent picture = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(picture, 2);
                        break;
                    case 2:
                        Toast.makeText(Wdzl.this, "关闭对话框", 10).show();
                        break;
                }
            }
        });
        builder.setCancelable(false);
        builder.create().show();

    }

    /**
     * 1取出拍照的结果存储到手机内存则pictures文件夹， 再从文件加下取出展示到界面，并且点击放大
     * 2从相册中取出图片，压缩，放到dialog中，然后去出展示到界面 点击放大。
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;
        File outDir = null;
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 这个路径，在手机内存下创建一个pictures的文件夹，把图片存在其中。
            outDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        } else {
            if (Wdzl.this != null) {
                outDir = this.getFilesDir();
            }
        }
        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                // 直接获取照片，data是系统默认的（在系统中已经压缩过图片取出来就行了）；
                bitmap = (Bitmap) data.getExtras().get("data");
                if (bitmap != null) {
                } else {

                    Toast.makeText(Wdzl.this, "图片获取失败，请重新选择", 10)
                            .show();
                }
                // 保存图片
                try {
                    outFile = new File(outDir, System.currentTimeMillis()
                            + ".jpg");
                    FileOutputStream fos = new FileOutputStream(outFile);
                    boolean flag = bitmap.compress(Bitmap.CompressFormat.JPEG,
                            100, fos);// 把数据写入文件
                    Log.i("1", "flag=" + flag);
                    if (flag) {
                        Toast.makeText(Wdzl.this,
                                "图片已保存至:" + outFile.getAbsolutePath(), 10)
                                .show();
                        final BmobFile file=new BmobFile(outFile);
                        file.upload(new UploadFileListener() {
                            @Override
                            public void done(BmobException e) {
                                userinfo user =userinfo.getCurrentUser(userinfo.class);
                                user.setImage(file);
                                user.update(user.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        EventBus.getDefault().post(new ChangeIconEvent(userinfo.getCurrentUser(userinfo.class).getImage().getUrl()));
                                    }
                                });
                            }
                        });
                        // 展示图片，点击放大
                        String pathname = outFile.getAbsolutePath();// 绝对路径
                        final Bitmap myBitmap = BitmapFactory
                                .decodeFile(pathname);
                        grzl_iv.setImageBitmap(bitmap);
                        grzl_iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getBigPicture(myBitmap);// 点击放大
                            }
                        });
                    } else {

                        Toast.makeText(Wdzl.this, "图片保存失败!", 10).show();

                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }// 相册显示界面
        else if (requestCode == 2 && data != null
                && resultCode == Activity.RESULT_OK) {
            try {
                Uri selectedImage = data.getData();
                String[] filePathColumns = { MediaStore.Images.Media.DATA };
                Cursor c = Wdzl.this.getContentResolver().query(
                        selectedImage, filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                picturePath = c.getString(columnIndex);// 取出图片路径
                Log.e("1", "图片路径" + picturePath);
                c.close();

                // 调用压缩方法压缩图片
                bitmap = createThumbnail(picturePath, 4);
                // 保存图片到pictures文件夹下,上传的时候还要上传outFile
                outFile = new File(outDir, System.currentTimeMillis() +
                        ".jpg");
                FileOutputStream fos = new FileOutputStream(outFile);
                boolean flag = bitmap.compress(Bitmap.CompressFormat.JPEG,
                        100,
                        fos);// 把数据写入文件

                if (bitmap != null) {
                    // 选择图片后显示在对话框内

                }
                final BmobFile file=new BmobFile(outFile);
                file.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        userinfo user =userinfo.getCurrentUser(userinfo.class);
                        user.setImage(file);
                        user.update(user.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                EventBus.getDefault().post(new ChangeIconEvent(userinfo.getCurrentUser(userinfo.class).getImage().getUrl()));
                            }
                        });
                    }
                });
                // 展示图片并点击放大
                String pathname = outFile.getAbsolutePath();//绝对路径
                final Bitmap myBitmap = BitmapFactory.decodeFile(pathname);
                grzl_iv.setImageBitmap(myBitmap);
                grzl_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getBigPicture(myBitmap);// 点击放大
                    }
                });

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Bitmap createThumbnail(String filepath, int i) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = i;
        return BitmapFactory.decodeFile(filepath, options);
    }

    /**
     * 判断网络是否正常
     *
     *
     */
    public boolean isNetworkConnected(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 点击图片放大查看
     *
     */
    private void getBigPicture(Bitmap b) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View imgEntryView = inflater.inflate(R.layout.dialog_photo_entry, null); // 加载自定义的布局文件
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        ImageView img = (ImageView) imgEntryView.findViewById(R.id.large_image);
        if (b != null) {
            Display display = Wdzl.this.getWindowManager()
                    .getDefaultDisplay();
            int scaleWidth = display.getWidth();
            int height = b.getHeight();// 图片的真实高度
            int width = b.getWidth();// 图片的真实宽度
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) img.getLayoutParams();
            lp.width = scaleWidth;// 调整宽度
            lp.height = (height * scaleWidth) / width;// 调整高度
            img.setLayoutParams(lp);
            img.setImageBitmap(b);
            dialog.setView(imgEntryView); // 自定义dialog
            dialog.show();
        }
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.grzl_line1:
                setView();
                break;
            case R.id.grzl_line2:
                showRenameNicknamedialog();
                break;
            case R.id.grzl_line3:
                showGenderChoose();
                break;
            case R.id.grzl_line4:
                break;
            case R.id.grzl_line5:
                startActivity(new Intent(this, Forgetpwd.class));
                break;
        }
    }


    protected int setViewId() {
        return R.layout.grzl;
    }

    //修改性别
    public void showGenderChoose() {
        PopupMenu pop = new PopupMenu(this, grzl_gender);
        getMenuInflater().inflate(R.menu.mymenu, pop.getMenu());
        pop.show();
        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                userinfo user = userinfo.getCurrentUser(userinfo.class);
                switch (item.getTitle().toString()) {
                    case "男":
                        grzl_gender.setText("男");
                        user.setGender("男");
                        Toast.makeText(Wdzl.this, "修改成功", Toast.LENGTH_SHORT).show();
                        break;
                    case "女":
                        grzl_gender.setText("女");
                        user.setGender("女");
                        Toast.makeText(Wdzl.this, "修改成功", Toast.LENGTH_SHORT).show();
                        break;
                }
                user.update(user.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(Wdzl.this, "修改成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Wdzl.this, "修改失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                return false;
            }
        });
    }

    //修改nickname
    public void showRenameNicknamedialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("重命名匿名");    //设置对话框标题
        builder.setIcon(android.R.drawable.ic_menu_edit);    //设置对话框标题前的图标
        final EditText edit = new EditText(this);
        edit.setText(userinfo.getCurrentUser(userinfo.class).getNickname());
        builder.setView(edit);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userinfo user = userinfo.getCurrentUser(userinfo.class);
                user.setNickname(edit.getText().toString());
                user.update(user.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(Wdzl.this, "修改成功", Toast.LENGTH_SHORT).show();
                            grzl_nickname.setText(edit.getText().toString());
                            EventBus.getDefault().post(new ChangeNicknameEvent(edit.getText().toString()));
                        } else {
                            Toast.makeText(Wdzl.this, "修改失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(true);    //设置按钮是否可以按返回键取消,false则不可以取消
        AlertDialog dialog = builder.create();    //创建对话框
        dialog.setCanceledOnTouchOutside(true);    //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
        dialog.show();
    }
}
